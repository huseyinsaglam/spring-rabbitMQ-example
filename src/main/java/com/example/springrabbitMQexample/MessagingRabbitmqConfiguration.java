package com.example.springrabbitMQexample;

import com.example.springrabbitMQexample.consume.RabbitMQListner;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingRabbitmqConfiguration {

    @Value("${rabbit.queuname}")
    private String queueName;

    @Value("${rabbit.binding}")
    private String nicknameBinding;

    @Value("${rabbit.exchange}")
    private String nicknameExchange;

    @Bean
    Queue queue() {
        return new Queue(queueName);
    }

    @Bean
    TopicExchange topicExchange(){
        return new TopicExchange(nicknameExchange);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
    }

    // create MessageListenerContainer using default connection factory
    // Spring MessageListenerContainer, Mesaj Odaklı EJB'nin yerini almıştır.
    // AMQ konu/sırasıyla bir bağlantı kurulur, bu konudan/sıradan mesajlar alır ve bunları MessageListener'ınıza besler
    @Bean
    MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setQueues(queue());
        simpleMessageListenerContainer.setMessageListener(new RabbitMQListner());
        return simpleMessageListenerContainer;
    }

    // Varsayılan bağlantı fabrikasını kullanmak istemiyorsak, kendi CachingConnectionFactory'mizi oluşturabilir ve kullanabiliriz.
    //create custom connection factory
	/*@Bean
	ConnectionFactory connectionFactory() {
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
		cachingConnectionFactory.setUsername(username);
		cachingConnectionFactory.setUsername(password);
		return cachingConnectionFactory;
	}*/

    //create MessageListenerContainer using custom connection factory
	/*@Bean
	MessageListenerContainer messageListenerContainer() {
		SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
		simpleMessageListenerContainer.setConnectionFactory(connectionFactory());
		simpleMessageListenerContainer.setQueues(queue());
		simpleMessageListenerContainer.setMessageListener(new RabbitMQListner());
		return simpleMessageListenerContainer;

	}*/

}
