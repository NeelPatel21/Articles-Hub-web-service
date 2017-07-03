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
import javax.persistence.Entity;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Neel Patel
 */
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GenericGenerator(name = "commentId_gen", strategy = "sequence")
    @GeneratedValue(generator = "commentId_gen") 
    private long commentId;
    
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article; //foreign key Article(articleId)
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserProfile author; //foreign key :- UserProfile(userId)
    
    @Column(name = "comment_data", length = 2000)
    private String commentBody;
    
    @Column(name = "comment_date")
    private LocalDateTime date;
}
