package billy.rpg.common.formatter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultDialogTextFormatter implements DialogTextFormatter {

    private int wordsNumPerLine;

    public DefaultDialogTextFormatter(int wordsNumPerLine) {
        this.wordsNumPerLine = wordsNumPerLine;
    }

    @Override
    public List<DialogFormattedText> format(String text) {
        List<DialogFormattedText> textListWithColor = processColorTag(text);
        return textListWithColor;
        /*
        List<DialogFormattedText> result = new ArrayList<>();

        int start = 0;
        int end = wordsNumPerLine;
        while (start < text.length()) {
            if (text.length() < end) {
                end = text.length();
            }
            String lineText = text.substring(start, end);
            result.add(new DialogFormattedText(lineText, Color.BLACK));
            start = end;
            end = start + wordsNumPerLine;
        }
        return result;*/
    }

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

}
