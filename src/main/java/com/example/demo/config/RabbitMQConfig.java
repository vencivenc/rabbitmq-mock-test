package com.example.demo.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class RabbitMQConfig {
    private ConnectionFactory rabbitConnectionFactory;

    private static final String topicExchangeName = "exchange";

    public static final String queueName = "queue";

    private static final String routing = "exchange.queue.test";

    public RabbitMQConfig(ConnectionFactory rabbitConnectionFactory) {
        this.rabbitConnectionFactory = rabbitConnectionFactory;
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    public List<Declarable> topicBindings(TopicExchange exchange) {
        Queue queue = new Queue(queueName, true);

        TopicExchange topicExchange = new TopicExchange(topicExchangeName);

        return Arrays.asList(
                queue,
                topicExchange,
                BindingBuilder
                        .bind(queue)
                        .to(exchange).with(routing));
    }

    @Bean(name = "rabbitTemplate")
    public RabbitTemplate exchangeTemplate() {
        RabbitTemplate r = new RabbitTemplate(rabbitConnectionFactory);
        r.setExchange(topicExchangeName);
        r.setConnectionFactory(rabbitConnectionFactory);
        return r;
    }

    @Bean
    public RabbitMessagingTemplate rabbitMessagingTemplate() {
        return new RabbitMessagingTemplate(exchangeTemplate());
    }
}

