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
package com.articles_hub.model;

import java.time.LocalDateTime;
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
    private List<String> articleContant;
    
    @Transient
    private Set<Tag> tags;
    
    @OneToMany(mappedBy = "article")
    private Set<Comment> comments; // comments on this article
    
    @Transient
    private Set<Like> likes; // likes on this article
    
}
