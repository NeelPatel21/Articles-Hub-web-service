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
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Neel Patel
 */
@Entity
@Table(name = "comments")
//@XmlRootElement
public class Comment {

//schema
    @Id
    @GenericGenerator(name = "commentId_gen", strategy = "sequence")
    @GeneratedValue(generator = "commentId_gen") 
    private long commentId;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "article_id")
    private Article article; //foreign key Article(articleId)
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserProfile author; //foreign key :- UserProfile(userId)
    
    @Column(name = "comment_data", length = 2000, nullable = false)
    private String commentBody;
    
    @Column(name = "comment_date")
    private LocalDate date;
    
    @Column(name = "comment_time")
    private LocalTime time;

//constructors & methods    
    
    /**
     * initialized object with specified id.
     * @deprecated object initialized with this constructor might not be
       able to used with database as the userId is auto-generated field.
     * @param id articleId
     */
    public Comment(long id){
        this.commentId=id;
    }

    public Comment() {
    }
    
    public long getCommentId() {
        return commentId;
    }

    public Article getArticle() {
        return article;
    }

    public UserProfile getAuthor() {
        return author;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }
    
    void setAuthor(UserProfile user){
        this.author=user;
    }
    
    void setArticle(Article article){
        this.article=article;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        if(date!=null)
            this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        if(time!=null)
            this.time = time;
    }

    
    @Override
    public String toString() {
        return commentId+"";
    }

//    /**
//     * this method returns true only if 'obj' is instance of Comment and it have
//       same 'commentId'.
//     * @param obj object
//     * @return true if object is equal, false otherwise.
//     */
//    @Override
//    public boolean equals(Object obj) {
//        if(!(obj instanceof Comment))
//            return false;
//        Comment up=(Comment)obj;
//        if(this.commentId==0 || up.commentId==0)
//            return false;
//        return this.commentId==up.commentId;
//    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (int) (this.commentId ^ (this.commentId >>> 32));
        return hash;
    }
    
}
