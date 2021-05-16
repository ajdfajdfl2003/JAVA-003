# Task01

> 1.（选做）使用 GCLogAnalysis.java 自己演练一遍串行 / 并行 /CMS/G1 的案例。>

```bash
cd Week_02/task01
# compile
javac -g GCLogAnalysis.java
# Java Version
java -version
java version "1.8.0_281"
Java(TM) SE Runtime Environment (build 1.8.0_281-b09)
Java HotSpot(TM) 64-Bit Server VM (build 25.281-b09, mixed mode)
```

## 串行 GC

### Xmx1g, Xms1g

```bash
# run app with SerialGC(串行）
java -XX:+PrintGCDetails -Xmx1g -Xms1g -Xloggc:serial.gc.xms1g.xmx1g.log -XX:+UseSerialGC GCLogAnalysis
```

觀察 `serial.gc.xms1g.xmx1g.log` 的日誌

- 在 Old 區 GC(Tenured) 之前，共做了九次 Young 區的 GC(DefNew)
    - 在應用程式啟動後的第 219 毫秒，做了第一次 Miner GC：
        - ```log
          0.218: [GC (Allocation Failure) 0.218: [DefNew: 279616K->34944K(314560K), 0.0502821 secs] 279616K->88009K(1013632K), 0.0503818 secs] [Times: user=0.03 sys=0.02, real=0.05 secs]
          ```
        - 耗費 50 毫秒
        - Young 區從 273MB 壓縮到 34MB
        - 而整個 Heap 容量從 273MB 壓縮到了 85MB
        - 回想一下上週的作業，因為我們 Xmx and Xms 設定一樣的大小，因此我們可以說 Heap = Young + Old
        - 所以在這個階段我們從 Young 區晉升到 Old 區的容量有 51 MB
    - 中間先忽略...
    - 啟動後的第 866 毫秒，做了第九次的 Miner GC:
        - ```log
          0.866: [GC (Allocation Failure) 0.866: [DefNew: 314560K->34943K(314560K), 0.0591935 secs] 911341K->710816K(1013632K), 0.0593143 secs] [Times: user=0.03 sys=0.03, real=0.06 secs]
          ```
        - 耗費 59 毫秒
        - Young 區 307MB 壓縮到 34MB
        - 整了 Heap 容量從 889MB 壓縮到 694MB
        - 此時 Old 區已經來到了 660MB，此時我們的 Old 區已經快滿了
        - Old 區有 699072K 相當於 682MB
    - 一直到啟動後第 954 毫秒，發現 Old 區已經快滿了，做了一次 Old 區的 GC
        - ```
          0.954: [GC (Allocation Failure) 0.954: [DefNew: 314559K->314559K(314560K), 0.0000140 secs]0.954: [Tenured: 675872K->388126K(699072K), 0.0492943 secs] 990432K->388126K(1013632K), [Metaspace: 2580K->2580K(1056768K)], 0.0494255 secs] [Times: user=0.05 sys=0.00, real=0.05 secs]
          ```
        - 耗費 49 毫秒做 Old 區 GC
        - Old 區從 660MB 壓縮到 379 MB
        - 此時整個 Heap 容量從 967MB 壓縮到了 379 MB
