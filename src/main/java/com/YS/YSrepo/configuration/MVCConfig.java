package com.YS.YSrepo.configuration;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class MVCConfig implements WebMvcConfigurer {

    private String librayPath = "uploads/library";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/library/file/**")
                .addResourceLocations("file:" + librayPath + "/");
    }

}
