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

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
//import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 *
 * @author Neel Patel
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Article.byTag",
          query = "select distinct a from Article a inner join "
                    +"a.tags t where t.tagName in (:tags) order by a.articleId desc"),
    @NamedQuery(name = "Article.count",
          query = "select count(a) from Article a"),
    @NamedQuery(name = "Article.allArticle", query = "from Article")
})
//@XmlRootElement
@Table(name = "articles")
public class Article {

//schema    
    @Id
    @GenericGenerator(name = "articleId_gen", strategy = "sequence")
    @GeneratedValue(generator = "articleId_gen")
    private long articleId;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private UserProfile author; //foreign key :- UserProfile(userId)
    
    @Column(name = "article_title", nullable = false, length = 1000)
    private String title;
    
    @Column(name = "publish_date")
    private LocalDate publishDate;
    
    @Lob
    @Column(name = "article_data",nullable = false)
    private ArticleContent articleContent = new ArticleContent();
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "article_tags", joinColumns = @JoinColumn(name = "article_id"),
              inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();
    
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>(); // comments on this article
    
    @ManyToMany()
    @JoinTable(name = "likes", joinColumns = @JoinColumn(name = "article_id"),
              inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserProfile> likes = new HashSet<>(); // likes on this article
    
//methods & constructors

    /**
     * initialized object with specified id.
     * @deprecated object initialized with this constructor might not be
       able to used with database as the userId is auto-generated field.
     * @param id articleId
     */
    public Article(long id){
        this.articleId=id;
    }

    public Article() {
    }
    
    public long getArticleId() {
        return articleId;
    }

    public UserProfile getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public List<String> getArticleContent() {
        return articleContent.getContent();
    }

    @XmlTransient
    public Set<Tag> getTags() {
        return tags;
    }

    @XmlTransient
    public Set<Comment> getComments() {
        return Collections.unmodifiableSet(comments);
    }

    @XmlTransient
    public Set<UserProfile> getLikes() {
        return Collections.unmodifiableSet(likes);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * update publish date of the article.
     * @param publishDate new publish date
     */
    public void setPublishDate(LocalDate publishDate) {
        if(publishDate == null)
            return;
        this.publishDate = publishDate;
    }
    
    /**
     * update author with 'user'
     * @param user author of the article
     */
    void setAuthor(UserProfile user){
        this.author=user;
    }
    
    /**
     * this method add comment to the article.
     * this method will add the comment to the articles record of comments.
     * it also update the comment author of the object with the current object.
     * if 'comment' parameter is {@code null} then this method do nothing.<br>
     * Note:- if the comment is already exist in the record (i.e. comment having
       same commentId) then this method will do nothing.
     * @param comment comment object going to be added.
     */
    public void addComment(Comment comment){
        if(comment == null)
            return;
        if(this.comments.add(comment))
            comment.setArticle(this);
    }
    
    public void addLike(UserProfile user){
        if(user == null)
            return;
        if(likes.add(user))
            user.addLike(this);
    }
    
    public void removeLike(UserProfile user){
        if(user == null)
            return;
        likes.remove(user);
        user.removeLike(this);
    }
    
    public void removeComment(Comment comment){
        comments.remove(comment);
        comment.setAuthor(null);
    }
    
    @Override
    public String toString() {
        return articleId+", "+title;
    }

    /**
     * this method returns true only if 'obj' is instance of Article and it have
       same 'articleId'.
     * @param obj object
     * @return true if object is equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Article))
            return false;
        Article up=(Article)obj;
        return this.articleId==up.articleId;
    }
 
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (int) (this.articleId ^ (this.articleId >>> 32));
        return hash;
    }
    
}
