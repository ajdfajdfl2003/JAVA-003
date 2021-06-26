# Week 08
## 作業

1.（选做）分析前面作业设计的表，是否可以做垂直拆分。

2.（必做）设计对前面的订单表数据进行水平分库分表，拆分 2 个库，每个库 16 张表。并在新结构在演示常见的增删改查操作。代码、sql 和配置文件，上传到 Github。
  - Answer: [task 2 solution](https://github.com/ajdfajdfl2003/JAVA-003/tree/main/Week_08/task02)

3.（选做）模拟 1000 万的订单单表数据，迁移到上面作业 2 的分库分表中。

4.（选做）重新搭建一套 4 个库各 64 个表的分库分表，将作业 2 中的数据迁移到新分库。

5.（选做）列举常见的分布式事务，简单分析其使用场景和优缺点。

6.（必做）基于 hmily TCC 或 ShardingSphere 的 Atomikos XA 实现一个简单的分布式事务应用 demo（二选一），提交到 Github。
  - Answer: [task 6 solution](https://github.com/ajdfajdfl2003/JAVA-003/tree/main/Week_08/task06)

7.（选做）基于 ShardingSphere narayana XA 实现一个简单的分布式事务 demo。

8.（选做）基于 seata 框架实现 TCC 或 AT 模式的分布式事务 demo。

9.（选做☆）设计实现一个简单的 XA 分布式事务框架 demo，只需要能管理和调用 2 个 MySQL 的本地事务即可，不需要考虑全局事务的持久化和恢复、高可用等。

10.（选做☆）设计实现一个 TCC 分布式事务框架的简单 Demo，需要实现事务管理器，不需要实现全局事务的持久化和恢复、高可用等。

11.（选做☆）设计实现一个 AT 分布式事务框架的简单 Demo，仅需要支持根据主键 id 进行的单个删改操作的 SQL 或插入操作的事务。

---

以上作业，要求 2 道必做题目先提交到自己的 GitHub 上面，再提交到下方表单中：

- https://jinshuju.net/f/POWS9D

作业提交截止时间 6 月 27 日（周日）23:59 前。

作业参考示例地址，由秦老师和助教共建，每周同步更新： https://github.com/JavaCourse00/JavaCourseCodes

GitHub 使用教程： https://u.geekbang.org/lesson/51?article=294701

学号查询方式：PC 端登录 time.geekbang.org, 点击右上角头像进入我的教室，左侧头像下方 G 开头的为学号
