# Task02
> 2.**（必做）按自己设计的表结构，插入 100 万订单模拟数据，测试不同方式的插入效率**

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
