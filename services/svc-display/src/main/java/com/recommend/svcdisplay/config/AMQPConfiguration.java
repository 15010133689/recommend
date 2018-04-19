package com.recommend.svcdisplay.config;

import com.rabbitmq.client.Channel;
import com.recommend.svcdisplay.saga.UserSaga;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.config.SagaConfiguration;
import org.axonframework.messaging.interceptors.TransactionManagingInterceptor;
import org.axonframework.serialization.Serializer;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.slf4j.Logger;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.transaction.Transactional;


import static org.slf4j.LoggerFactory.getLogger;

@Configuration
public class AMQPConfiguration {
    private static final Logger LOGGER = getLogger(AMQPConfiguration.class);
    @Value("${axon.amqp.exchange}")
    private String exchangeName;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public Queue queue(){
        return new Queue("userQueue", true);
    }
    @Bean
    public Exchange exchange(){
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }
    @Bean
    public Binding queueBinding() {
        return BindingBuilder.bind(queue()).to(exchange()).with("").noargs();
    }
    @Bean
    public SpringAMQPMessageSource queueMessageSource(Serializer serializer){
        return new SpringAMQPMessageSource(serializer){
            @RabbitListener(queues = "userQueue")
            @Override
            @Transactional
            public void onMessage(Message message, Channel channel) throws Exception {
                LOGGER.debug("Message received: "+message.toString());
                System.out.println("Message received######################: "+message.toString());
                super.onMessage(message, channel);
            }
        };
    }
    @Bean
    public SagaConfiguration<UserSaga> userSagaConfiguration(Serializer serializer){
        SagaConfiguration<UserSaga> sagaConfiguration = SagaConfiguration.subscribingSagaManager(UserSaga.class, c-> queueMessageSource(serializer));
        //sagaConfiguration.registerHandlerInterceptor(c->transactionManagingInterceptor());
        return sagaConfiguration;
    }
    @Bean
    public TransactionManagingInterceptor transactionManagingInterceptor(){
        return new TransactionManagingInterceptor(new SpringTransactionManager(transactionManager));
    }

}
