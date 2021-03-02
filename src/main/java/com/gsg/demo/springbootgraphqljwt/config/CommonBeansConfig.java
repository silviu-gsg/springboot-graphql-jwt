package com.gsg.demo.springbootgraphqljwt.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonBeansConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}