package com.togo.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * <p>MQ发送器, 需要明确指出MQ所在平台地址, exchange. 用户需要自定义发送方法, 方法中包括使用的template, exchange等</p>
 *
 * <p>
 * <code>private void send(ExchangeEnums exchangeEnums, RoutingKeyEnums routingKeyEnums, Object object, AmqpTemplate amqpTemplate)</code>
 * 是底层发送方法, 用户需要在此基础上定义自己的发送方法, 区别就是template的connectionFactory不同, 如
 * <code>private void sendToNewCluster(ExchangeEnums exchangeEnums, RoutingKeyEnums routingKeyEnums, Object object)</code>
 * 该方法表示出房的新集群MQ, 使用rentMQTemplate作为template.
 * <p>
 * 在此基础上, 用户需要定义自己的exchange, 如
 * <code>public void sendToRentSign(Object object) {
 * sendToNewCluster(ExchangeEnums.RESERVE_TO_SIGN, RoutingKeyEnums.RESERVE_TO_SIGN, object);
 * }
 * </code>
 *
 * <p>
 * 方法命名
 * sendSthOnSth
 *
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author taiyn
 * @version 1.0
 * @date Created in 2019年12月08日 14:44
 * @since 1.0
 */
@Component
public class MQSender {

    @Autowired
    private AmqpTemplate myMQTemplate;

    public void send(String exchangeName, String routingKey, Object object) {

        myMQTemplate.convertAndSend(exchangeName, routingKey, object);
    }


}