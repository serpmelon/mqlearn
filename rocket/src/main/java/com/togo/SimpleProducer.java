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

//    private static DefaultMQProducer producer = new DefaultMQProducer("group_one");
//
//    static {
//        try {
//            producer.setNamesrvAddr("localhost:9876");
//            producer.start();
//        } catch (MQClientException e) {
//            e.printStackTrace();
//        }
//    }

    public void syncSend() throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        try {

            // Specify name server addresses.
            producer.setNamesrvAddr("localhost:9876");
            producer.setSendMsgTimeout(30000);
            //Launch the instance.
            producer.start();
            Message msg = new Message("TopicTest" /* Topic */,
                    "TagA" /* Tag */,
                    ("Hello RocketMQ ").getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );

            SendResult sendResult = producer.send(msg);
            System.out.println(sendResult);
        } catch (Exception e) {
            e.printStackTrace();
            producer.shutdown();
        }
    }

    public void asyncSend() throws Exception {

//        producer.setRetryTimesWhenSendAsyncFailed(0);
//
//        Message message = new Message("Tp", "TagA", "Order", "Hello".getBytes());
//        producer.send(message, new SendCallback() {
//            public void onSuccess(SendResult sendResult) {
//                System.out.println(sendResult);
//            }
//
//            public void onException(Throwable e) {
//                System.out.println(e);
//            }
//        });
    }

    public static void main(String[] args) throws Exception {

        SimpleProducer simpleProducer = new SimpleProducer();
        simpleProducer.syncSend();
//        simpleProducer.asyncSend();

//        simpleProducer.shutdown();
    }
}
