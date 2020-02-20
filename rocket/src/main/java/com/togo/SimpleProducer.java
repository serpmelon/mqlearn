package com.togo;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
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

        Message msg = new Message("taiynTopic" /* Topic */,
                ("Hello RocketMQ ").getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
        );

        SendResult sendResult = producer.send(msg);
        System.out.println(sendResult);

        producer.shutdown();
    }

    public void asyncSend() throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("group_two");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);

        Message message = new Message("Tp", "TagA", "Order", "Hello".getBytes());
        producer.send(message, new SendCallback() {
            public void onSuccess(SendResult sendResult) {
                System.out.println(sendResult);
            }

            public void onException(Throwable e) {
                System.out.println(e);
            }
        });
    }

    public static void main(String[] args) throws Exception {

        SimpleProducer simpleProducer = new SimpleProducer();
        simpleProducer.syncSend();
        simpleProducer.asyncSend();
    }
}
