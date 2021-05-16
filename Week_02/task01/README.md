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
### Xmx4g, Xms4g
```bash
java -XX:+PrintGCDetails \
-Xmx4g -Xms4g \
-Xloggc:serial.gc.xms4g.xmx4g.log \
-XX:+UseSerialGC \
GCLogAnalysis
```

- 只做了兩次的 Young 區 GC，但相對的暫停時間增加到了 0.1 秒多

### Xmx1g, Xms1g
```bash
# run app with SerialGC(串行）
java -XX:+PrintGCDetails \
-Xmx1g -Xms1g \
-Xloggc:serial.gc.xms1g.xmx1g.log \
-XX:+UseSerialGC \
GCLogAnalysis
```

觀察 `serial.gc.xms1g.xmx1g.log` 的日誌
- 在 Old 區 GC(Tenured) 之前，共做了九次 Young 區的 GC(DefNew)
    - 在應用程式啟動後的第 219 毫秒，做了第一次 Miner GC：
        - ```log
          0.218: [GC (Allocation Failure) 0.218: [DefNew: 279616K->34944K(314560K), 0.0502821 secs] 279616K->88009K(1013632K), 0.0503818 secs] [Times: user=0.03 sys=0.02, real=0.05 secs]
          ```
        - 耗費 50 毫秒
        - user + sys = real，因為串行 GC 是單線程的
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

## 并行 GC
### Xmx4g, Xms4g
```bash
java -XX:+PrintGCDetails \
-Xmx4g -Xms4g \
-Xloggc:parallel.gc.xms4g.xmx4g.log \
GCLogAnalysis
```

- 有三次的 Young 區 GC，這個階段的暫停時間大約 0.04 ~ 0.07 秒的時間
    - 相較於在調大 Xmx, Xms 的情況下較快速

### Xmx1g, Xms1g
```bash
# run app with Parallel(並行）
# 因為 Java 8 預設是使用 ParallelGC，所以這裡不特別指名使用
java -XX:+PrintGCDetails \
-Xmx1g -Xms1g \
-Xloggc:parallel.gc.xms1g.xmx1g.log \
GCLogAnalysis
```

觀察 `parallel.gc.xms1g.xmx1g.log` 的日誌
- CommandLine flags: -XX:InitialHeapSize=1073741824 -XX:MaxHeapSize=1073741824 -XX:+PrintGC -XX:+PrintGCDetails -XX:
  +PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseParallelGC
    - -XX:+UseParallelGC，證實 Java 8 預設使用
- 在 Old 區 GC 之前，共做了十三次 Young 區的 GC
    - ParallelGC 與 SerialGC 類似，所以不特別做說明
    - 但有觀察到 user + sys 已經不等於 real 了
    - 原因是 ParallelGC 是使用多線程來做 GC，預設為系統核心數
