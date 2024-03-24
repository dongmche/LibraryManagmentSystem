package com.example.Fakeapp;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class applicationConf {
    @Bean
    @Qualifier("bean1")
    public firstClass first(){
        return new firstClass("first");
    }
    @Bean
//    @Qualifier("bean2")
    public firstClass second(){
        return new firstClass("second");
    }

}
