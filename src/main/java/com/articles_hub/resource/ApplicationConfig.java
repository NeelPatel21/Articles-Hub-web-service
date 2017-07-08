/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.articles_hub.resource;

//import com.sun.jersey.server.impl.wadl.WadlResource;
import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Neel Patel
 */
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
//        resources.add(com.articles_hub.resource.GenericResource.class);
        resources.add(com.articles_hub.resource.UserResource.class);
    }
    
}
