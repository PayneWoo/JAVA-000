



## ActiveMQ

## 1. 搭建 ActiveMQ 服务,基于 JMS 分别实现对于 queue 和 topic 的消息生产和消费

Mac 上安装 activemq 非常的方便，通过 brew 即可安装

```
// 安装 activemq
brew install activemq

// 查看 activemq 版本
activemq --version

// 启动 activemq
activemq start

```

这里我安装的是 5.16.0 的版本

![ActiveMQ 搭建](https://i.loli.net/2021/01/16/vUEHX3Lwbgiq8yC.png)

浏览器访问：http://localhost:8161/ 点击 Manager ActiveMQ boker 输入用户名/密码：admin/admin 就可以进入  activemq 的web 控制台页面。因为 activemq 自带了一个 jetty 实现了控制台这个功能，jetty 对外的端口是 8161，具体可以在 jetty.xml 看到

![activemq web 控制台](https://i.loli.net/2021/01/16/osp68UD3gSie2WO.png)

![jetty配置](https://i.loli.net/2021/01/16/VC7i5PEgOshFkdZ.png)


【JMS 分别实现对于 queue 和 topic 的消息生产和消费】 代码见 JmsConnectActivemq.Java

## 2.（选做）基于数据库的订单表，模拟消息队列处理订单：

一个程序往表里写新订单，标记状态为未处理 (status=0);另一个程序每隔 100ms 定时从表里读取所有 status=0 的订单，打印一下订单数据，然后改成完成 status=1；
（挑战☆）考虑失败重试策略，考虑多个消费程序如何协作。

待完成

## 3.（选做）将上述订单处理场景，改成使用 ActiveMQ 发送消息处理模式。
待完成

## 4.（选做）使用 java 代码，创建一个 ActiveMQ Broker Server，并用 JMS 测试连接 ActiveMQ Broker Server。

创建 Broker 代码见 ActivemqBroker.java
测试代码见 ActivemqTestApplicationTests.java:testJmsConnectBroker()

### 创建一个 ActiveMQ Broker

1).Broker 相当于一个 ActiveMQ 实例。首先需要在项目中引入 xbean-spring 依赖项。

```java
        <!-- 使用Spring及XBean来创建一个内置的broker -->
        <dependency>
            <groupId>org.apache.xbean</groupId>
            <artifactId>xbean-spring</artifactId>
            <version>4.18</version>
        </dependency>
```



2).在 61617 端口启动一个 ActiveMQ Broker

```Java
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
```



3).使用 JMS 连接 ActiveMQ Broker

```java
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
```

## 5.（挑战☆☆）搭建 ActiveMQ 的 network 集群和 master-slave 主从结构。
待完成

## 6.（挑战☆☆☆）基于 ActiveMQ 的 MQTT 实现简单的聊天功能或者 Android 消息推送。
待完成

## 7.（挑战☆）创建一个 RabbitMQ，用 Java 代码实现简单的 AMQP 协议操作。
待完成

## 8.（挑战☆☆）搭建 RabbitMQ 集群，重新实现前面的订单处理。
待完成

## 9.（挑战☆☆☆）使用 Apache Camel 打通上述 ActiveMQ 集群和 RabbitMQ 集群，实现所有写入到 ActiveMQ 上的一个队列 q24 的消息，自动转发到 RabbitMQ。
待完成

## 10.（挑战☆☆☆）压测 ActiveMQ 和 RabbitMQ 的性能。
待完成