- 參考：[GC LOGGING – USER, SYS, REAL – WHICH TIME TO USE?](https://blog.gceasy.io/2016/04/06/gc-logging-user-sys-real-which-time-to-use/)

## CMS GC
### Xmx4g, Xms4g
```bash
java -XX:+PrintGCDetails \
-Xmx4g -Xms4g \
-Xloggc:cms.gc.xms4g.xmx4g.log \
-XX:+UseConcMarkSweepGC \
GCLogAnalysis
```

- 歷經七次的 Young GC，大約暫停了 0.04 ~ 0.08 秒之間

### Xmx1g, Xms1g
```bash
# run app with CMS GC
java -XX:+PrintGCDetails \
-Xmx1g -Xms1g \
-Xloggc:cms.gc.xms1g.xmx1g.log \
-XX:+UseConcMarkSweepGC \
GCLogAnalysis
```

觀察 `cms.gc.xms1g.xmx1g.log` 的日誌
- 從啟動後到第 496 毫秒，總共做了五次 Young 區 GC
    - ```log
      0.224: [GC (Allocation Failure) 0.224: [ParNew: 279616K->34943K(314560K), 0.0309837 secs] 279616K->93401K(1013632K), 0.0310931 secs] [Times: user=0.07 sys=0.12, real=0.03 secs] 
      0.293: [GC (Allocation Failure) 0.293: [ParNew: 314559K->34943K(314560K), 0.0282108 secs] 373017K->171563K(1013632K), 0.0283563 secs] [Times: user=0.06 sys=0.10, real=0.03 secs]
      0.353: [GC (Allocation Failure) 0.353: [ParNew: 314559K->34944K(314560K), 0.0423477 secs] 451179K->243572K(1013632K), 0.0424395 secs] [Times: user=0.27 sys=0.03, real=0.05 secs]
      0.425: [GC (Allocation Failure) 0.425: [ParNew: 314560K->34944K(314560K), 0.0453744 secs] 523188K->319574K(1013632K), 0.0454712 secs] [Times: user=0.26 sys=0.03, real=0.04 secs]
      0.496: [GC (Allocation Failure) 0.496: [ParNew: 314560K->34944K(314560K), 0.0484191 secs] 599190K->401462K(1013632K), 0.0485666 secs] [Times: user=0.33 sys=0.02, real=0.05 secs]
      ```
    - 這五次的 GC 都壓縮到了 34MB
- 到的第 545 毫秒，開始進入到了 CMS 初始化的階段
    - ```log
      0.545: [GC (CMS Initial Mark) [1 CMS-initial-mark: 366518K(699072K)] 407641K(1013632K), 0.0003757 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
      ```
    - Old 區目前是 357 MB，而整個 Heap 容量是 398 MB
    - 會把業務邏輯的線程暫停
- 而在之後，就開始做 併發 Old 區的 GC
    - ```log
      0.546: [CMS-concurrent-mark-start]
      0.548: [CMS-concurrent-mark: 0.003/0.003 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
      0.549: [CMS-concurrent-preclean-start]
      0.550: [CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
      0.550: [CMS-concurrent-abortable-preclean-start]
      0.572: [GC (Allocation Failure) 0.572: [ParNew: 314560K->34942K(314560K), 0.0452038 secs] 681078K->476206K(1013632K), 0.0453316 secs] [Times: user=0.32 sys=0.02, real=0.05 secs]
      0.644: [GC (Allocation Failure) 0.644: [ParNew: 314558K->34943K(314560K), 0.0445881 secs] 755822K->554030K(1013632K), 0.0446839 secs] [Times: user=0.32 sys=0.03, real=0.05 secs]
      0.716: [GC (Allocation Failure) 0.716: [ParNew: 314559K->34942K(314560K), 0.0418943 secs] 833646K->627927K(1013632K), 0.0419896 secs] [Times: user=0.30 sys=0.03, real=0.04 secs]
      0.786: [GC (Allocation Failure) 0.786: [ParNew: 314558K->34942K(314560K), 0.0426601 secs] 907543K->704973K(1013632K), 0.0428310 secs] [Times: user=0.30 sys=0.02, real=0.04 secs]
      0.829: [CMS-concurrent-abortable-preclean: 0.009/0.279 secs] [Times: user=1.35 sys=0.10, real=0.28 secs]
      ```
    - 而在併發的過程中，還有發生四次的 Young GC
- 在啟動後的第 922 毫秒，CMS 來到了最終標記階段
    - ```log
      0.829: [GC (CMS Final Remark) [YG occupancy: 40753 K (314560 K)]0.829: [Rescan (parallel) , 0.0003699 secs]0.829: [weak refs processing, 0.0000396 secs]0.829: [class unloading, 0.0001741 secs]0.829: [scrub symbol table, 0.0002264 secs]0.830: [scrub string table, 0.0000806 secs][1 CMS-remark: 670031K(699072K)] 710784K(1013632K), 0.0010007 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
      ```
    - 會把業務邏輯的線程暫停

## G1GC
### Xmx1g, Xms1g
```bash
# run app with G1GC
java -XX:+PrintGC \
-Xmx1g -Xms1g \
-Xloggc:g1.gc.xms1g.xmx1g.log \
-XX:+UseG1GC \
GCLogAnalysis
```

觀察 `g1.gc.xms1g.xmx1g.log` 的日誌
