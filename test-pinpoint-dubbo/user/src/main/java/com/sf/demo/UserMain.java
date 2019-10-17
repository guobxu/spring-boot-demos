package com.sf.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * Creator: 01380994
 * Date: 2019/4/18
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.sf.demo.service",
        "com.sf.demo.rpc",
})
@MapperScan(basePackages = {
        "com.sf.demo.mapper"
})
@ImportResource(locations = {
        "classpath:dubbo-config.xml",
})
public class UserMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserMain.class);

    public static void main(String[] args) {
        SpringApplication.run(UserMain.class, args);

        LOGGER.info("test-pinpoint-dubbo user starts successfully.");
    }

}
