package com.example.demo;

import com.example.demo.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @RabbitListener(queues = RabbitMQConfig.queueName, concurrency = "4-16")
    public void receiveCreateNotificationMessage(final String message) {
        System.out.println("Received message");
        System.out.println(message);
    }
}
