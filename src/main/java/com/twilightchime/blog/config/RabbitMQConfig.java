package com.twilightchime.blog.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitMQConfig {
    // 常量定义
    public static final String BLOG_EXCHANGE = "blog.exchange";
    public static final String BLOG_PUBLISH_QUEUE = "blog.publish.queue";
    public static final String BLOG_DELETE_QUEUE = "blog.delete.queue";
    public static final String BLOG_PUBLISH_KEY = "blog.publish";
    public static final String BLOG_DELETE_KEY = "blog.delete";

    //交换机
    @Bean
    public TopicExchange blogExchange() {
        return new TopicExchange(BLOG_EXCHANGE, true, false);
        // durable=true 持久化，autoDelete=false 不自动删除
    }

    //队列
    @Bean
    public Queue blogPublishQueue() {
        return QueueBuilder.durable(BLOG_PUBLISH_QUEUE)
                .withArgument("x-dead-letter-exchange", "blog.dlx.exchange")    // 死信交换机
                .withArgument("x-dead-letter-routing-key", "blog.dlx")
                .withArgument("x-message-ttl", 60000)   // 消息 60 秒过期
                .build();
    }

    @Bean
    public Queue blogDeleteQueue() {
        return QueueBuilder.durable(BLOG_DELETE_QUEUE).build();
    }

    // 绑定
    @Bean
    public Binding blogPublishBinding() {
        return BindingBuilder.bind(blogPublishQueue()).to(blogExchange()).with(BLOG_PUBLISH_KEY);
    }

    @Bean
    public Binding blogDeleteBinding() {
        return BindingBuilder.bind(blogDeleteQueue()).to(blogExchange()).with(BLOG_DELETE_KEY);
    }

    // 死信队列
    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange("blog.dlx.exchange");
    }

    @Bean
    public Queue dlxQueue() {
        return QueueBuilder.durable("blog.dlx.queue").build();
    }

    @Bean
    public Binding dlxBinding() {
        return BindingBuilder.bind(dlxQueue()).to(dlxExchange()).with("blog.dlx");
    }

    // JSON 消息转换器
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // RabbitTemplate 配置
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());

        // 生产者确认回调
        template.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                log.error("消息发送到 Exchange 失败: {}, 原因: {}", correlationData, cause);
            }
        });

        // 消息路由失败回调
        template.setReturnsCallback(returned -> {
            log.error("消息路由到 Queue 失败: {}", returned);
        });

        return template;
    }
}
