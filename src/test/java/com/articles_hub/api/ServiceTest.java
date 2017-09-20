package com.articles_hub.api;

import com.articles_hub.api.model.TagDetail;
import com.articles_hub.application.RestApplication;
import com.articles_hub.service.TagService;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.test.JerseyTest;

import org.junit.Test;

//import com.articles_hub.MyResource;

public class ServiceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new RestApplication();
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testRemoveTag() {
        TagDetail tag=new TagDetail();
        tag.setTagName("test_tag");
        assert TagService.getTagService().addTag(tag);
        assert TagService.getTagService().removeTag("test_tag");
    }
    
}
