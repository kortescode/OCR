package widget;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GraphicalDrawingPanel extends JPanel {

    final protected int      AXE_PADDING = 5;
    protected Color          color;

    final protected int      PADDING     = 30;
    public ArrayList<Double> values;
    protected int            xMax        = 100;

    public GraphicalDrawingPanel(ArrayList<Double> values, Color color) {
        super();

        this.setValues(values);
        this.color = color;
        this.setBackground(Color.BLACK);
    }

    protected void drawGraph(Graphics g, ArrayList<Double> values) {
        final int graphWidth = (this.getWidth() - PADDING * 2) - PADDING / 2;
        final int graphHeight = (this.getHeight() - PADDING * 2) - PADDING / 2;
        final int unit = graphWidth / (values.size() * 2);
        int x1 = PADDING;

        for (int i = 0; i < values.size(); i++, x1 += unit * 2) {
            int val = (int) (values.get(i) * graphHeight);
            g.fillRect(x1, (this.getHeight() - PADDING) - val, unit, val);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        g.setColor(Color.WHITE);
        g.drawRect(PADDING, PADDING, this.getWidth() - PADDING * 2, this.getHeight() - PADDING * 2);

        g.drawString("0", PADDING / 2 - AXE_PADDING, this.getHeight() - PADDING);
        g.drawString("-1", PADDING, this.getHeight() - PADDING / 2 + AXE_PADDING);
        g.drawString("1", PADDING / 2 - AXE_PADDING, PADDING + AXE_PADDING);
        g.drawString(String.valueOf(this.xMax), this.getWidth() - PADDING - AXE_PADDING,
                                        this.getHeight() - PADDING / 2 + AXE_PADDING);

        g.setColor(this.color);
        if (this.xMax > 1)
            drawGraph(g, values);
    }

    public void setValues(ArrayList<Double> values) {
        this.values = values;
        this.xMax = this.values.size() + 1;
    }
}
