#### tdd_rpg_dialog_color_text

它是一个简单项目，用以展示使用tdd（test driven development）方式完成一个需求的过程。

##### 工具

- gradle
- junit4
- jacoco 

##### 缘由

为什么会有本项目？因为我之前用java写过一个rpg，见xxx。里面就有显示彩色对话文本的功能，这个功能比较独立，这几天又看到了tdd，又想使用一下gradle，所以该项目就出现了。

#### 目标

对代码编程者：通过使用tdd，来快乐地编程。
对代码阅读者：不借助最终的源码库，一步步手工编写或复制来完成本项目。

##### 准备

雨血是我非常喜欢的一部武侠rpg。下面我将会以它其中的两个对话截图来完成我们的项目。

![](./image/rain_blood/d1.jpg)

![](./image/rain_blood/d2.jpg)

PS：对话内容是：

    一切，都将在埋葬之地重生。

    有些人将在埋葬之地重生，而另外的一些人，将在埋葬之地被埋葬！

从截图上可以看到，一行显示18个汉字。

下面我们就开始一步一步地把对话文本分割成一行行彩色对话。

##### 第一步：创建测试骨架

我们先定义一个接口`DialogTextFormatter`。它有一个format方法，参数是一个字符串。
本项目中使用的类的包名都是billy.rpg.common.formatter，如下不再重复说明。

```java
package billy.rpg.common.formatter;

import java.util.List;

public interface DialogTextFormatter {
    List<String> format(String text);
}
```

接着，我们就可以编写测试类了。从面向对象角度来说DialogTextFormatter应该有个默认的实现类，名叫`DefaultDialogTextFormatter`。

```java
package billy.rpg.common.formatter;

import java.util.List;

public class DefaultDialogTextFormatter implements DialogTextFormatter {

    @Override
    public List<String> format(String text) {
        return null;
    }

}
```

那我们的测试类的名称就是`DefaultDialogTextFormatterTest`。我们使用的测试骨架如下（以后都是往该测试类里添加方法）：

```java
package billy.rpg.common.formatter;

import org.junit.Before;
import org.junit.Test;

public class DefaultDialogTextFormatterTest {

    private DialogTextFormatter dialogTextFormatter;

    @Before
    public void before() {
        dialogTextFormatter = new DefaultDialogTextFormatter();
    }

}
```

##### 第二步：显示简单对话文本

我们要显示一段简单的文本，效果图如下：

![](./image/singleLine.png)

如下我们将在DefaultDialogTextFormatterTest类中添加第一个测试方法。

```java
    @Test
    public void testFormatWithSingleLine() {
        String text = "一切，都将在埋葬之地重生。";
        List<String> resultList = dialogTextFormatter.format(text);
        assertNotNull(resultList);
        assertThat(resultList.size(), is(1));
        assertThat(resultList.get(0), is(text));
    }
```

我们传入一个简单的文本，期望dialogTextFormatter返回一个List<String>类型的resultList，期望它的大小是1，期望它的第一个值是传入的参数。
那么，执行该测试方法！

不出意外，断言没能通过，出现了红条。当然了，因为我们还没有编写实现方法，如下

```java
    @Override
    public List<String> format(String text) {
        List<String> result = new ArrayList<>();
        result.add(text);
        return result;
    }
```

可以看到实现方法非常简单，这也是tdd的核心，每次实现都以最小的代码来让测试通过。

运行测试类，或使用命令行`gradle clean test`，然后去build/reports/tests/test下打开index.html，看下测试成功率。
目前我们只有一个方法，它执行成功了，所以成功率就是100%。
我们还需要看测试覆盖率，执行`gradle clean test jacocoTestReport`然后去build/reports/jacoco/test/html下访问index.html，可以看到每个方法的覆盖率情况。
以后我们每编写一点功能就得不断地执行测试类来确保新功能没有影响到旧功能，或修改旧测试方法以适应新功能。


