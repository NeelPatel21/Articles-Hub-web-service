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

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
//import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 *
 * @author Neel Patel
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Tag.byName",
              query = "from Tag where tagName = :name and tagStatus = 'enable'"),
    @NamedQuery(name = "Tag.byName_all",
              query = "from Tag where tagName = :name"),
    @NamedQuery(name = "Tag.byName_byStatus",
              query = "from Tag where tagName = :name and tagStatus = :status"),
    @NamedQuery(name = "Tag.byStatus", query = "from Tag where tagStatus = :status"),
    @NamedQuery(name = "Tag.allTag", query = "from Tag")
})
@Table(name = "tags")
//@XmlRootElement
public class Tag {

//static
//schema
    @Id
    @GenericGenerator(name = "tagId_gen", strategy = "sequence")
    @GeneratedValue(generator = "tagId_gen") 
    private long tagId;
    
    @Column(name = "tag_name",unique = true, length = 50, nullable = false)
    private String tagName;
    
    @Column(name = "tag_status", length = 10, nullable = false)
    private String tagStatus;

    @ManyToMany(mappedBy = "favoriteTag", fetch = FetchType.LAZY)
    private Set<UserProfile> favTag=new HashSet<UserProfile>();
    
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private Set<Article> articleTags=new HashSet<Article>();
//constructors & methods    

    /**
     * initialized object with specified id.
     * @deprecated object initialized with this constructor might not be
       able to used with database as the userId is auto-generated field.
     * @param id articleId
     */
    public Tag(long id){
        this.tagId=id;
    }

    public Tag() {
    }
   
    public long getTagId() {
        return tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public TagStatus getTagStatus() {
        return TagStatus.valueOf(tagStatus);
    }

    public void setTagStatus(TagStatus tagStatus) {
        this.tagStatus = tagStatus.name();
    }
    
    public void removeAll(){
        this.favTag.parallelStream().forEach(x->x.getFavoriteTag().remove(this));
        this.articleTags.parallelStream().forEach(x->x.getTags().remove(this));
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Tag){
            Tag o=(Tag)obj;
            return o.tagName.equals(this.tagName);
        }
        return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.tagName);
        return hash;
    }

    
}
