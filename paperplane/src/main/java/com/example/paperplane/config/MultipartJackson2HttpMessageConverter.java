//package com.example.paperplane.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Type;
//
//@Component
//public class MultipartJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter {
//
//
//    public MultipartJackson2HttpMessageConverter(ObjectMapper objectMapper) {
//        super(objectMapper, MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM);
//    }
//
//    @Override
//    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
//        return false; // This converter is only for reading
//    }
//
//    @Override
//    public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
//        return false; // This converter is only for reading
//    }
//
//    @Override
//    protected boolean canWrite(MediaType mediaType) {
//        return false; // This converter is only for reading
//    }
//}
