package me.codetalk.demo.proxy.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(value = {
		"me.codetalk.demo.proxy.client",
})
@ComponentScan(value = {
		"me.codetalk.demo.proxy.controller",
		"me.codetalk.demo.proxy.client.config",
})
public class ProxyMain {

	public static void main(String[] args) {
		SpringApplication.run(ProxyMain.class, args);
	}
	
}
