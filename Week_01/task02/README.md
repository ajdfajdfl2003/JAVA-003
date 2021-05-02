### Task 02
> 自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。文件群里提供。

#### 思路

在課堂上，老師演示的範例是透過 base64 的方式把內容反轉回來，這個題目意思差不多。
大概的步驟會是：
- 讀檔
  - 這個步驟我卡的比較久，問題在`找不到或無法載入主要類別`
  - 在我[忘記這件事情](https://docs.oracle.com/javase/7/docs/technotes/tools/windows/javac.html)
    > You should arrange source files in a directory tree that reflects their package tree. For example, if you keep all your source files in C:\workspace, the source code for com.mysoft.mypack.MyClass should be in C:\workspace\com\mysoft\mypack\MyClass.java.
  - 後來切到正確路徑下，使用 javac 與 java 指令就 work 了！
    ```bash
    $ javac -g Week_01/task02/src/HelloClassLoader.java
    $ java Week_01.task02.src.HelloClassLoader
    ```
- 處理內容，也就是 x = 255 - x 的這件事情
- 執行方法
