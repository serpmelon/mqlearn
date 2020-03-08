package com.togo.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>MQ配置文件, 主要负责connectionFactory, template的配置</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author taiyn
 * @version 1.0
 * @date Created in 2019年12月08日 14:50
 * @since 1.0
 */
@Configuration
public class RabbitConfig {

    @Bean(value = "connectionFactory")
    @Primary
    public ConnectionFactory connectionFactory(
            @Value("${spring.rabbitmq.host}") String host,
            @Value("${spring.rabbitmq.port}") int port,
            @Value("${spring.rabbitmq.username}") String username,
            @Value("${spring.rabbitmq.password}") String password,
            @Value("${spring.rabbitmq.virtual-host}") String virtualHost) {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);

        return connectionFactory;
    }

    /**
     * @return org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
     * @Author taiyn
     * @Description 新mq集群监听factory
     * @Date 2:40 下午 2020/2/26
     * @Param [newRentConnectionFactory]
     **/
    @Bean(value = "listenerFactoryWithAutoAck")
    public SimpleRabbitListenerContainerFactory listenerFactoryWithAutoAck(@Qualifier("connectionFactory") ConnectionFactory newRentConnectionFactory) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(newRentConnectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    @Bean(value = "listenerFactoryWithManualAck")
    public SimpleRabbitListenerContainerFactory listenerFactoryWithManualAck(@Qualifier("connectionFactory") ConnectionFactory newRentConnectionFactory) {

        SimpleRabbitListenerContainerFactory factory = listenerFactory(newRentConnectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    private SimpleRabbitListenerContainerFactory listenerFactory(ConnectionFactory newRentConnectionFactory) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(newRentConnectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        return factory;
    }

    @Bean
    public RetryTemplate retryTemplate() {

        RetryTemplate retryTemplate = new RetryTemplate();
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(500);
        backOffPolicy.setMultiplier(10.0);
        backOffPolicy.setMaxInterval(10000);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        return retryTemplate;
    }

    @Bean
    public AmqpTemplate rentMQTemplate(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {

        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setRetryTemplate(retryTemplate());
        template.setMessageConverter(new Jackson2JsonMessageConverter());

        return template;
    }
}