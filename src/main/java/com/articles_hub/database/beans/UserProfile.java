/*
 * The MIT License
 *
 * Copyright 2017 Neel Patel.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.articles_hub.database.beans;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
//import org.hibernate.annotations.NamedQuery;

/**
 *
 * @author Neel Patel
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "UserProfile.byName",
          query = "from UserProfile where userName = :name")
})
@Table(name = "user_profiles")
//@XmlRootElement
public class UserProfile {
    
//schema
    @Id
    @GenericGenerator (name = "userId_gen", strategy = "sequence")
    @GeneratedValue(generator = "userId_gen")
    private long userId;
    
    @Column(name = "user_name", length = 50, nullable = false, unique = true)
    private String userName;
    
    @Column(name = "passwd", length = 50, nullable = false)
    private String pass;
    
    @Column(name = "user_email", length = 100, nullable = false, unique = true)
    private String emailId;
    
    @Column(name = "user_info", length = 5000)
    private String info;
    
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private Set<Article> articles = new HashSet<>(); // articles written by User.
    
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>(); // comments by user.
    
    @ManyToMany(mappedBy = "likes", cascade = CascadeType.ALL)
    private Set<Article> likes = new HashSet<>(); // likes by this user.
    
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "user_favorite_tags", joinColumns = @JoinColumn(name = "userId"),
              inverseJoinColumns = @JoinColumn(name = "tagId"))
    private Set<Tag> favoriteTag = new HashSet<>(); // favorite tags of this user.
    
//methods & constructors

    /**
     * initialized object with specified id.
     * @deprecated object initialized with this constructor might not be
       able to used with database as the userId is auto-generated field.
     * @param id userId
     */
    public UserProfile(long id){
        this.userId=id;
    }

    public UserProfile() {
    }
    
    public long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPass() {
        return pass;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getInfo() {
        return info;
    }

    @XmlTransient
    public Set<Article> getArticles() {
        return Collections.unmodifiableSet(articles);
    }

    @XmlTransient
    public Set<Comment> getComments() {
        return Collections.unmodifiableSet(comments);
    }

    @XmlTransient
    public Set<Article> getLikes() {
        return Collections.unmodifiableSet(likes);
    }

    public Set<Tag> getFavoriteTag() {
        return favoriteTag;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setEmailId(String emailId) {
        if(emailId == null || emailId.trim().equals(""))
            return;
        this.emailId = emailId;
    }
    
    /**
     * this method add article to the user.
     * this method will add the article to the users record articles.
     * it also update the article author of the object.
     * if 'comment' parameter is {@code null} then this method do nothing.<br>
     * Note:- if the article is already exist in the record (i.e. article having
       same articleId) then this method will do nothing.
     * @param article Article object going to be added.
     */
    public void addArticle(Article article){
        if(article == null)
            return;
        if(articles.add(article))
            article.setAuthor(this);
    }
    
    /**
     * this method add comment to the user.
     * this method will add the comment to the users record of comments.
     * it also update the comment author of the object with the current object.
     * if 'comment' parameter is {@code null} then this method do nothing.<br>
     * Note:- if the comment is already exist in the record (i.e. comment having
       same commentId) then this method will do nothing.<br>
     * this method will not remove comment from the record of previous author
       of the comment. it is recommended to update old author before this operation.
     * @param comment comment object going to be added.
     */
    public void addComment(Comment comment){
        if(comment == null)
            return;
        if(comments.add(comment))
            comment.setAuthor(this);
    }
    
    void addLike(Article article){
        if(article == null)
            return;
        likes.add(article);
    }
    
    void removeLike(Article article){
        likes.remove(article);
    }
    
    public void removeComment(Comment comment){
        comments.remove(comment);
        comment.setAuthor(null);
    }
    
    public void removeArticle(Article article){
        articles.remove(article);
        article.setAuthor(null);
    }

    @Override
    public String toString() {
        return userId+", "+userName;
    }

    /**
     * this method returns true only if 'obj' is instance of UserProfile and
       it have same 'userId'.
     * @param obj object
     * @return true if object is equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof UserProfile))
            return false;
        UserProfile up=(UserProfile)obj;
        return this.userId==up.userId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (int) (this.userId ^ (this.userId >>> 32));
        return hash;
    }
    
    
}
