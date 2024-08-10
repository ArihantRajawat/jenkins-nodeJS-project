package com.hospital.management.config;

import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @SuppressWarnings("unchecked")
    @Bean
    public Cloudinary getCloudinary() {
        @SuppressWarnings("rawtypes")
        Map config = new HashMap<>();
        config.put("cloud_name", "dqv66cygs");
        config.put("api_key", "816436918347434");
        config.put("api_secret", "wKphdH7QwFxrkt2ioiN3AVNJzXw");
        config.put("secure", true);
        return new Cloudinary(config);
    }
}
