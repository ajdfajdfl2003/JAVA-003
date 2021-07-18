# Week 11
## 作業

1.（选做）按照课程内容，动手验证 Hibernate 和 Mybatis 缓存。

2.（选做）使用 spring 或 guava cache，实现业务数据的查询缓存。

3.（挑战☆）编写代码，模拟缓存穿透，击穿，雪崩。

4.（挑战☆☆）自己动手设计一个简单的 cache，实现过期策略。

5.（选做）命令行下练习操作 Redis 的各种基本数据结构和命令。

6.（选做）分别基于 jedis，RedisTemplate，Lettuce，Redission 实现 redis 基本操作的 demo，可以使用 spring-boot 集成上述工具。

7.（选做）spring 集成练习:
  - 实现 update 方法，配合 @CachePut

  - 实现 delete 方法，配合 @CacheEvict

  - 将示例中的 spring 集成 Lettuce 改成 jedis 或 redisson

8.**（必做）基于 Redis 封装分布式数据操作：**
  - 在 Java 中实现一个简单的分布式锁；
    - Answer: [task 8 - 1 solution](https://github.com/ajdfajdfl2003/JAVA-003/tree/main/Week_11/task08/lock)    

  - 在 Java 中实现一个分布式计数器，模拟减库存。
    - Answer: [task 8 - 2 solution](https://github.com/ajdfajdfl2003/JAVA-003/tree/main/Week_11/task08/Counter)

9.（必做）基于 Redis 的 PubSub 实现订单异步处理

10.（挑战☆）基于其他各类场景，设计并在示例代码中实现简单 demo：
  - 实现分数排名或者排行榜；

  - 实现全局 ID 生成；

  - 基于 Bitmap 实现 id 去重；

  - 基于 HLL 实现点击量计数；

  - 以 redis 作为数据库，模拟使用 lua 脚本实现前面课程的外汇交易事务。

11.（挑战☆☆）升级改造项目：
  - 实现 guava cache 的 spring cache 适配；

  - 替换 jackson 序列化为 fastjson 或者 fst，kryo；

  - 对项目进行分析和性能调优。

12.（挑战☆☆☆）以 redis 作为基础实现上个模块的自定义 rpc 的注册中心。

---

以上作业，要求 1 道必做题目先提交到自己的 GitHub 上面，再提交到下方表单中：

- https://jinshuju.net/f/q69J8A

作业提交截止时间 7 月 11 日（周日）23:59 前。

作业参考示例地址，由秦老师和助教共建，每周同步更新： https://github.com/JavaCourse00/JavaCourseCodes

GitHub 使用教程： https://u.geekbang.org/lesson/51?article=294701

学号查询方式：PC 端登录 time.geekbang.org, 点击右上角头像进入我的教室，左侧头像下方 G 开头的为学号
