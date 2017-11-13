package me.codetalk.demo.mysql.utf8mb4.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

// 配置启动VM参数: -Ddruid.mysql.usePingMethod=false
@SpringBootApplication
@ComponentScan(value = {
		"me.codetalk.demo.mysql.utf8mb4.controller",
})
@MapperScan(value = {"me.codetalk.demo.mysql.utf8mb4.mapper"})
public class DemoMain {
	
    public static void main(String[] args) throws Exception {
        SpringApplication.run(DemoMain.class, args);
    }

}