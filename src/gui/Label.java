package gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Label extends JLabel
{
	protected Color color;
	protected Font police;

	public Label(String name)
	{
		super(name);

		this.police = new Font("Comic sans ms", Font.BOLD, 15);
		this.color = Color.BLACK;

		this.init();
	}

	public Label(String name, Color color)
	{
		super(name);

		this.police = new Font("Comic sans ms", Font.BOLD, 15);
		this.color = color;

		this.init();
	}

	public Label(String name, Font police, Color color)
	{
		super(name);

		this.police = police;
		this.color = color;

		this.init();
	}

	public Label(String name, Font police)
	{
		super(name);

		this.police = police;
		this.color = Color.BLACK;

		this.init();
	}

	private void init()
	{
		this.setFont(this.police);
		this.setForeground(this.color);
		this.setHorizontalAlignment(SwingConstants.CENTER);
	}
}
