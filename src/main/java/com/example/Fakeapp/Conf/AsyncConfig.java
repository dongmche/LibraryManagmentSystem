package com.example.Fakeapp.Conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

@Configuration
public class AsyncConfig {

    @Bean
    public ExecutorService taskExecutor() {
        return Executors.newCachedThreadPool();  // Creates a thread pool that creates new threads as needed, but will reuse previously constructed threads when they are available.
    }
}
