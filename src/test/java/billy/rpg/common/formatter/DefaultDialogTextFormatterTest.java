package billy.rpg.common.formatter;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

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
        List<String> resultList = dialogTextFormatter.format(text);
        assertNotNull(resultList);
        assertThat(resultList.size(), is(1));
        assertThat(resultList.get(0), is(text));

        debug(resultList);
    }

    @Test
    public void testFormatWithMultiLine() {
        String text = "有些人将在埋葬之地重生，而另外的一些人，将在埋葬之地被埋葬！";
        List<String> resultList = dialogTextFormatter.format(text);
        assertNotNull(resultList);
        assertThat(resultList.size(), is(2));
        assertThat(resultList.get(0), is(text.substring(0, WORDS_NUM_PER_LINE)));
        assertThat(resultList.get(1), is(text.substring(WORDS_NUM_PER_LINE)));

        debug(resultList);
    }

    private void debug(List<String> resultList) {
        for (String dialogFormattedText : resultList) {
            logger.debug(dialogFormattedText);
        }
    }

}
