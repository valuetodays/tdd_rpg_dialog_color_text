package billy.rpg.common.formatter;

import java.util.ArrayList;
import java.util.List;

public class DefaultDialogTextFormatter implements DialogTextFormatter {

    @Override
    public List<String> format(String text) {
        List<String> result = new ArrayList<>();
        result.add(text);
        return result;
    }

}
