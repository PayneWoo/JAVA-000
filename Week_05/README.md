# 框架

## Spring

### Spring Bean 的装配方式总结

Spring 版本 | 装配方式
-------- | -----
1.0 | 通过 XML 装配 bean
2.0  | 使用 @Autowired 自动装配bean
2.0  | 通过 @ComponentScan 注解，开启组件扫描，查找带有 @Component 注解的类，并在 Spring 中自动为其创建一个 bean
3.0  | 通过 @Configuration 注解自动来装配 bean
3.1  | 通过 @Profile 注解条件化 bean
4.0  | 通过 @Conditional 注解条件化 bean

[Spring Bean 的装配方式总结-代码地址](https://github.com/PayneWoo/JAVA-000/blob/0f729658c5fead6673bbf636c430b3024bd13b19/Week_05/SpringAop/src/test/java/LoadBeanTest.java)

### 使用 JDBC 原生方式操作数据

[原生JDBC操作数据库-代码地址](https://github.com/PayneWoo/JAVA-000/blob/0f729658c5/Week_05/SpringAop/src/main/java/com/payne/util/JdbcUtil.java)

[Hikari优化数据库连接-代码地址](https://github.com/PayneWoo/JAVA-000/blob/0f729658c5/Week_05/SpringAop/src/main/java/com/payne/util/HikariUtil.java)