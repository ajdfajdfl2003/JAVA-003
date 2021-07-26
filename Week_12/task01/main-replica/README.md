# 主從複製

啟動的話，就是：
```bash
# 兩台的話就分別起兩個
redis-server redis6379.conf
redis-server redis6380.conf
````

用 redis-cli 連接
```bash
redis-cli -p 6379
redis-cli -p 6380
```

看 redis 的一些設定
```bash
info
```

把 6380 變成 6379 的 slave
```bash
redis6380> slaveof 127.0.0.1 6379
```

這個步驟也能用利用前面提到的設定檔，讓啟動的時候就指定當誰的複本
例如：我(6380) 想當 6379 的複本，那我們就在 6380 的設定檔中加上
```config
replicaof 127.0.0.1 6380
replicaof ::1 6380
```

好，以上就是怎麼啟動
至於啟動後，我們在 replica 上就不能 set 任何東西了
若要 set 東西要在 main 上面做設定

```bash
redis6380> set javacourse 03
(error) READONLY You can't write against a read only replica.

redis6379> set javacourse 03
OK
```

那我們取東西一下
```bash
redis6380> get javacourse
"03"

redis6379> get javacourse
"03"
```
