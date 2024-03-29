# cloud-notebook
云上笔记本

## 简介
该项目是一个支持多人协作、 Markdown 语法的线上笔记本（Web 应用），满足大学生记录学习经验、
开发笔记等。
使用 spring cloud alibaba 架构（仅用于学习微服务架构）

## 技术栈
Java8, Spring, SpringMVC, Mybatis, Mybatis-plus, SpringBoot, spring cloud Alibaba, GateWay, MySQL, Redis, RabbitMQ, JWT 等。

## 技术点

- 应用 Spring AOP 封装权限拦截器，并匹配到自定义注解，用于系统接口的权限认证，避免重复代码的编写
- 使用阿里云短信服务实现用户手机登录，并使用 RabbitMQ 异步实现短信发送，提高服务可用性
- 使用 Redis 做缓存，保存用户登录信息和常用查询数据，提高查询效率，为数据库减轻压力，同时使用
- ThreadLocal 保存用户登录信息，并配置拦截器自动刷新用户信息缓存的有效期
- 通过分布式锁解决了保存笔记线程安全问题，配合使用浏览器本地缓存历史版本的笔记，既解决了线程安全问
题，也不会丢失其他用户的编辑版本。
- 使用 gateway 进行系统间调用


