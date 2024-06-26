package com.example.Fakeapp.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;


    @Autowired
    private RootInterceptor rootInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/books/**", "/book/**")
                .excludePathPatterns("/login", "/error");
        registry.addInterceptor(rootInterceptor)
                .addPathPatterns("/root/**").excludePathPatterns("/root/login");
    }
}

