package gui;

import java.awt.Font;

public final class GuiContent extends Panel
{
	public Label res;
	private final Label label;

	public GuiContent(String label, double res)
	{
		this(label, String.valueOf(res));
	}

	public GuiContent(String label, String res)
	{
		this.label = new Label(label, new Font("Comic sans ms", Font.PLAIN, 14));
		this.res = new Label(res, new Font("Comic sans ms", Font.BOLD, 14));

		this.init();
	}

	public GuiContent(String label)
	{
		this.label = new Label(label, new Font("Comic sans ms", Font.BOLD, 14));
		this.res = new Label("", new Font("Comic sans ms", Font.BOLD, 14));

		this.init();
	}

	private void init()
	{
		this.getLeft().add(this.label);
		this.getBody().add(this.res);
	}

	public String getName()
	{
		return (this.label.getText());
	}

	public String getResult()
	{
		return (this.res.getText());
	}

	public void setResult(double res)
	{
		this.setResult(String.valueOf(res));
	}

	public void setResult(String res)
	{
		this.res.setText(res);
	}
}