package me.codetalk.demo.auth.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(value = {
		"me.codetalk.demo.auth.service",
		"me.codetalk.demo.auth.controller",
})
public class AuthMain {

	public static void main(String[] args) {
		SpringApplication.run(AuthMain.class, args);
	}
	
}
