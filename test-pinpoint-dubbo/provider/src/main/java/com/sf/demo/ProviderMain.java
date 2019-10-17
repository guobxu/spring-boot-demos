package com.sf.demo;

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
@ImportResource(locations = {
        "classpath:dubbo-config.xml",
})
public class ProviderMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProviderMain.class);

    public static void main(String[] args) {
        SpringApplication.run(ProviderMain.class, args);

        LOGGER.info("test-pinpoint-dubbo provider starts successfully.");
    }

}
