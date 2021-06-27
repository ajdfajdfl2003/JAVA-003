# Task09
> 2.（必做）设计对前面的订单表数据进行水平分库分表，拆分 2 个库，每个库 16 张表。并在新结构在演示常见的增删改查操作。代码、sql 和配置文件，上传到 Github。

## 坑...

- Alpha 跟 Beta 的設定方式不一樣...

## 啟動方式

```bash
shardingsphere-proxy/bin/start.sh
```

## 連到 proxy

日誌會在 `shardingsphere-proxy/logs` 底下

用 mysql cli 連進去 proxy 的時候，在 alpha 版本記得加上 `-A`:
- https://dev.mysql.com/doc/refman/8.0/en/mysql-command-options.html#option_mysql_no-auto-rehash

```bash
mysql -h127.0.0.1 -uroot -proot -P3307 -A
```
