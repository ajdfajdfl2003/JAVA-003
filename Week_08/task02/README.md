# Task09
> 2.（必做）设计对前面的订单表数据进行水平分库分表，拆分 2 个库，每个库 16 张表。并在新结构在演示常见的增删改查操作。代码、sql 和配置文件，上传到 Github。

## 坑...
- Alpha 跟 Beta 的設定方式不一樣...
  
- 實測結果是：keyGenerateStrategy，對應的欄位要 **小寫** ！！！！！！！！！！
  - 也就是要 `shardingAlgorithms` 要使用的 `algorithm-expression` 欄位要特別注意啊啊啊啊啊啊啊 ！！！！！
  
  - 有待追原始碼 ....

老師使用的是 Alpha 的版本，因此本次作業先用 Alpha 來實作...

## 啟動方式

```bash
sh shardingsphere-proxy/bin/start.sh
```

關閉 Process
```bash
ps aux | grep shardingsphere | grep -v grep | grep -v IntelliJ
```

## 連到 proxy

日誌會在 `shardingsphere-proxy/logs` 底下

用 mysql cli 連進去 proxy 的時候，在 alpha 版本記得加上 `-A`:
- https://dev.mysql.com/doc/refman/8.0/en/mysql-command-options.html#option_mysql_no-auto-rehash

```bash
mysql -h127.0.0.1 -uroot -proot -P3307 -A
```

```sql
## 觀察資料表結構
show create table `order`;
```

## Return Auto Increment ID After Insert

- https://stackoverflow.com/a/1915197/6578107

## 自己耍蠢
- [寫了一樣的蠢 code](https://zh.codeprj.com/blog/ba51cc1.html)
