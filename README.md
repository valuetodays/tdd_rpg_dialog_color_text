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

我们的目标是展示出如下对话：

![](./image/goal.png)

它的特点是对话文本会换行，里面会有些文本是彩色。
好了，下面一起开始吧。

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

##### 第三步：显示简单长对话文本

上步我们展示了一个简单的文本，这步我们要往目标迈近一步——展示换行的文本。

目标效果图如下：

![](./image/multiLine.png)

想要展示换行文本，我们需要定一个变量，每行显示文字数量，即wordsNumPerLine，它是DefaultDialogTextFormatter的一个属性，
该值应该由外部传入，所以为DefaultDialogTextFormatter添加构造方法并传入此值。

（为什么不在DefaultDialogTextFormatter内部直接定义一个静态常量呢，因为只要定义了常量，类与数值就绑定了，我们要做的是解耦，所以要传入）

我们在DefaultDialogTextFormatterTest内部定义一个静态常量WORDS_NUM_PER_LINE，用以设置每行显示文字数量，上面说过了，它的值是18。

```java
    private final static int WORDS_NUM_PER_LINE = 18;
```

然后我们的测试方法应该如下：

```java
    @Test
    public void testFormatWithMultiLine() {
        String text = "有些人将在埋葬之地重生，而另外的一些人，将在埋葬之地被埋葬！";
        List<String> resultList = dialogTextFormatter.format(text);
        assertNotNull(resultList);
        assertThat(resultList.size(), is(2));
        assertThat(resultList.get(0), is(text.substring(0, WORDS_NUM_PER_LINE)));
        assertThat(resultList.get(1), is(text.substring(WORDS_NUM_PER_LINE)));
    }
```

此时我们应该在before()方法中将WORDS_NUM_PER_LINE传给DefaultDialogTextFormatter，修改后的before()方法如下：

```java
    @Before
    public void before() {
        dialogTextFormatter = new DefaultDialogTextFormatter(WORDS_NUM_PER_LINE);
    }
```

这时DefaultDialogTextFormatter类会报错。我们在DefaultDialogTextFormatter类中添加如下内容

```java
    private int wordsNumPerLine;

    public DefaultDialogTextFormatter(int wordsNumPerLine) {
        this.wordsNumPerLine = wordsNumPerLine;
    }
```

代码编译正常后，我们执行新写的测试方法testFormatWithMultiLine()。
又是红条。
我们就修改实现方法，实现方法不只一种，我的策略是从第一个字符开始，每次取wordsNumPerLine字，不足字数就说明到了文本的末尾了。

修改后的format()如下：

```java
    @Override
    public List<String> format(String text) {
        List<String> result = new ArrayList<>();

        int start = 0;
        int end = wordsNumPerLine;
        while (start < text.length()) {
            if (text.length() < end) {
                end = text.length();
            }
            String lineText = text.substring(start, end);
            result.add(lineText);
            start = end;
            end = start + wordsNumPerLine;
        }
        return result;
    }
```

##### 第四步：显示简单对话文本（含颜色）

上步我们实现了文本的换行，这次我们要在单行文本的基础上，添加颜色功能。

目标效果如下：

![](./image/coloredSingleLine.png)

这次我们发现format()方法的返回值是List<String>，不足以存放颜色字段。我们需要添加一个类DialogFormattedText作为返回的对象。

```java
package billy.rpg.common.formatter;

import java.awt.*;

public class DialogFormattedText {

    private final String content;
    private final Color color;

    public DialogFormattedText(String content, Color color) {
        this.content = content;
        this.color = color;
    }

    public String getContent() {
        return content;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return content + "@(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + ")";
    }

}

```

注意要将DialogTextFormatter的format()方法要由原来的

    List<String> format(String text);

改为

    List<DialogFormattedText> format(String text);

这时会有编译错误，我们要改动的内容还真不少。

DefaultDialogTextFormatter类中result的类型要从

    List<String> result = new ArrayList<>();

改为

    List<DialogFormattedText> result = new ArrayList<>();

对result添加数据的操作也要从

    result.add(new DialogFormattedText(lineText, Color.BLACK));

改为

    result.add(new DialogFormattedText(lineText, Color.BLACK));

之前的两个测试类也相应在修改为

