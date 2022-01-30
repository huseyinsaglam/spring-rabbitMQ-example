package com.example.springrabbitMQexample;

import com.example.springrabbitMQexample.consume.RabbitMQListner;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingRabbitmqConfiguration {

/*    @Value("${rabbit.queuname}")
    private String queueName;

    @Value("${rabbit.binding}")
    private String nicknameBinding;

    @Value("${rabbit.exchange}")
    private String nicknameExchange;*/

    @Value("${rabbit.direct.queuname}")
    private String nicknameQueueDirect;

    @Value("${rabbit.direct.binding}")
    private String nicknameBindingDirect;

    @Value("${rabbit.direct.exchange}")
    private String nicknameExchangeDirect;

    @Bean
    Queue queue() {
        return new Queue(nicknameQueueDirect);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(nicknameExchangeDirect);
    }

    @Bean
    public Binding binding(final Queue queue, final DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with(nicknameBindingDirect);
    }

    // create MessageListenerContainer using default connection factory
    // Spring MessageListenerContainer, Mesaj Odaklı EJB'nin yerini almıştır.
    // AMQ konu/sırasıyla bir bağlantı kurulur, bu konudan/sıradan mesajlar alır ve bunları MessageListener'ınıza besler
    // mesajı kuyruktan okurken
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

    // kuyruga mesaj atarken
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
