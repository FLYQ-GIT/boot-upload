package com.guocoffee.upload.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;
    @Value("${prop.img-folder}")
    private String IMG_FOLDER;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        System.out.println("UPLOAD_FOLDER:" + UPLOAD_FOLDER);
        System.out.println("IMG_FOLDER:" + IMG_FOLDER);

        // addResourceHandler是指你想在url请求的路径
        // addResourceLocations是图片存放的真实路径
        // 访问路径如：http://IP:端口/myImage/getImage/XXX.png
        // file作用 指定文件传输协议 获取电脑文件一般都是file
        registry.addResourceHandler(IMG_FOLDER + "**").addResourceLocations("file:" + UPLOAD_FOLDER);
        super.addResourceHandlers(registry);
    }
}

