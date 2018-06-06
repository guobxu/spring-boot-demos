package me.codetalk.test.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by guobiao.xu on 2018/6/6.
 */
@SpringBootApplication
@ComponentScan(value = {
        "me.codetalk.test.controller",
})
public class TestMain {

    public static void main(String[] args) {
        SpringApplication.run(TestMain.class, args);
    }

}