```java
    @Test
    public void testFormatWithSingleLine() {
        String text = "一切，都将在埋葬之地重生。";
        List<DialogFormattedText> resultList = dialogTextFormatter.format(text);
        assertNotNull(resultList);
        assertThat(resultList.size(), is(1));
        assertThat(resultList.get(0).getContent(), is(text));
    }
```

和

```java
    @Test
    public void testFormatWithMultiLine() {
        String text = "有些人将在埋葬之地重生，而另外的一些人，将在埋葬之地被埋葬！";
        List<DialogFormattedText> resultList = dialogTextFormatter.format(text);
        assertNotNull(resultList);
        assertThat(resultList.size(), is(2));
        assertThat(resultList.get(0).getContent(), is(text.substring(0, WORDS_NUM_PER_LINE)));
        assertThat(resultList.get(1).getContent(), is(text.substring(WORDS_NUM_PER_LINE)));
    }
```

同时，用于简单显示的debug()方法也应该有相应的变动。

```java
    private void debug(List<DialogFormattedText> resultList) {
        for (DialogFormattedText dialogFormattedText : resultList) {
            logger.debug(dialogFormattedText);
        }
    }
```

下面该说文本中含有颜色的事了，我们使用标签如<r></r>来表示红色，<g></g>来表示绿色，<y></y>表示黄色，<b></b>表示蓝色。

再贴下本步的目标图

![](./image/coloredSingleLine.png)

这样，我们的文本应该是`一切，都将在<y>埋葬之地</y>重生。`

测试方法如下：

```java
    @Test
    public void testFormatWithColorSingleLine() {
        String text = "一切，都将在<y>埋葬之地</y>重生。";
        List<DialogFormattedText> resultList = dialogTextFormatter.format(text);
        assertNotNull(resultList);
        assertThat(resultList.size(), is(3));
        assertThat(resultList.get(0).getContent(), is("一切，都将在"));
        assertThat(resultList.get(0).getColor(), is(Color.BLACK));
        assertThat(resultList.get(1).getContent(), is("埋葬之地"));
        assertThat(resultList.get(1).getColor(), is(Color.YELLOW));
        assertThat(resultList.get(2).getContent(), is("重生。"));
        assertThat(resultList.get(2).getColor(), is(Color.BLACK));
    }
```

执行测试类，失败，下面开始编写实现方法。format()方法如下：

```java
    public List<DialogFormattedText> format(String text) {
        List<DialogFormattedText> textListWithColor = processColorTag(text);
        return textListWithColor;
    }
```

添加processColorTag()方法如下：

```java
    private List<DialogFormattedText> processColorTag(String msg) {
        List<DialogFormattedText> msgListTemp = new ArrayList<>();

        String msgTemp = msg;
        while (true) {
            int colorTagPos = msgTemp.indexOf('<');
            if (colorTagPos == -1) {
                break;
            }
            String tagBegin = msgTemp.substring(colorTagPos, colorTagPos + "<c>".length());
            int indexOf = msgTemp.indexOf(tagBegin);
            String bef = msgTemp.substring(0, indexOf);
            msgListTemp.add(new DialogFormattedText(bef, Color.BLACK));
            String tagEnd = tagBegin.substring(0, 1) + "/" + tagBegin.substring(1);
            int indexOf2 = msgTemp.indexOf(tagEnd, indexOf);
            if (indexOf2 < 0) {
                throw new RuntimeException("unclose tag found!");
            }
            String coloredMsg = msgTemp.substring(indexOf + tagBegin.length(), indexOf2);
            Color color = getColor(tagBegin);
            msgListTemp.add(new DialogFormattedText(coloredMsg, color));
            msgTemp = msgTemp.substring(indexOf2 + tagEnd.length());
        }

        msgListTemp.add(new DialogFormattedText(msgTemp, Color.BLACK));

        return msgListTemp;
    }

    private Color getColor(String tagName) {
        char flagName = tagName.toLowerCase().charAt(1);
        if ('r' == flagName) {
            return Color.red;
        }
        if ('b' == flagName) {
            return Color.blue;
        }
        if ('g' == flagName) {
            return Color.green;
        }
        if ('y' == flagName) {
            return Color.yellow;
        }

        return Color.BLACK;
    }
```

执行新写的测试方法testFormatWithColorSingleLine()，正常。但是testFormatWithMultiLine()方法则断言失败了。
下一步我们就修复这个问题。

