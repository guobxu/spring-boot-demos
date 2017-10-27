package me.codetalk.demo.springboot.kafka;

import java.util.Map;

import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer implements ConsumerSeekAware {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

//	@KafkaListener(topics = "testrep3", groupId = "testrep3-group")
//	@KafkaListener(topics = "topicx", groupId = "topicx-group")
//	@KafkaListener(topics = "topicx", groupId = "topicx-group", topicPartitions = {
//			@org.springframework.kafka.annotation.TopicPartition(topic = "topicx", partitions={"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"})
//	})
//	@KafkaListener(topics = "topicx", groupId = "topicx-group", containerFactory = "topicxContainerFactory")
	@KafkaListener(topics = "topicx", groupId = "${topicx_group}")
	public void receive(String mesg) {
		LOGGER.info("received data='{}'", mesg);
	}

	@Override
	public void registerSeekCallback(ConsumerSeekCallback callback) {

	}

	@Override
	public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
		for(Map.Entry<TopicPartition, Long> kv : assignments.entrySet()) {
			TopicPartition partition = kv.getKey();
			Long offset = kv.getValue();
			
			LOGGER.info("Topic = " + partition.topic() + ", partition = " + partition.partition() + ", offset = " + offset);
		}
	}

	@Override
	public void onIdleContainer(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
		// TODO Auto-generated method stub
		
	}
	
}
