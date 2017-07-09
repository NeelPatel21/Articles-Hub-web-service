package com.articles_hub.application;

import com.sun.jersey.api.core.PackagesResourceConfig;
import javax.ws.rs.ApplicationPath;


@ApplicationPath("/")
public class RestApplication extends PackagesResourceConfig {

    public RestApplication() {
        super("com.articles_hub.resource");
    }
}
