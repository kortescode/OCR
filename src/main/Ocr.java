package main;

import static main.ApplicationManager.DEBUG;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import bmp.Image;
import util.Tools;
import fann.Dat;
import fann.INeuralNetworkConstants;
import fann.NeuralNetwork;
import gui.Button;
import gui.GuiContent;
import gui.Label;
import gui.Panel;
import gui.TextField;
import gui.Window;

final public class Ocr extends KeyAdapter implements ActionListener
{
	private static final int DRAWINGSTROKE 		 	 = 7;
    private static final int DRAWINGIMGWIDTH 		 = 750;
    private static final int DRAWINGIMGHEIGHT 		 = 450;
    private static final String IMGFOLDERPATH 		 = "./img/";
    private static final String RESULTCONTAINERTITLE = "Optical Character Recognition";
    private static final String DEFAULTFILENAME      = "Browse a .bmp or draw";
    
    private NeuralNetwork       nn;
    private BufferedImage		image;
    final private Window        windowRef;
    final private JLabel        computedImage        = new JLabel();
    final private JTextArea     resultContent        = new JTextArea(5, 20);
    final private GuiContent    reliabilityContent   = new GuiContent(" Reliability: ", "__ %");
    final private TextField     fileName             = new TextField(new Dimension(350, 25), this);
    
    public Ocr(Window window)
    {
        super();

        windowRef = window;
        this.init(window);
    }

    public void actionPerformed(ActionEvent arg0)
    {
    	this.compute();
    	this.display(this.nn.getResult(), Tools.roundTo(this.nn.getReliability(), 2) + " %", this.nn.getImage()); 
    }

    @Override
    public void keyPressed(final KeyEvent e)
    {
        if (e.getKeyChar() == '\n')
            this.actionPerformed(null);
        ((TextField) e.getComponent()).setForeground(Color.DARK_GRAY);
    }
    
