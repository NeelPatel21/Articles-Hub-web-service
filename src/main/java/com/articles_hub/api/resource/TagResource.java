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
import com.articles_hub.api.model.TagDetail;
import com.articles_hub.api.providers.Secured;
import com.articles_hub.service.TagService;
import java.net.URI;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Neel Patel
 */
@Path("/tag")
@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class TagResource {
    
    private TagService service;
    
    public TagResource(){
        try{
            service=TagService.getTagService();
//            System.out.println(" dccewcahcajhcabcjabcajcbac "+service);
        }catch(Exception ex){
            ex.printStackTrace();
        }
//        System.out.println("tag service request");
    }
    
    @Context
    private UriInfo urif;
    
//    @GET
//    public String getTagDetail(){
//        return "service :- ";
//    }
    
    @GET
    @Path("/{tagName}")
//    @Produces(MediaType.APPLICATION_XML)
    public TagDetail getTagDetail(@PathParam("tagName") String tagName){
        TagDetail tag=service.getTagDetail(tagName);
        LinkMaker.popLinks(urif, tag);
        return tag;
    }
    
    @GET
    @Path("/{tagName}/articles")
//    @Produces(MediaType.APPLICATION_XML)
    public ShortArticleDetail[] getAllArticles(@PathParam("tagName") String tags){
        ShortArticleDetail ar[]=service.getAllArticles(tags);
        LinkMaker.popLinks(urif, ar);
        return ar;
    }
    
//    @PUT
//    @Path("/{tagName}")
//    public String updateTagDetail(@PathParam("tagName") String tagName){
//        return "service :- "+tagName;
//    }
//  
    
//secure    
    @POST
    @Secured
    public Response createTagDetail(TagDetail tag){
        if(service.addTag(tag)){
            TagDetail newTag=service.getTagDetail(tag.getTagName());
            LinkMaker.popLinks(urif, newTag);
            return Response.created(URI.create(newTag.getLinks().stream()
                      .filter(x->x.getName().equalsIgnoreCase("self"))
                      .findAny().get().getUrl())).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    
}
