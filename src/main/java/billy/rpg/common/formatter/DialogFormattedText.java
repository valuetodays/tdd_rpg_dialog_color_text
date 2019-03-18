package billy.rpg.common.formatter;

import java.awt.*;

public class DialogFormattedText {

    public static final DialogFormattedText NULL = new DialogFormattedText(null, Color.WHITE);

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
