package com.sf.demo;

import com.sf.demo.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.Resource;

/**
 * Creator: 01380994
 * Date: 2019/2/11
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.sf.demo.config",
})
public class DemoMain implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoMain.class);

    @Resource(name = "appCache")
    private Cache appCache;

    @Resource(name = "txmgrCache")
    private Cache txmgrCache;

    public static void main(String[] args) {
        SpringApplication.run(DemoMain.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Long val1 = appCache.incr("key1");
        Long val2 = txmgrCache.incr("key1");

        LOGGER.info("val1 from appCache = {}", val1);
        LOGGER.info("val2 from txmgrCache = {}", val2);
    }

}
