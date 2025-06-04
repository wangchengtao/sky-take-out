package com.summer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@Slf4j
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        log.info("项目启动完成");


    }
}
