package com.example.filesystem;

import com.example.filesystem.controller.FileUploadController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;


@SpringBootApplication
public class FilesystemApplication{

    private static final Logger logger = LoggerFactory.getLogger(FilesystemApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FilesystemApplication.class, args);
    }

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        try {
            factory.setLocation("D:/Program Files/javaFileUpload/tomcatTemporary");
        }catch (Exception e){
            logger.error("上传文件临时目录不存在:" + e.getMessage());
            e.printStackTrace();
        }
        return factory.createMultipartConfig();
    }
}
