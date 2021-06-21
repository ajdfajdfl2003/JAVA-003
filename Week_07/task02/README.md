# Task02
> 2.**（必做）按自己设计的表结构，插入 100 万订单模拟数据，测试不同方式的插入效率**

## 結論
**InsertWithThreadAndPoolSize.class**

- 開 Thread Pool 大小為 20，且 Connection Pool 大小與 Thread Pool 一樣
- 有加上群組老師跟同學們建議的 `rewriteBatchedStatements` 參數 

最後執行總時間為： `9127 mills`

最後有參考：
- https://developer.aliyun.com/article/621133
- https://www.jianshu.com/p/04d3d235cb9f
- https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-connp-props-performance-extensions.html

## 單一個 Thread
### 一次就新增 100 萬筆才一次 Commit
```
00:57:57.437 [main] INFO  idv.angus.task02.InsertAllAsOneTime
    - Start 1_000_000 as one time
01:03:01.160 [main] INFO  idv.angus.task02.InsertAllAsOneTime
    - Time Elapsed for 1_000_000 as one time: 303889mills
```

### 跑 100 萬筆，每一筆資料就一次 Commit
```
00:35:38.925 [main] INFO  idv.angus.task02.InsertAllOncePerCommit - 
    Start 1_000_000, one record one commit
沒有跑結束我就先停掉了....，跑了將近二十分鐘...
```

### 每筆資料都進到 Batch，跑 100 萬筆才做 Batch 更新然後 Commit
```
01:18:38.052 [main] INFO  idv.angus.task02.InsertAllBatch
    - Start 1_000_000 record, do insert as batch. After 1_000_000 record, do commit
01:21:35.953 [main] INFO  idv.angus.task02.InsertAllBatch
    - Time Elapsed for 1_000_000 record, do insert as batch. 
      After 1_000_000 record, do commit: 177904mills
```

加了群組內建議的 rewriteBatchedStatements 參數，整整快了 156028 mills

```
19:43:58.969 [main] INFO  idv.angus.task02.InsertAllBatch
    - Start 1_000_000 record, do insert as batch. After 1_000_000 record, do commit
19:44:20.848 [main] INFO  idv.angus.task02.InsertAllBatch
    - Time Elapsed for 1_000_000 record, do insert as batch. After 1_000_000 record, do commit: 21876mills
```

### 每 1000 筆資料就做 Batch，跑完 100 萬筆後 Commit
```
01:29:51.681 [main] INFO  idv.angus.task02.InsertBatchPer1000
    - Start 1_000_000 record, Batch per 1000 record
01:32:47.486 [main] INFO  idv.angus.task02.InsertBatchPer1000
    - Time Elapsed for 1_000_000 record, Batch per 1000 record: 175805mills
```

加了群組內建議的 rewriteBatchedStatements 參數，整整快了 158056 mills

```
19:50:54.496 [main] INFO  idv.angus.task02.InsertBatchPer1000
    - Start 1_000_000 record, batch per 1000 record.
19:51:12.248 [main] INFO  idv.angus.task02.InsertBatchPer1000
    - Time Elapsed for 1_000_000 record, batch per 1000 record: 17749mills
```

## Thread Pool
### 每 200000 筆資料一個 Thread，然後每個 Thread 各自 Commit，跑完 100 萬筆資料
```
02:21:51.848 [main] INFO  idv.angus.task02.InsertWithThreadAs200000Batch
    - Start 1_000_000 record. 200_000 record per Thread
02:22:55.425 [main] INFO  idv.angus.task02.InsertWithThreadAs200000Batch
    - Time Elapsed for 1_000_000 record. 200_000 record per Thread: 63573mills
```

加了群組內建議的 rewriteBatchedStatements 參數，整整快了 51382 mills

```
19:59:03.885 [main] INFO  idv.angus.task02.InsertWithThreadAs200000Batch
    - Start 1_000_000 record. 200_000 record per Thread
19:59:16.082 [main] INFO  idv.angus.task02.InsertWithThreadAs200000Batch
    - Time Elapsed for 1_000_000 record. 200_000 record per Thread: 12191mills
```

### 用 Connection Pool 和 Thread 去跑 100 萬筆資料
```
02:44:08.103 [main] INFO  idv.angus.task02.InsertWithThreadAndPoolSize
    - Start 1_000_000 record. 100000 record per Thread
02:45:02.105 [main] INFO  idv.angus.task02.InsertWithThreadAndPoolSize
    - Time Elapsed for 1_000_000 record. 100000 record per Thread: 53998mills
```

加了群組內建議的 rewriteBatchedStatements 參數，整整快了 42289 mills 

```
20:01:50.961 [main] INFO  idv.angus.task02.InsertWithThreadAndPoolSize
    - Start 1_000_000 record. 100000 record per Thread
20:02:02.673 [main] INFO  idv.angus.task02.InsertWithThreadAndPoolSize
    - Time Elapsed for 1_000_000 record. 100000 record per Thread: 11709mills
```

---

```
02:36:50.934 [main] INFO  idv.angus.task02.InsertWithThreadAndPoolSize
- Start 1_000_000 record. 50000 record per Thread with 20 PoolSize
  02:37:42.044 [main] INFO  idv.angus.task02.InsertWithThreadAndPoolSize
- Time Elapsed for 1_000_000 record. 50000 record per Thread with 20 PoolSize: 51106mills
```

加了群組內建議的 rewriteBatchedStatements 參數，整整快了 41979 mills 

```
20:05:36.962 [main] INFO  idv.angus.task02.InsertWithThreadAndPoolSize
    - Start 1_000_000 record. 50000 record per Thread
20:05:46.092 [main] INFO  idv.angus.task02.InsertWithThreadAndPoolSize
    - Time Elapsed for 1_000_000 record. 50000 record per Thread: 9127mills
```
