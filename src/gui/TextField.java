package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyListener;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class TextField extends JTextField
{
    private final int     ID;
    protected static int  count      = 0;
    private Dimension     defaultDim = new Dimension(150, 28);
    private final Pattern pattern    = Pattern.compile("[0-9]{3}");
    private Font          police     = new Font("", Font.BOLD, 14);

    public TextField(Dimension dim, KeyListener action)
    {
        super("");
        this.ID = count++;
        this.setPreferredSize(dim);
        this.addKeyListener(action);
        this.setFont(police);
    }

    public TextField(KeyListener action)
    {
        super("");
        this.ID = count++;
        this.setPreferredSize(defaultDim);
        this.addKeyListener(action);
        this.setFont(police);
    }

    public TextField(String text, Dimension dim, KeyListener action)
    {
        super(text);
        this.ID = count++;
        this.setPreferredSize(dim);
        this.addKeyListener(action);
        this.setFont(police);
    }

    public TextField(String text, KeyListener action)
    {
        super(text);
        this.ID = count++;
        this.setPreferredSize(defaultDim);
        this.addKeyListener(action);
        this.setFont(police);
    }

    public void correctField()
    {
        this.setText(this.getText().replaceAll("\\s+", " ").trim());
    }

    public int getID()
    {
        return ID;
    }

    public boolean verifField()
    {
        String text = this.getText();

        if (!text.isEmpty())
            return (true);
        JOptionPane.showMessageDialog(null, "Please fill out the both fields", "Error", JOptionPane.ERROR_MESSAGE);
        return (false);
    }

    // public boolean onlyNum(int lim) {
    // try {
    //
    // } catch (Exception e) {
    // e.printStackTrace();
    // return (false);
    // }
    // String value = this.getText().trim();
    // System.out.println("|"+value+"|");
    // return (pattern.matcher(value).matches()
    // && Integer.valueOf(value) >= 0 && Integer.valueOf(value) <= lim);
    // }
}