package me.codetalk.main;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by guobiao.xu on 2018/6/6.
 */
@SpringBootApplication
@EnableDubboConfiguration
@ComponentScan(value = {
        "me.codetalk.controller"
})
public class ConsumerMain {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerMain.class, args);
    }

}
