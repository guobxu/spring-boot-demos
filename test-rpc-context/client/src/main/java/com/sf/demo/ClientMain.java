package com.sf.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;

/**
 * Creator: 01380994
 * Date: 2020/4/10
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.sf.demo.controller",
        "com.sf.demo.aop"
})
@ImportResource(locations = {
        "classpath:dubbo-config.xml",
})
@EnableAspectJAutoProxy
public class ClientMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientMain.class);

    public static void main(String[] args) {
        SpringApplication.run(ClientMain.class, args);
    }

}
