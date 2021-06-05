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
          <bean class="idv.angus.task02.components.keycaps.A"/>
          <bean class="idv.angus.task02.components.keycaps.Enter"/>
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
      <bean class="idv.angus.task02.components.keycaps.B"/>
      <bean class="idv.angus.task02.components.keycaps.Enter"/>
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

## Annotation 方式

使用 AnnotationConfigApplicationContext 來當作我們的設定檔
```java
ApplicationContext ctx = new AnnotationConfigApplicationContext(AnnotationConfig.class);
```

先來講一下 Joe 這個 Class
```java
@Log4j2
public class Joe implements User {
    @Autowired
    private StrangeKeyboard keyboard;

    @Resource(name = "myBackpack")
    private Backpack backpack;
```

這邊我們告訴 Spring 說我們有兩個成員變數需要注入：

- 麻煩你幫我找找看有沒有 StrangeKeyboard 這個 class 的 Bean 可以來注入
- 麻煩你幫我找一個 name 是 myBackpack 的 bean 拿來注入

關於這段的 config 就會是

```java
@Configuration
public class AnnotationConfig {
    @Bean
    public StrangeKeyboard strangeKeyboard() {
        return new StrangeKeyboard();
    }

    @Bean(name = "myBackpack")
    public Backpack backpack() {
        return new Backpack();
    }
}
```

講完 Joe 那就來講一下 StrangeKeyboard 這個類別
```java
public class StrangeKeyboard {
    @Autowired
    private List<KeyCap> lefKeyCaps;
    @Resource(name = "rightKeyCap")
    private List<KeyCap> rightKeyCaps;
}
```

先擺上設定檔案

```java
@Bean
public List<KeyCap> lefKeyCaps() {
    return Arrays.asList(new A(), new Enter());
}

@Bean
public List<KeyCap> rightKeyCap() {
    return Arrays.asList(new B(), new Enter(), new A());
}
```

這邊有一個有趣的發現

因為我們需要有兩個不一樣的 List<KeyCap> 的 bean，進而發現，

在設定兩種相同類型的 bean 的情況下，他沒辦法從類型去判定的時候，就會去 match 創 bean 的方法名稱

- 像上述的例子，我需要 Autowired 兩個 List<KeyCap>，那主程式變數名字 `lefKeyCaps` 與設定檔內 `lefKeyCaps()` 一樣才會成功注入。
- Resource 指定要找 `rightKeyCap`，設定檔案並沒有設定 bean name 是 `rightKeyCap`，那方法名是：`rightKeyCap()`，就會 match 到了。  
