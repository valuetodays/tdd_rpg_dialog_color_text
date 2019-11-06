package billy.rpg.common.formatter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

/**
 * @author lei.liu
 * @since 2019-11-05 18:21
 */
public class TestPanel extends JPanel {


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setContentPane(new TestPanel());
        frame.setTitle("测试对话框格式化");
        frame.setLocation(500, 100);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.pack();
    }

    public TestPanel() {
        setPreferredSize(new Dimension(640, 480));
        try {
            initComponents();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assignValues();
    }

    private DialogTextFormatter formatter18 = new DefaultDialogTextFormatter(18);
    private DialogTextFormatter formatter3 = new DefaultDialogTextFormatter(3);
    private DialogTextFormatter formatter8 = new DefaultDialogTextFormatter(8);

    private static final int OFFSET_X = 30;
    private static final int OFFSET_Y = 30;
    private static Font FONT_DLG_MSG = new Font("楷体", Font.PLAIN, 20);

    @Override
    public void paint(Graphics g) {
//        super.paint(g);

        Font oldFont = g.getFont();
        Color oldColor = g.getColor();

        g.setFont(FONT_DLG_MSG);
        String text = "有些人将在<r>埋葬之地</r>重生，而另外的一些人，将在埋葬之地被埋葬！";
        List<DialogFormattedText> resultList18 = formatter18.format(text);
        List<DialogFormattedText> resultList3 = formatter3.format(text);
        List<DialogFormattedText> resultList8 = formatter8.format(text);
        drawFormattedText(g, resultList18, OFFSET_X, OFFSET_Y);
        drawFormattedText(g, resultList3, OFFSET_X + 300, OFFSET_Y + (int)(20*2.5));
        drawFormattedText(g, resultList8, OFFSET_X + 50, OFFSET_Y + (20*5));
        g.setColor(oldColor);
        g.setFont(oldFont);
    }

    private void drawFormattedText(Graphics g, List<DialogFormattedText> resultList, int x, int y) {

        int fontHeight = g.getFontMetrics().getHeight();
        int tmpX = x;
        int tmpY = y;
        for (int i = 0; i < resultList.size(); i++) {
            DialogFormattedText dialogFormattedText = resultList.get(i);
            if (dialogFormattedText.isNewLine()) {
                tmpX = x;
                tmpY += (i == 0 ? 0 : fontHeight * 1.5);
            } else {
                Color color = dialogFormattedText.getColor();
                String content = dialogFormattedText.getContent();

                g.setColor(color);
                g.drawString(content, tmpX, tmpY);
                tmpX += g.getFontMetrics(FONT_DLG_MSG).stringWidth(content);
            }
        }
    }

    private void initComponents() {
        this.setLayout(null);
    }

    private void assignValues() {

    }

}
