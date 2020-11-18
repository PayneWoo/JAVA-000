# 框架

## Spring

### Spring Bean 的装配方式总结

Spring 版本 | 装配方式
-------- | -----
2.0  | 使用 @Autowired 自动装配bean
1.0 | 通过 XML 装配 bean
3.0  | 通过 @Configuration 注解自动来装配 bean
2.0  | 通过 @ComponentScan 注解，开启组件扫描，查找带有 @Component 注解的类，并在 Spring 中自动为其创建一个 bean
3.1  | 通过 @Profile 注解条件化 bean
4.0  | 通过 @Conditional 注解条件化 bean


