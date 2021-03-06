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
package com.articles_hub.api.resource;

import com.articles_hub.api.model.LinkMaker;
import com.articles_hub.api.model.ShortArticleDetail;
import com.articles_hub.api.providers.Secured;
import com.articles_hub.service.HomeService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Neel Patel
 */
@Path("/home")
@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class HomeResource {
    private HomeService service;
    
    public HomeResource(){
        try{
            service=HomeService.getHomeService();
//            System.out.println(" dccewcahcajhcabcjabcajcbac "+service);
        }catch(Exception ex){
            ex.printStackTrace();
        }
//        System.out.println("user service request");
    }
    
    @Context
    private UriInfo urif;
    
    @GET
    @Path("")
//    @Produces(MediaType.APPLICATION_XML)
    public ShortArticleDetail[] getDefaultList(){
        ShortArticleDetail ar[]=service.getArticles();
        LinkMaker.popLinks(urif,ar);
        return ar;
    }
    
    @GET
    @Path("/{userName}")
    @Secured
//    @Produces(MediaType.APPLICATION_XML)
    public ShortArticleDetail[] getPersonalList(@PathParam("userName") String userName,
              @Context SecurityContext secure){
        if(!secure.getUserPrincipal().getName().equals(userName))
            return null;
        ShortArticleDetail ar[]=service.getArticles(userName);
        LinkMaker.popLinks(urif, ar);
        return ar;
    }
    
}
