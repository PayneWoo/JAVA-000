## Week07-读写分离

### 读写分离 - 动态切换数据源版本 1.0

1.0: 基于 springboot 配置多个数据源（master，slave）.不同的service注入不同的数据源。

[配置多个数据源，不同 service 注入不同的数据源](https://github.com/PayneWoo/JAVA-000/tree/master/Week_07/multi-data-source)


1.1: 基于 AbstractRoutingDataSource 或者 自定义注解 readOnly 实现自动切换数据源。
[AbstractRoutingDataSource 结合 AOP 实现自动切换数据源](https://github.com/PayneWoo/JAVA-000/tree/master/Week_07/abstract-routing-data-source)


### 读写分离 - 数据库框架版本 2.0
使用 ShardingSphere-jdbc 实现读写分离配置（ShardingSphere-jdbc的Master-Slave功能）

[ShardingSphere-jdbc 配置读写分离，不侵入业务代码](https://github.com/PayneWoo/JAVA-000/tree/master/Week_07/shardingsphere-jdbc-demo)

