# Task04
> 4.（必做）根据上述自己对于 1 和 2 的演示，写一段对于不同 GC 和堆内存的总结，提交到 GitHub。

## GC 暫停時間
就串行 GC 來看，把整個 Heap 容量從 1g 調整到 4g
- 整個 GC 暫停時間拉長了兩倍，從 47.3 ms 到 135 ms
- 單線程去做 GC 因此當要處理的 GC 量變大，速度就會慢

並行 GC的部分則是
- 雖然 GC 暫停時間也大約成長了兩倍，但大約都是在 100 毫秒以內
    - 24.2 ms -> 60.0 ms
    
CMS GC
- 24.4 ms -> 65.7 ms

G1GC
- 5.37 ms -> 33.7 ms
- 值得一提的是從目前數據來看，還沒有超過預設的 200 milliSecs
  - 若需要調整可以透過 `-XX:MaxGCPauseMilllis=[number]`
  - G1GC 會在多次 GC 後，調整策略頻率以及處理垃圾的數量，慢慢地控制在 200 ms 內

就速度來看，大概會是
- Serial GC > Parallel GC ~= CMS GC > G1GC
- 雖然 Parallel GC 跟 CMS GC 好像平分秋色
  - 但 Parallel GC 是使用所有 CPU Thread 來做，意思就是他會把業務邏輯暫停
  - 而 CMS 會撥出 1/4 的 CPU 來處理 GC
