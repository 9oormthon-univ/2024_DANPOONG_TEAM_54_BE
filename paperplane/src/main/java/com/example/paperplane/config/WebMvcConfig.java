package com.example.paperplane.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final MultipartJackson2HttpMessageConverter multipartJackson2HttpMessageConverter;

    public WebMvcConfig(MultipartJackson2HttpMessageConverter converter) {
        this.multipartJackson2HttpMessageConverter = converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(multipartJackson2HttpMessageConverter);
    }
}
