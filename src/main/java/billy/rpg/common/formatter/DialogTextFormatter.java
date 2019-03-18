package billy.rpg.common.formatter;

import java.util.List;

public interface DialogTextFormatter {
    List<DialogFormattedText> format(String text);
}
