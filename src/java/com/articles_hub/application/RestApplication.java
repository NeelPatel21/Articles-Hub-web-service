package com.articles_hub.application;

//import com.sun.jersey.api.core.PackagesResourceConfig;
import com.articles_hub.resource.ArticleResource;
import com.articles_hub.resource.CommentResource;
import com.articles_hub.resource.TagResource;
import com.articles_hub.resource.UserResource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


@ApplicationPath("api")
public class RestApplication extends Application {

    private Set<Class<?>> resource=new HashSet<>();
    private Map<String,Object> pro=new HashMap<>();
    public RestApplication() {
        
        System.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/Articles_Hub");     
    }

//    @Override
//    public Map<String, Object> getProperties() {
//        pro.putAll(super.getProperties());
//        return pro; //To change body of generated methods, choose Tools | Templates.
//    }

    @Override
    public Set<Class<?>> getClasses() {
        resource.add(UserResource.class);
        resource.add(ArticleResource.class);
        resource.add(CommentResource.class);
        resource.add(TagResource.class);
        return resource;
    }
    
    
}
