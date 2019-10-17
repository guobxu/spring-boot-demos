package me.codetalk.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Creator: 01380994
 * Date: 2019/3/11
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "me.codetalk.demo.controller",
})
public class DemoMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoMain.class);

    public static void main(String[] args) {
        SpringApplication.run(DemoMain.class, args);

        LOGGER.info("web started...");
    }

}
