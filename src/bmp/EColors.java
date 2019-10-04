package bmp;

import java.awt.Color;

public enum EColors
{
	BLACK(new Color(0, 0, 0).getRGB()),
	WHITE(new Color(255, 255, 255).getRGB()),
	GREY(new Color(200, 200, 200).getRGB());
	
	private int rgb = 0;
	
	EColors(int rgb)
	{
		this.rgb = rgb;
	}
	
	public int getRGB()
	{
		return this.rgb;
	}
}
