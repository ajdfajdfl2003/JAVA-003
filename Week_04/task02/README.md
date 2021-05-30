# Task02
> 2.（必做）思考有多少种方式，在 main 函数启动一个新线程，运行一个方法，拿到这个方法的返回值后，退出主线程? 写出你的方法，越多越好，提交到 GitHub。 
> 
> 一个简单的代码参考:  https://github.com/kimmking/JavaCourseCodes/tree/main/03concurrency/0301/src/main/java/java0/conc0303/Homework03.java

## Main Thread
- https://github.com/ajdfajdfl2003/JAVA-003/blob/main/Week_04/task02/src/main/java/idv/angus/concurrency/thread/MainThread.java

## Non-Daemon + Thread Sleep 方法
- https://github.com/ajdfajdfl2003/JAVA-003/blob/main/Week_04/task02/src/main/java/idv/angus/concurrency/thread/NonDaemonThreadSleep.java

## Thread Join 方法
- https://github.com/ajdfajdfl2003/JAVA-003/blob/main/Week_04/task02/src/main/java/idv/angus/concurrency/thread/ThreadJoin.java

## Wait and Notify + Thread Sleep 方法
- https://github.com/ajdfajdfl2003/JAVA-003/blob/main/Week_04/task02/src/main/java/idv/angus/concurrency/object/WaitAndNotifyThreadSleep.java

## ExecutorService
- https://github.com/ajdfajdfl2003/JAVA-003/blob/main/Week_04/task02/src/main/java/idv/angus/concurrency/threadpool/FibonacciExecutorService.java

## FutureTask without Thread Pool
- https://github.com/ajdfajdfl2003/JAVA-003/blob/main/Week_04/task02/src/main/java/idv/angus/concurrency/future/FibonacciFutureTaskWithoutPool.java

## FutureTask with Thread Pool
- https://github.com/ajdfajdfl2003/JAVA-003/blob/main/Week_04/task02/src/main/java/idv/angus/concurrency/future/FibonacciFutureTaskWithPool.java

## CompletableFuture
For more information:
- https://popcornylu.gitbooks.io/java_multithread/content/async/cfuture.html
- https://openhome.cc/Gossip/CodeData/JDK8/CompleteableFuture.html 

### Composable: SupplyAsync then AcceptAsync
- https://github.com/ajdfajdfl2003/JAVA-003/blob/main/Week_04/task02/src/main/java/idv/angus/concurrency/future/completefuture/SupplyAsyncAcceptAsync.java

### Listenable: SupplyAsync when Complete
- https://github.com/ajdfajdfl2003/JAVA-003/blob/main/Week_04/task02/src/main/java/idv/angus/concurrency/future/completefuture/SupplyAsyncListenable.java

### CountDownLatch
- https://github.com/ajdfajdfl2003/JAVA-003/blob/main/Week_04/task02/src/main/java/idv/angus/concurrency/tools/FibonacciCountDownLatch.java
