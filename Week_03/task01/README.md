# Task01
>（必做）整合你上次作业的 httpclient/okhttp。


## 作業過程中遇到什麼問題&Fix
### MyHttpInboundInitializer
在 MyHttpInboundInitializer 類中，沒有加上 `HttpServerCodec` 與 `HttpObjectAggregator`
會拋出 `java.lang.ClassCastException: io.netty.buffer.PooledUnsafeDirectByteBuf cannot be cast to io.netty.handler.codec.http.FullHttpRequest`
回頭看老師的代碼才發現少了這兩個

更多參考: 
- https://blog.csdn.net/m0_45406092/article/details/104895032
- https://kkewwei.github.io/elasticsearch_learning/2018/04/16/Netty-Http%E9%80%9A%E4%BF%A1%E8%A7%A3%E7%A0%81%E6%BA%90%E7%A0%81%E9%98%85%E8%AF%BB/