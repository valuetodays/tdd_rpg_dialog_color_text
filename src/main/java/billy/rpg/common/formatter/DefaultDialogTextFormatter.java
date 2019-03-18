package billy.rpg.common.formatter;

import java.util.ArrayList;
import java.util.List;

public class DefaultDialogTextFormatter implements DialogTextFormatter {

    private int wordsNumPerLine;

    public DefaultDialogTextFormatter(int wordsNumPerLine) {
        this.wordsNumPerLine = wordsNumPerLine;
    }

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

}
