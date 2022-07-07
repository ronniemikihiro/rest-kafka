package br.com.consumer.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfiguration {

    private final String topicNameUsers;
    private final String topicNameCars;

    public KafkaTopicConfiguration(@Value("${topic.name.users}") String topicNameUsers,
                                   @Value("${topic.name.cars}") String topicNameCars) {
        this.topicNameUsers = topicNameUsers;
        this.topicNameCars = topicNameCars;
    }

    @Bean
    public NewTopic createTopicUsers() {
        return new NewTopic(topicNameUsers, 3, (short) 1);
    }

    @Bean
    public NewTopic createTopicCars() {
        return new NewTopic(topicNameCars, 3, (short) 1);
    }
}
