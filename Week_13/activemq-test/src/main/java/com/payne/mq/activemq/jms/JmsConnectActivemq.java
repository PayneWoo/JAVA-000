package com.payne.mq.activemq.jms;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

import javax.jms.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Payne
 * @date 2021/1/16
 */
public class JmsConnectActivemq {


    /**
     * 测试 activemq
     * @param destination 指明消息被发送的目的地以及客户端接收消息的来源。JMS 中使用两种目的地，队列 Queue 和 话题 Topic
     * @param brokerUrl ActiveMQ 实例所支持的服务协议对外暴露的连接URL
     */
    public static void testDestination(Destination destination, String brokerUrl) {
        try {
            // 创建连接和会话
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerUrl);
            ActiveMQConnection conn = (ActiveMQConnection) factory.createConnection();
            conn.start();
            Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // 创建消费者
            MessageConsumer consumer = session.createConsumer(destination);
            final AtomicInteger count = new AtomicInteger(0);
            MessageListener listener = new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        // 打印所有的消息内容
                        System.out.println(count.incrementAndGet() + " => receive from " + destination.toString() + ": " + message);

                        // 前面所有未被确认的消息全部都确认。
                        // message.acknowledge();

                    } catch (Exception e) {
                        // 生产环境不要吞任何这里的异常。应该向外抛出异常，表明这个消息没有被成功消费
                        e.printStackTrace();
                    }
                }
            };

            // 绑定消息监听器，MQ 会将消息推送给消费者
            consumer.setMessageListener(listener);

            // 如果使用 consumer.receive() 则表明，消费者主动从 MQ 拉消息

            // 创建生产者，生产100个消息
            MessageProducer producer = session.createProducer(destination);
            int index = 0;
            while (index++ < 100) {
                TextMessage message = session.createTextMessage(index + " message.");
                producer.send(message);
            }

//            Thread.sleep(2000);
//            // 关闭会话
//            session.close();
//            // 关闭连接工厂
//            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
