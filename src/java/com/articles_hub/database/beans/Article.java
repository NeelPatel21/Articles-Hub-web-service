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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Neel Patel
 */
@Entity
@Table(name = "articles")
public class Article {

//schema    
    @Id
    @GenericGenerator(name = "articleId_gen", strategy = "sequence")
    @GeneratedValue(generator = "articleId_gen")
    private long articleId;
    
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private UserProfile author; //foreign key :- UserProfile(userId)
    
    @Column(name = "article_title", nullable = false, length = 1000)
    private String title;
    
    @Column(name = "publish_datetime")
    private LocalDateTime publishDate;
    
    @Lob
    @Column(name = "article_data",nullable = false)
    private List<String> articleContant = new ArrayList<>();
    
    @ManyToMany
    @JoinTable(name = "article_tags", joinColumns = @JoinColumn(name = "article_id"),
              inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();
    
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>(); // comments on this article
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "likes", joinColumns = @JoinColumn(name = "article_id"),
              inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserProfile> likes = new HashSet<>(); // likes on this article
    
//methods & constructors

    public long getArticleId() {
        return articleId;
    }

    public UserProfile getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public List<String> getArticleContant() {
        return articleContant;
    }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Set<Comment> getComments() {
        return Collections.unmodifiableSet(comments);
    }

    public Set<UserProfile> getLikes() {
        return Collections.unmodifiableSet(likes);
    }
    
    
}
