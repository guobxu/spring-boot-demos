package com.sf.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.io.IOException;

/**
 * Creator: 01380994
 * Date: 2020/4/10
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.sf.demo.service",
        "com.sf.demo.locale",
})
@ImportResource(locations = {
        "classpath:dubbo-config.xml",
})
public class ServerMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerMain.class);

    public static void main(String[] args) throws IOException {
        SpringApplication.run(ServerMain.class, args);

        System.in.read();
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:locale/messages");
        messageSource.setCacheSeconds(3600);
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }

}