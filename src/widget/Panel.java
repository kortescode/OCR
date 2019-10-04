package widget;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class Panel extends JPanel {
    protected JPanel body;

    protected JPanel bottom;

    private JPanel   container;
    protected JPanel left;
    protected JPanel right;
    protected JPanel top;

    public Panel() {
        super(new BorderLayout(10, 10));

        this.body = new JPanel(new BorderLayout());
        this.bottom = new JPanel(new BorderLayout());
        this.top = new JPanel(new BorderLayout());
        this.right = new JPanel(new BorderLayout());
        this.left = new JPanel(new BorderLayout());

        this.body.setBackground(Window.APP_BG);
        this.top.setBackground(Window.APP_BG);
        this.bottom.setBackground(Window.APP_BG);
        this.right.setBackground(Window.APP_BG);
        this.left.setBackground(Window.APP_BG);

        // container = new JPanel(new BorderLayout(5, 5));

        this.add(body, BorderLayout.CENTER);
        this.add(bottom, BorderLayout.SOUTH);
        this.add(top, BorderLayout.NORTH);
        this.add(left, BorderLayout.WEST);
        this.add(right, BorderLayout.EAST);
        this.setBackground(Window.APP_BG);

        // this.add(container);

        this.setBackground(Window.APP_BG);
    }

    public JPanel getBody() {
        return body;
    }

    public JPanel getBottom() {
        return bottom;
    }

    public JPanel getLeft() {
        return left;
    }

    public JPanel getRight() {
        return right;
    }

    public JPanel getTop() {
        return top;
    }

    public void setBody(JPanel body) {
        this.body = body;
    }

    public void setBottom(JPanel bottom) {
        this.bottom = bottom;
    }

    public void setLeft(JPanel left) {
        this.left = left;
    }

    public void setRight(JPanel right) {
        this.right = right;
    }

    public void setTop(JPanel top) {
        this.top = top;
    }
}
