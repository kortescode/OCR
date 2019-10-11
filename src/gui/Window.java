package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Window extends JFrame
{
    static final public Color APP_BG     = Color.decode("#c8ddf2");
    
    final private JPanel      container  = new JPanel(new BorderLayout());
    final private JTabbedPane tabbedPane = new JTabbedPane();

    public Window(String title)
    {
        this.setTitle(title);
        this.init();
    }

    public Window()
    {
        this.init();
    }

    private void init()
    {
        this.setSize(900, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        container.add(tabbedPane);

        this.setContentPane(container);
    }

    public void run()
    {
        this.setVisible(true);
    }

    public void setTabPanel(Map<String, JPanel> tabMap, int index)
    {
        int mnemonic = 0x31;
        int tabIndex = 0;

        for (Iterator<Entry<String, JPanel>> it = tabMap.entrySet().iterator(); it.hasNext();)
        {
            Entry<String, JPanel> pair = it.next();

            this.tabbedPane.addTab(pair.getKey(), pair.getValue());
            this.tabbedPane.setMnemonicAt(tabIndex++, mnemonic++);
        }
    }

    public void stop()
    {
        this.setVisible(false);
    }
}
