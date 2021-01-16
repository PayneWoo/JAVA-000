package com.payne.mq.activemq;

import com.payne.mq.activemq.jms.JmsConnectActivemq;
import org.apache.activemq.command.ActiveMQTopic;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.Destination;

@RunWith(SpringJUnit4ClassRunner.class)
class ActivemqTestApplicationTests {

    /**
     * 自己启动的 Broker 的连接 URL
     */
    private static final String MY_BROKER_URL = "tcp://127.0.0.1:61617";


    /**
     * 本地 ActiveMQ 对外暴露的 tcp 连接
     */
    private static final String LOCAL_BROKER_URL = "tcp://127.0.0.1:61616";


    /**
     *  使用 JMS 连接本地 ActiveMQ
     */
    @Test
    void testJmsConnectActivemq() {
        // 定义Destination
        Destination destination = new ActiveMQTopic("test.topic");
        // Destination destination = new ActiveMQQueue("test.queue");

        // 测试 JMS 连接 ActiveMQ
        JmsConnectActivemq.testDestination(destination, LOCAL_BROKER_URL);

    }


    /**
     * 使用 JMS 连接 ActiveMQ Broker
     */
    @Test
    void testJmsConnectBroker() {
        // 定义Destination
        Destination destination = new ActiveMQTopic("test.topic");
        // Destination destination = new ActiveMQQueue("test.queue");

        // 测试 JMS 连接 ActiveMQ
        JmsConnectActivemq.testDestination(destination, MY_BROKER_URL);

    }


}
