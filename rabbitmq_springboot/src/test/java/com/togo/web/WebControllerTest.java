package com.togo.web;

import com.togo.BaseTest;
import com.togo.mq.MQSender;
import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class WebControllerTest extends BaseTest {

    @Autowired
    private MQSender mqSender;

    @Test
    public void send() {

        mqSender.send("e.send", "r.send", "send a message");
    }
}