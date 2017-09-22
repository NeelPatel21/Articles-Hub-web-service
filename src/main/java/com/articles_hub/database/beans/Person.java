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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 *
 * @author Neel Patel
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Person.byName",
          query = "from Person where userName = :name")
})
@Table(name = "persons")
@Inheritance(strategy = InheritanceType.JOINED)
//@XmlRootElement
public class Person {
    
//schema
    @Id
    @GenericGenerator (name = "userId_gen", strategy = "sequence")
    @GeneratedValue(generator = "userId_gen")
    private long userId;
    
    @Column(name = "user_name", length = 50, nullable = false, unique = true)
    private String userName;
    
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;
    
    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;
    
    @Column(name = "passwd", length = 50, nullable = false)
    private String pass;
    
    @Column(name = "user_email", length = 100, nullable = false, unique = true)
    private String emailId;
    
//methods & constructors

    /**
     * initialized object with specified id.
     * @deprecated object initialized with this constructor might not be
       able to used with database as the userId is auto-generated field.
     * @param id userId
     */
    public Person(long id) {
        this.userId=id;
    }

    public Person() {
    }
    
    public long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPass() {
        return pass;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
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
        Person up=(Person)obj;
        return this.userId==up.userId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (int) (this.userId ^ (this.userId >>> 32));
        return hash;
    }
    
}
