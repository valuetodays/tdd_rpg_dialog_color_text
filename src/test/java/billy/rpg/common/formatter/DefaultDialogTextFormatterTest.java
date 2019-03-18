package billy.rpg.common.formatter;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.awt.*;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class DefaultDialogTextFormatterTest {
    private final Logger logger = Logger.getLogger(getClass());

    private final static int WORDS_NUM_PER_LINE = 18;

    private DialogTextFormatter dialogTextFormatter;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void before() {
        dialogTextFormatter = new DefaultDialogTextFormatter(WORDS_NUM_PER_LINE);
    }

    @Test
    public void testFormatWithSingleLine() {
        String text = "一切，都将在埋葬之地重生。";
        List<DialogFormattedText> resultList = dialogTextFormatter.format(text);
        assertNotNull(resultList);
        assertThat(resultList.size(), is(2));
        assertThat(resultList.get(0).getContent(), nullValue());
        assertThat(resultList.get(1).getContent(), is(text));

        debug(resultList);
    }

    @Test
    public void testFormatWithMultiLine() {
        String text = "有些人将在埋葬之地重生，而另外的一些人，将在埋葬之地被埋葬！";
        List<DialogFormattedText> resultList = dialogTextFormatter.format(text);
        assertNotNull(resultList);
        assertThat(resultList.size(), is(4));
        assertThat(resultList.get(0).getContent(), nullValue());
        assertThat(resultList.get(1).getContent(), is(text.substring(0, WORDS_NUM_PER_LINE)));
        assertThat(resultList.get(2).getContent(), nullValue());
        assertThat(resultList.get(3).getContent(), is(text.substring(WORDS_NUM_PER_LINE)));

        debug(resultList);
    }

    @Test
    public void testFormatWithColorSingleLine() {
        String text = "一切，都将在<y>埋葬之地</y>重生。";
        List<DialogFormattedText> resultList = dialogTextFormatter.format(text);
        assertNotNull(resultList);
        assertThat(resultList.size(), is(4));
        assertThat(resultList.get(0).getContent(), nullValue());
        assertThat(resultList.get(1).getContent(), is("一切，都将在"));
        assertThat(resultList.get(1).getColor(), is(Color.BLACK));
        assertThat(resultList.get(2).getContent(), is("埋葬之地"));
        assertThat(resultList.get(2).getColor(), is(Color.YELLOW));
        assertThat(resultList.get(3).getContent(), is("重生。"));
        assertThat(resultList.get(3).getColor(), is(Color.BLACK));

        debug(resultList);
    }

    @Test
    public void testFormatWithColorMultiLine() {
        String text = "有些人将在<r>埋葬之地</r>重生，而另外的一些人，将在埋葬之地被埋葬！";
        List<DialogFormattedText> resultList = dialogTextFormatter.format(text);
        assertNotNull(resultList);
        assertThat(resultList.size(), is(6));
        assertThat(resultList.get(0).getContent(), nullValue());
        assertThat(resultList.get(1).getContent(), is("有些人将在"));
        assertThat(resultList.get(1).getColor(), is(Color.BLACK));
        assertThat(resultList.get(2).getContent(), is("埋葬之地"));
        assertThat(resultList.get(2).getColor(), is(Color.RED));
        assertThat(resultList.get(3).getContent(), is("重生，而另外的一些"));
        assertThat(resultList.get(3).getColor(), is(Color.BLACK));
        assertThat(resultList.get(4).getContent(), nullValue());
        assertThat(resultList.get(5).getContent(), is("人，将在埋葬之地被埋葬！"));
        assertThat(resultList.get(5).getColor(), is(Color.BLACK));

        debug(resultList);
    }

    @Test
    public void testFormat_shouldThrowExceptionWhenUncloseColorTag() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("unclose tag found!");
        String dialogText = "一切，都将在<y>埋葬之地重生。";
        dialogTextFormatter.format(dialogText);
    }

    @Test
    public void testFormat_shouldThrowExceptionWhenErrorColorTagWith() {
        expectedException.expect(StringIndexOutOfBoundsException.class);
        String dialogText = "一切，都将在埋葬之地重生。<";
        dialogTextFormatter.format(dialogText);
    }

    private void debug(List<DialogFormattedText> resultList) {
        for (DialogFormattedText dialogFormattedText : resultList) {
            logger.debug(dialogFormattedText);
        }
    }

}
