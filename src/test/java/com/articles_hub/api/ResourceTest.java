package com.articles_hub.api;

import com.articles_hub.application.RestApplication;
import com.articles_hub.api.model.TagDetail;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.test.JerseyTest;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

//import com.articles_hub.MyResource;

public class ResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new RestApplication();
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testGetTag() {
        final TagDetail tag = target().path("tag").path("game").request()
                  .get(TagDetail.class);
        assertEquals("game", tag.getTagName());
    }
    
}
