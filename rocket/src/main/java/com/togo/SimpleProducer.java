package com.togo;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * @Author taiyn
 * @Description TODO
 * @Date 8:57 下午 2020/2/18
 **/
public class SimpleProducer {

    public void syncSend() throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        Message msg = new Message()
    }
}
