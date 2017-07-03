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
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Neel Patel
 */
@Entity 
@Table(name = "user_profiles")
public class UserProfile {
    
//schema
    @Id
    @GenericGenerator (name = "userId_gen", strategy = "sequence")
    @GeneratedValue(generator = "userId_gen")
    private long userId;
    
    @Column(name = "user_name", length = 50, nullable = false)
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
    
//methods & constructors

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

    public Set<Article> getArticles() {
        return Collections.unmodifiableSet(articles);
    }

    public Set<Comment> getComments() {
        return Collections.unmodifiableSet(comments);
    }

    public Set<Article> getLikes() {
        return Collections.unmodifiableSet(likes);
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
    
    
}
