package com.example.springrabbitMQexample.consume;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQListner implements MessageListener {

    // MessageListener => This class is responsible for getting the message from the RabbitMQ queue.
    @Override
    public void onMessage(Message message) {
        System.out.println("Consuming Message - " + new String(message.getBody()));
    }
}
