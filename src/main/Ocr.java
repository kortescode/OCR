package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import util.ArgumentsException;
import util.Tools;
import widget.Button;
import widget.Label;
import widget.Panel;
import widget.TextField;
import widget.Window;
import bmp.ICharacterConstants;
import bmp.IImageConstants;
import fann.Dat;
import fann.INeuralNetworkConstants;
import fann.NeuralNetwork;

final public class Ocr extends KeyAdapter implements ActionListener, MouseListener {

    private static final String RESULTCONTAINERTITLE = "Optical Character Recognition";
    final private Panel         gui                  = new Panel();
    final private TextField     fileName             = new TextField(new Dimension(350, 25), this);
    final private Button        browse               = new Button("browse", new BrowseAction());
    final private Button        start                = new Button("start", this);
    final private Window        win_ref;
    final private JLabel        computedPicture      = new JLabel();
    final private JTextArea     resultContent        = new JTextArea(5, 20);
    final private GuiContent    reliabilityContent   = new GuiContent(" Reliability: ", "_");

    private NeuralNetwork       nn;

    public Ocr(Window window) {
        super();

        win_ref = window;
        this.init(window);
    }

    public Ocr(Window window, String file) {
        super();

        win_ref = window;
        this.init(window);
        this.execute(file);
    }

    private void init(Window win) {
        JPanel displaying = new JPanel(new BorderLayout());
        displaying.setBackground(Window.APP_BG);
        displaying.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), RESULTCONTAINERTITLE));

        JScrollPane scrollPane = new JScrollPane(resultContent);
        resultContent.setEditable(false);

        Panel computedPictureContainer = new Panel();
        computedPictureContainer.setPreferredSize(new Dimension(60, 60));
        computedPictureContainer.getBody().add(computedPicture);

        Panel resultContainer = new Panel();
        resultContainer.getTop().add(computedPictureContainer);
        resultContainer.getBottom().add(scrollPane);
        resultContainer.getBody().add(reliabilityContent);
        displaying.add(resultContainer);

        JPanel buttonContainer = new JPanel();
        buttonContainer.add(this.browse);
        buttonContainer.add(this.start);
        buttonContainer.setBackground(Window.APP_BG);

        this.fileName.addMouseListener(this);

        JPanel inputContainer = new JPanel();
        inputContainer.setBackground(Window.APP_BG);
        inputContainer.add(this.fileName, BorderLayout.NORTH);

        gui.getBottom().add(inputContainer, BorderLayout.NORTH);
        gui.getBottom().add(buttonContainer);
        gui.getBody().add(displaying);

        win.add(gui);

        // TEST: generate tmp image files
        // Dat.writeFilesFromDataset(true, true, true, INeuralNetworkConstants.IMG_EXAMPLE_PATH);
		
        nn = NeuralNetwork.getFromConfFile(INeuralNetworkConstants.NET_PATH + INeuralNetworkConstants.NET_FILE);
    }

    private void compute() {
        try {
            BufferedImage picture = ImageIO.read(new File(this.fileName.getText()));

            computedPicture.setIcon(new ImageIcon(picture));
            nn.compute(this.fileName.getText());
            resultContent.setText(nn.getResult());
            reliabilityContent.setResult(Tools.roundTo(nn.getReliability(), 2) + "%");
        } catch (Exception e) {
            System.out.println("Exception : " + e.toString());
            e.printStackTrace();
        }
    }

    public void execute(String file) {
        this.fileName.setText(file);
        this.actionPerformed(null);
    }

    public void actionPerformed(ActionEvent arg0) {
        File file;

        try {
            file = new File(this.fileName.getText());
            if (file.exists() && file.isFile()) {
                this.compute();
                this.win_ref.pack();
            } else
                throw new ArgumentsException();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Invalid File", "Error",
                                            JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        if (e.getKeyChar() == '\n')
            this.actionPerformed(null);
        ((TextField) e.getComponent()).setForeground(Color.DARK_GRAY);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (this.fileName.getText().isEmpty()) {
            this.fileName.setForeground(Color.GRAY);
            this.fileName.setText("File name");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (this.fileName.getText().isEmpty()) {
            this.fileName.setForeground(Color.GRAY);
            this.fileName.setText("File name");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (this.fileName.getText().isEmpty()) {
            this.fileName.setForeground(Color.GRAY);
            this.fileName.setText("File name");
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (this.fileName.getText().isEmpty()) {
            this.fileName.setForeground(Color.GRAY);
            this.fileName.setText("File name");
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (this.fileName.getText().isEmpty()) {
            this.fileName.setForeground(Color.GRAY);
            this.fileName.setText("File name");
        }
    }

    class BrowseAction implements ActionListener {
        JFileChooser fileChooser = new JFileChooser(".");

        public void actionPerformed(final ActionEvent e) {
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            final int returnVal = fileChooser.showOpenDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                final File fileChose = fileChooser.getSelectedFile();
                if (!checkFile(fileChose)) {
                    fileName.setText("");
                    return;
                }
                fileName.setText(fileChose.getPath());
            }
        }

        public boolean checkFile(final File file) {
            String ext;
            String filename;
            BufferedImage image = null;

            if (file != null && file.exists() && file.isFile()) {
                filename = file.getName();
                ext = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
                if (ext.equals("bmp") || ext.equals("BMP")) {
                    try {
                        if ((image = ImageIO.read(file)) == null) {
                            JOptionPane.showMessageDialog(null, "Error: " + file.getName()
                                                            + " can't be read.", "Error",
                                                            JOptionPane.ERROR_MESSAGE);
                            return false;
                        }
                    } catch (Exception e) {
                        System.out.println("Exception : " + e.toString());
                        JOptionPane.showMessageDialog(null, "Error: " + file.getName()
                                                        + " can't be read.", "Error",
                                                        JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error: " + file.getName()
                                                    + " is not a bmp file.", "Error",
                                                    JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                if (!(image.getWidth() == ICharacterConstants.WIDTH && image.getHeight() == ICharacterConstants.HEIGHT)
                                                && !(image.getWidth() == IImageConstants.WIDTH && image
                                                                                .getHeight() == IImageConstants.HEIGHT)) {
                    JOptionPane.showMessageDialog(null, "Error: " + file.getName()
                                                    + " is not at a good format.", "Error",
                                                    JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error: " + file.getName() + " doesn't exist.",
                                                "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            return true;
        }
    }

    private final class GuiContent extends Panel {
        private final Label label;
        public Label        res;

        public GuiContent(String label, double res) {
            this(label, String.valueOf(res));
        }

        public GuiContent(String label, String res) {
            this.label = new Label(label, new Font("Comic sans ms", Font.PLAIN, 14));
            this.res = new Label(res, new Font("Comic sans ms", Font.BOLD, 14));

            this.init();
        }

        public GuiContent(String label) {
            this.label = new Label(label, new Font("Comic sans ms", Font.BOLD, 14));
            this.res = new Label("", new Font("Comic sans ms", Font.BOLD, 14));

            this.init();
        }

        private void init() {
            this.getLeft().add(this.label);
            this.getBody().add(this.res);
        }

        public String getName() {
            return (this.label.getText());
        }

        public String getResult() {
            return (this.res.getText());
        }

        public void setResult(double res) {
            this.setResult(String.valueOf(res));
        }

        public void setResult(String res) {
            this.res.setText(res);
        }
    }
}
