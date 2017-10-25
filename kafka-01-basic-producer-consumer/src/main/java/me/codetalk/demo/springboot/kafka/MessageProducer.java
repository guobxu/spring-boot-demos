package me.codetalk.demo.springboot.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class MessageProducer {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageProducer.class);
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	public void send(String topic, String key, String mesg) {
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, key, mesg);
		future.addCallback(new ListenableFutureCallback<SendResult<String, String>> () {

			public void onSuccess(SendResult<String, String> result) {
				LOGGER.info("success send, key = " + key);
			}

			public void onFailure(Throwable ex) {
				LOGGER.error("error send message, key = " + key + ", data = " + mesg);
				LOGGER.error(ex.getMessage(), ex);
			}
			
		});
	}
	
}
