package me.codetalk.demo.main;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import me.codetalk.cache.service.ICacheService;
import me.codetalk.messaging.MesgObj;
import me.codetalk.messaging.MessagingUtil;
import me.codetalk.messaging.redis.pub.IMessagePublisher;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {
		"me.codetalk.cache.config",
		"me.codetalk.cache.service.impl",
		"me.codetalk.messaging.redis.pub.impl",
})
public class DemoApp {

	private static final Logger LOGGER = LoggerFactory.getLogger(DemoApp.class);
	
	@Autowired  
    private ICacheService cacheService; 
	
	@Autowired
	private IMessagePublisher mesgPublisher;
	
    private Random rand = new Random();
    
    @Scheduled(fixedDelay = 6 * 1000)
    public void testCache(){  
    	cacheService.set("a", rand.nextInt(1000));  
    }
	
    @Scheduled(fixedDelay = 5 * 1000)
    public void testMesgSend(){
    	String text = "Bar " + rand.nextInt(100);
    	MesgObj msgobj = MessagingUtil.convertAsMesgObj("Foo Mesg", text);
    	
    	mesgPublisher.sendMessage("Foo", msgobj);
    }
    
	public static void main(String[] args) throws Exception {
		SpringApplication.run(DemoApp.class);
	}

//	@Scheduled(fixedDelay = 6 * 1000 * 1000)
//	public void testCache() {
//		cacheService.set("Hello", "Foo X");
//	}
	
//	@Scheduled(fixedDelay = 6 * 1000)
//	public void testMesgSend() {
//		String mesgType = "Hello", channel = "Hello";
//		Map<String, String> mesg = new HashMap<>();
//		mesg.put("Mesg", "Foo " + rand.nextInt(100));
//		
//		MesgObj msgobj = MessagingUtil.convertAsMesgObj(mesgType, mesg);
//		
//		LOGGER.info("Send message: " + mesg);
//		mesgPublisher.sendMessage(channel, msgobj);
//	}
	
}
















