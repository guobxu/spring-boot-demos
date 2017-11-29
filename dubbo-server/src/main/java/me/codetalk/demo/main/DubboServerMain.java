package me.codetalk.demo.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ComponentScan(value = {
		"me.codetalk.demo.service.impl",
})
@ImportResource("classpath:dubbo-config.xml")
public class DubboServerMain {

	public static void main(String[] args) throws Exception {
		
        SpringApplication.run(DubboServerMain.class, args);
        
    }
	
}
