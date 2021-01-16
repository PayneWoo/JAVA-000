package com.payne.mq.activemq.ActivemqBroker;

import org.apache.activemq.broker.BrokerService;

import static java.lang.Thread.sleep;

/**
 * 启动一个ActiveMQ broker server
 * @author Payne
 * @date 2021/1/16
 */
public class ActivemqBroker {

    public static void main(String[] args) throws Exception {

        BrokerService broker = new BrokerService();
        broker.setUseJmx(true);
        // 设置broker名字
        broker.setBrokerName("MyBroker");
        // 是否使用持久化
        broker.setPersistent(false);
        broker.addConnector("tcp://localhost:61617");
        broker.start();
        sleep(100000);
    }

}