    private void init(Window win)
    {
        JPanel computedPictureContainer = new JPanel();
        computedPictureContainer.add(computedImage);
        computedPictureContainer.setSize(computedImage.getWidth(), computedImage.getHeight());
        
        Panel resultContainer = new Panel();
        resultContainer.getTop().add(computedPictureContainer);
        resultContainer.getBody().add(reliabilityContent);
        resultContainer.getBottom().add(new JScrollPane(resultContent));

        JPanel displaying = new JPanel(new BorderLayout());
        displaying.setBackground(Window.APP_BG);
        displaying.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), RESULTCONTAINERTITLE));
        displaying.add(resultContainer);
        
        JPanel buttonContainer = new JPanel();
        Font buttonContainerFont  = new Font("Comic sans ms", Font.PLAIN, 14);
        buttonContainer.setBackground(Window.APP_BG);
        buttonContainer.add(new Button("Browse", new BrowseAction()));
        buttonContainer.add(new Label(" or ", buttonContainerFont));
        buttonContainer.add(new Button("Draw", new DrawAction()));
        buttonContainer.add(new Label("    ", buttonContainerFont));
        buttonContainer.add(new Button("Compute", this));

        JPanel inputContainer = new JPanel();
        inputContainer.setBackground(Window.APP_BG);
        inputContainer.add(this.fileName, BorderLayout.NORTH);
        
        Panel gui = new Panel();
        gui.getBody().add(displaying);
        gui.getBottom().add(inputContainer, BorderLayout.NORTH);
        gui.getBottom().add(buttonContainer);
        
        this.fileName.setText(DEFAULTFILENAME);
        this.fileName.setForeground(Color.GRAY);

        this.resultContent.setEditable(false);
        
        win.add(gui);

        nn = NeuralNetwork.getFromConfFile(INeuralNetworkConstants.NET_PATH + INeuralNetworkConstants.NET_FILE);
        
        if (DEBUG)
        {
        	// Generate tmp image files
        	Dat.writeFilesFromDataset(true, true, true, INeuralNetworkConstants.IMG_EXAMPLE_PATH);
        }
    }
    
    private void compute()
    {
        try
        {
        	this.nn.compute(this.image);
        }
        catch (Exception e)
        {
            System.out.println("Exception : " + e.toString());
            e.printStackTrace();
        }
    }
    
    private void display(String result, String reliability, BufferedImage computedImage)
    {
    	this.resultContent.setText(result);
        this.reliabilityContent.setResult(reliability);
    	this.computedImage.setIcon(new ImageIcon(computedImage));
    	this.windowRef.pack();
    }
    
    private class BrowseAction implements ActionListener
    {
    	private JFileChooser fileChooser = new JFileChooser(IMGFOLDERPATH);
    	
    	@Override
    	public void actionPerformed(final ActionEvent e)
    	{
    		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    		final int returnVal = fileChooser.showOpenDialog(null);

    		if (returnVal == JFileChooser.APPROVE_OPTION)
    		{
    			final File fileChose = fileChooser.getSelectedFile();
    			
    			if (!checkFile(fileChose))
    			{
    				fileName.setText(DEFAULTFILENAME);
    				return;
    			}
    			try
    	        {
    				fileName.setText(fileChose.getPath());
    				fileName.setForeground(Color.GRAY);
    				image = ImageIO.read(new File(fileName.getText()));
    				
    				display("", "__ %", image);
    	        }
    	        catch (Exception ex)
    	        {
    	        	fileName.setText(DEFAULTFILENAME);
    	            JOptionPane.showMessageDialog(null, "Error: Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
    	        }
    		}
    	}
    	
    	private boolean checkFile(final File file)
    	{
    		String ext;
    		String filename;
    		BufferedImage image = null;

    		if (file != null && file.exists() && file.isFile())
    		{
    			filename = file.getName();
    			ext = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
    			if (ext.equals("bmp") || ext.equals("BMP"))
    			{
    				try
    				{
    					if ((image = ImageIO.read(file)) == null)
    					{
    						JOptionPane.showMessageDialog(null, "Error: " + file.getName() + " can't be read.", "Error", JOptionPane.ERROR_MESSAGE);
    						return false;
    					}
    				}
    				catch (Exception e)
    				{
    					System.out.println("Exception : " + e.toString());
    					JOptionPane.showMessageDialog(null, "Error: " + file.getName() + " can't be read.", "Error", JOptionPane.ERROR_MESSAGE);
    					return false;
    				}
    			}
    			else
    			{
    				JOptionPane.showMessageDialog(null, "Error: " + file.getName() + " is not a bmp file.", "Error", JOptionPane.ERROR_MESSAGE);
    				return false;
    			}
    		}
    		else
    		{
    			JOptionPane.showMessageDialog(null, "Error: " + file.getName() + " doesn't exist.", "Error", JOptionPane.ERROR_MESSAGE);
    			return false;
    		}
    		return true;
    	}
    }
    
    @SuppressWarnings("deprecation")
    private class DrawAction implements ActionListener
    {
    	private Graphics2D graphics;
    	final private JDialog dialog = new JDialog(windowRef, "Draw", true);
		final private BufferedImage imageTmp = new BufferedImage(DRAWINGIMGWIDTH, DRAWINGIMGHEIGHT, BufferedImage.TYPE_INT_RGB);

    	public DrawAction()
    	{
    		super();
    		
    		init();
    	}
    	
    	@Override
    	public void actionPerformed(final ActionEvent e)
    	{
    		this.graphics.setColor(new Color(255, 255, 255));
    		this.graphics.fillRect(0, 0, DRAWINGIMGWIDTH, DRAWINGIMGHEIGHT);
    		this.dialog.setSize(DRAWINGIMGWIDTH + 50, DRAWINGIMGHEIGHT + 150);
    		
    		this.dialog.show();
    	}
    	
    	private void init()
        {
    		final JLabel drawingImage = new JLabel();
            drawingImage.setIcon(new ImageIcon(imageTmp));
            drawingImage.addMouseMotionListener(new MouseAdapter()
            {
                @Override
                public void mouseDragged(MouseEvent e)
                {
            		int x = (int) e.getPoint().getX();
            		int y = (int) e.getPoint().getY();

                    graphics.setColor(Color.BLACK);
                    graphics.fillRect(x, y, DRAWINGSTROKE, DRAWINGSTROKE);

                    drawingImage.setIcon(new ImageIcon(imageTmp));
                }
            });
            
    		JPanel drawingImageContainer = new JPanel(new BorderLayout());
    		drawingImageContainer.setBackground(Window.APP_BG);
    		drawingImageContainer.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Click to paint"));
    		drawingImageContainer.add(drawingImage);
    		
    		JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonContainer.setBackground(Window.APP_BG);
            buttonContainer.add(new Button("Done", new DoneAction()));
            buttonContainer.add(new Button("Cancel", new CancelAction()));
            
            Panel gui = new Panel();
            gui.getBody().add(drawingImageContainer, BorderLayout.CENTER);
            gui.getBottom().add(buttonContainer);

            this.dialog.add(gui);
            
            this.graphics = imageTmp.createGraphics();
        }

    	private class DoneAction implements ActionListener
    	{
			 @Override
             public void actionPerformed(ActionEvent e)
             {
    			 fileName.setText(DEFAULTFILENAME);
    			 image = Image.copyImage(imageTmp);
             	
    			 display("", "__ %", image);
             	
                 dialog.hide();
             }
    	}
    
    	private class CancelAction implements ActionListener
    	{
    		@Override
            public void actionPerformed(ActionEvent e)
            {
            	image = null;
            	
            	dialog.hide();
            }
    	}
    }
}