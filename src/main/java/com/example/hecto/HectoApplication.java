package com.example.hecto;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.hecto.mapper")
public class HectoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HectoApplication.class, args);
    }

}
