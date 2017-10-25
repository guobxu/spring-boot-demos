package me.codetalk.demo.springboot.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;

@ComponentScan(basePackages = {
		"me.codetalk.demo.springboot.kafka",
		"me.codetalk.demo.springboot.runner",
})
@SpringBootApplication
@EnableKafka
public class DemoApp {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(DemoApp.class, args);
		
		System.in.read();
	}
	
}
