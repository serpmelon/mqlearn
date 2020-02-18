package com.togo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.Scanner;
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
 * @date Created in 2020年02月04日 16:29
 * @since 1.0
 */
public class DebugSend {

    private static final String QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            try (Connection connection = factory.newConnection();
                 Channel channel = connection.createChannel();) {

                boolean durable = true;
                channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
                String message = String.join(" ", "dMessage.......");
                channel.exchangeDeclare("mind", "direct");
                channel.basicPublish("mind", "", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());

                System.out.println(" [x] Sent '" + message + "'");

                System.out.println("continue ?");
                String a = in.nextLine();
                if ("q".equals(a))
                    break;
            }
        }
        in.close();
    }
}