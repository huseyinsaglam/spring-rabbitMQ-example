package com.example.springrabbitMQexample.procuder;

import com.example.springrabbitMQexample.model.Employee;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbit.direct.exchange}")
    private String exchange;

    @Value("${rabbit.direct.binding}")
    private String routingkey;

    public void send(Employee employee) {
        rabbitTemplate.convertAndSend(exchange, routingkey, employee);
        System.out.println("Send msg = " + employee);

    }
}
