# Task02
> 2.（必做）写代码实现 Spring Bean 的装配，方式越多越好（XML、Annotation 都可以）, 提交到 GitHub。 

## XML 方式

首先，先把自己的場景先想好，我有一個戰場是兩個人在使用鍵盤（很無聊我知道，我隨便想的），

然後這兩個人身上會有一些不一樣的屬性，但共通點都是會使用鍵盤，然而每個人身上的鍵盤會裝配不一樣的鍵帽。

先把 Class 跟 Interface 創建好，我們會有一個鍵盤，裡面會裝著不同的鍵帽，

再來，我們有兩個 User，分別是 Damon 和 Katherine：
- Damon 身上會有
  - 一個鍵盤擁有兩個鍵帽：A、Enter
  - 他自己的姓名 

- Katherine 身上會有
  - 一個鍵盤擁有兩個鍵帽：B、Enter
  - 他自己的姓名
  - 他的穿著
  
物件跟介面都好了之後，開始設定 xml 吧！

先幫 Damon 這個人拼裝一下
```xml
<!-- 
    幫這個 bean 設定一個 id，待會執行的地方會需要拿到，
    並且這個 bean 對應的 Class 是 idv.angus.task02.xml.Damon
-->
<bean id="damon" class="idv.angus.task02.xml.Damon">
    <!-- 從 xml 設定自身的屬性 -->
    <property name="name" value="Damon"/>
    <property name="keyboard">
        <!-- 幫這個屬性設定 Keyboard 的實體內容 -->
        <bean class="idv.angus.task02.xml.Keyboard">
            <property name="keyCaps">
                <!-- 幫這個 Keyboard 裝配兩個鍵帽 -->
                <list>
                    <bean class="idv.angus.task02.xml.keycaps.A"/>
                    <bean class="idv.angus.task02.xml.keycaps.Enter"/>
                </list>
            </property>
        </bean>
    </property>
</bean>
```

再幫 Katherine 這個人拼裝一下
```xml
<!-- 
    幫這個 bean 設定一個 id，下面設定 Katherine 的時候會用到，
    並且這個 bean 對應的 Class 是 idv.angus.task02.xml.Keyboard
-->
<bean id="katherineKeyboard" class="idv.angus.task02.xml.Keyboard">
    <property name="keyCaps">
        <!-- 幫這個 Keyboard 裝配兩個鍵帽 -->
        <list>
            <bean class="idv.angus.task02.xml.keycaps.B"/>
            <bean class="idv.angus.task02.xml.keycaps.Enter"/>
        </list>
    </property>
</bean>
```
```xml
<!-- 
    幫這個 bean 設定一個 id，待會執行的地方會需要拿到，
    並且這個 bean 對應的 Class 是 idv.angus.task02.xml.Katherine
-->
<bean id="katherine" class="idv.angus.task02.xml.Katherine">
    <!-- 從 xml 設定自身的屬性 -->
    <property name="outfit" value="Street Style"/>
    <property name="name" value="Katherine"/>
    <!-- 這個人的 keyboard 使用上面有定義的 bean: katherineKeyboard -->
    <property name="keyboard" ref="katherineKeyboard"/>
</bean>
```
使用方其實就是

```java
// 告訴 Spring 的 Context 你的設定檔在哪
ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
```

```java
// 從 context 拿出設定的 bean id
(User) context.getBean("damon");
// 操作一下這個 class 的方法即可
damon.useKeyBoard();
```