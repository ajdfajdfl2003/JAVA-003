# Task02
> 2.（选做）使用压测工具（wrk 或 sb），演练 gateway-server-0.0.1-SNAPSHOT.jar 示例。

## 壓測對象
```bash
java -jar \
-XX:+PrintGCDetails -Xmx512m -Xms512m -Xloggc:gateway-server.gc.log \
gateway-server-0.0.1-SNAPSHOT.jar
```

## 壓測
```bash
# 呼叫 api 網址為：http://localhost:8088/api/hello
# wrk 使用參考：https://github.com/wg/wrk#command-line-options
wrk -t 4 -d 10s http://localhost:8088/api/hello
Running 10s test @ http://localhost:8088/api/hello
  4 threads and 10 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     0.96ms    6.44ms 120.96ms   98.87%
    Req/Sec     5.93k     1.63k    8.05k    82.07%
  233698 requests in 10.01s, 27.90MB read
Requests/sec:  23352.66
Transfer/sec:      2.79MB 
```
