package com.togo;

import com.rabbitmq.client.*;


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
 * @date Created in 2019年09月23日 20:52
 * @since 1.0
 */
public class Recv {

    private static final String QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        int prefetchCount = 1;
        // 一次只发送一个信息给消费者，消费者在处理完之前不回再被发送信息
        channel.basicQos(prefetchCount);
        boolean durable = true;
        channel.queueDeclare(QUEUE_NAME,durable, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {

            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");

            try {
                doWork(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(" [x] Done");
            }
        };

        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, consumerTag -> {});
    }

    private static void doWork(String task) throws InterruptedException {

        for (char ch : task.toCharArray()) {

            if (ch == '.')
                Thread.sleep(1000);
        }
    }
}