package me.codetalk.demo.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ComponentScan(value = {
		"me.codetalk.demo.proxy",
})
@ImportResource("classpath:dubbo-config.xml")
public class DubboClientMain {

	public static void main(String[] args) throws Exception {
		
        SpringApplication.run(DubboClientMain.class, args);
        
    }
	
}
