package com.aoye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@EnableDiscoveryClient
// 导入tk包
@MapperScan("com.aoye.product.mapper")
public class AoyeProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(AoyeProductApplication.class);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大,单位KB或MB
        factory.setMaxFileSize("500MB");
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("5000MB");
        return factory.createMultipartConfig();
    }
}
