package com.togo;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @Author taiyn
 * @Description TODO
 * @Date 8:57 下午 2020/2/18
 **/
public class SimpleProducer {

    public void syncSend() throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("group_one");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        Message msg = new Message("topicTest" /* Topic */,
                ("Hello RocketMQ ").getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
        );

        SendResult sendResult = producer.send(msg);
        System.out.println(sendResult);

        producer.shutdown();
    }

    public static void main(String[] args) throws Exception {

        SimpleProducer simpleProducer = new SimpleProducer();
        simpleProducer.syncSend();
    }
}
