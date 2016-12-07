package com.expedia.config;

import com.expedia.service.MailReceiver;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    @Bean
    public Queue hello() {
        return new Queue(Constants.QUEUE_NAME);
    }

    //@Profile("receiver")
    @Bean
    public MailReceiver receiver() {
        return new MailReceiver();
    }
}
