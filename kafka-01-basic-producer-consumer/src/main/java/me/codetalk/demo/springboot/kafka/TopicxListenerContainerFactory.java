package me.codetalk.demo.springboot.kafka;

import org.springframework.kafka.config.AbstractKafkaListenerEndpoint;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpoint;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.TopicPartitionInitialOffset;
import org.springframework.stereotype.Component;

@Component("topicxContainerFactory")
public class TopicxListenerContainerFactory extends ConcurrentKafkaListenerContainerFactory<String, String> {

	@Override
	protected ConcurrentMessageListenerContainer<String, String> createContainerInstance(KafkaListenerEndpoint endpoint) {
		TopicPartitionInitialOffset topicPartitions1 = new TopicPartitionInitialOffset("topicx", 0);
		TopicPartitionInitialOffset topicPartitions2 = new TopicPartitionInitialOffset("topicx", 1);
		TopicPartitionInitialOffset topicPartitions3 = new TopicPartitionInitialOffset("topicx", 2);
		TopicPartitionInitialOffset topicPartitions4 = new TopicPartitionInitialOffset("topicx", 3);
		((AbstractKafkaListenerEndpoint<String, String>) endpoint).setTopicPartitions(topicPartitions1, topicPartitions2, topicPartitions3, topicPartitions4);
		
		return super.createContainerInstance(endpoint);
	}
	
}
