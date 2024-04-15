package com.estate.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConfiguration extends WebMvcConfigurationSupport {

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry

                .addResourceHandler("/img/**")
            //every time link looking like this is searched

                .addResourceLocations("file:src/main/resources/static/img/");
            //it will be looking into these resources

    }
}