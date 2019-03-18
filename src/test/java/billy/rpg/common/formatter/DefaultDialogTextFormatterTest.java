package billy.rpg.common.formatter;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DefaultDialogTextFormatterTest {
    private final Logger logger = Logger.getLogger(getClass());

    private final static int WORDS_NUM_PER_LINE = 18;

    private DialogTextFormatter dialogTextFormatter;

    @Before
    public void before() {
        dialogTextFormatter = new DefaultDialogTextFormatter(WORDS_NUM_PER_LINE);
    }

    @Test
    public void testFormatWithSingleLine() {
        String text = "一切，都将在埋葬之地重生。";
        List<DialogFormattedText> resultList = dialogTextFormatter.format(text);
        assertNotNull(resultList);
        assertThat(resultList.size(), is(1));
        assertThat(resultList.get(0).getContent(), is(text));

        debug(resultList);
    }

    @Test
    public void testFormatWithMultiLine() {
        String text = "有些人将在埋葬之地重生，而另外的一些人，将在埋葬之地被埋葬！";
        List<DialogFormattedText> resultList = dialogTextFormatter.format(text);
        assertNotNull(resultList);
        assertThat(resultList.size(), is(2));
        assertThat(resultList.get(0).getContent(), is(text.substring(0, WORDS_NUM_PER_LINE)));
        assertThat(resultList.get(1).getContent(), is(text.substring(WORDS_NUM_PER_LINE)));

        debug(resultList);
    }

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

        debug(resultList);
    }

    private void debug(List<DialogFormattedText> resultList) {
        for (DialogFormattedText dialogFormattedText : resultList) {
            logger.debug(dialogFormattedText);
        }
    }

}
