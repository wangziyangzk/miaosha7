package com.wzy.miaosha7;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan(value = "com.wzy.miaosha7.dao")
public class Miaosha7Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Miaosha7Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Miaosha7Application.class);
    }
}
