package com.example.bookshop.infra.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {

        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dh4tdxre1",
                "api_key", "783882863847142",
                "api_secret", "lDrXURH26HTsH8PVxZT66ryvgjM",
                "secure", true
        ));
    }



}
