package com.togo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author taiyn
 * @version 1.0
 * @date Created in 2019年09月24日 11:12
 * @since 1.0
 */
public class EmitLogDirect {

    private static final String QUEUE_NAME = "task_queue";
    private static final String EXCHANGE = "logs_direct";

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel();) {

            channel.exchangeDeclare(EXCHANGE, "direct");

            String message = String.join(" ", "dMessage.......");

            channel.basicPublish(EXCHANGE, "orange", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());

            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}