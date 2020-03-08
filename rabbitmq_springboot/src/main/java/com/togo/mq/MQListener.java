package com.togo.mq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Author taiyn
 * @Description 签章PDF监听, 当生成PDF会通知, 接收消息插入到签章表
 * @Date 2:41 下午 2020/2/26
 **/
@Component
public class MQListener {

    @RabbitListener(containerFactory = "listenerFactoryWithManualAck",
            queues = "task_queue")
    public void consume(Message message, Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {

        System.out.println("message: " + message.toString());

        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        System.out.println("message body: " + msg);
    }
}
