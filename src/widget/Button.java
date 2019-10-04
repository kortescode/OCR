package widget;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class Button extends JButton implements MouseListener, KeyListener {
    final protected Color  BORDER_COLOR = Color.decode("#000000");
    protected Color        firstColor   = Color.decode("#c1d5e8");

    protected Color        secondColor  = Color.WHITE;
    final protected String TEXT;

    public Button(String text) {
        super(text);
        this.TEXT = text;
        init();
    }

    public Button(String text, ActionListener action) {
        super(text);
        this.addActionListener(action);
        this.TEXT = text;
        init();
    }

    public Button(String text, ActionListener action, Dimension dim) {
        super(text);
        this.addActionListener(action);
        this.setPreferredSize(dim);
        this.TEXT = text;
        init();
    }

    public Button(String text, ActionListener action, Dimension dim, Font police) {
        super(text);
        this.addActionListener(action);
        this.setFont(police);
        this.setPreferredSize(dim);
        this.TEXT = text;
    }

    public Button(String text, ActionListener action, Font police) {
        super(text);
        this.addActionListener(action);
        this.setFont(police);
        this.TEXT = text;
        init();
    }

    public Button(String text, Dimension dim) {
        super(text);
        this.setPreferredSize(dim);
        this.TEXT = text;
        init();
    }

    public void init() {
        this.addMouseListener(this);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (this.isEnabled()) {
            secondColor = Window.APP_BG;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (this.isEnabled()) {
            secondColor = Color.white;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (this.isEnabled()) {
            secondColor = Window.APP_BG;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (this.isEnabled()) {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            this.setBorderPainted(true);
            firstColor = Window.APP_BG;
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (this.isEnabled()) {
            this.setBorderPainted(false);
            firstColor = Color.decode("#c1d5e8");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (this.isEnabled()) {
            secondColor = Window.APP_BG;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (this.isEnabled()) {
            secondColor = Color.white;
        }
    }

    @Override
    protected void paintComponent(Graphics gp) {
        Graphics2D gp2 = (Graphics2D) gp;
        int height = this.getHeight();
        int width = this.getWidth();

        gp2.setPaint(new GradientPaint(width, height / 5, secondColor, width, height, firstColor));
        gp.fillRoundRect(0, 0, width, height, 5, 5);

        gp.setColor(BORDER_COLOR);
        gp.drawRoundRect(0, 0, width - 1, height - 1, 5, 5);

        gp.setColor(Color.darkGray);
        gp.drawString(TEXT, (width / 2) - (8 * TEXT.length()) / 2, (height / 2) + 4);
        this.setHorizontalTextPosition((int) Component.CENTER_ALIGNMENT);
    }

    @Override
    public void setEnabled(boolean enable) {
        this.setFocusPainted(false);
        this.setBorderPainted(false);
        secondColor = Color.decode("#F7F7F0");
        firstColor = Color.decode("#F7F7F0");
        super.setEnabled(enable);
    }
}