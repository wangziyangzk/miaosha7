package com.wzy.miaosha7;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.wzy.miaosha7.dao")
public class Miaosha7Application {

    public static void main(String[] args) {
        SpringApplication.run(Miaosha7Application.class, args);
    }

}
