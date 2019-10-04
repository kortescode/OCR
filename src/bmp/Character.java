package bmp;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Character
{
	BufferedImage character = null;
	
	public Character(BufferedImage image)
	{
		if ((this.character = image) != null)
		{			
			this.crop();
			this.resize(ICharacterConstants.NORM_WIDTH, ICharacterConstants.NORM_HEIGHT);
			this.normalize();
		}
	}
	
	public static Character getFromImage(BufferedImage image)
	{
		return new Character(image);
	}
	
	private void crop()
	{
		//int width;
		//int height;
		int xMin = this.character.getWidth() - 1;
		int xMax = 0;
		int yMin = this.character.getHeight() - 1;
		int yMax = 0;
		boolean empty = true;
		
		for (int x = 0; x < this.character.getWidth(); ++x)
		{
			for (int y = 0; y < this.character.getHeight(); ++y)
			{
				if (this.character.getRGB(x, y) <= EColors.GREY.getRGB())
				{
					xMin = (x < xMin) ? x : xMin;
					xMax = (x > xMax) ? x : xMax;
					yMin = (y < yMin) ? y : yMin;
					yMax = (y > yMax) ? y : yMax;
					empty = false;
				}
			}
		}
		if (empty)
		{
			xMin = 0;
			xMax = this.character.getWidth() - 1;
			yMin = 0;
			yMax = this.character.getHeight() - 1;
		}
		//width = ((xMax + 1) - xMin >= ICharacterConstants.NORM_WIDTH) ? (xMax + 1) - xMin : this.character.getWidth() - xMax;
		//height = ((yMax + 1) - yMin >= ICharacterConstants.NORM_HEIGHT) ? (yMax + 1) - yMin : this.character.getHeight() - yMax;
		this.character = this.character.getSubimage(xMin, yMin, (xMax + 1) - xMin, (yMax + 1) - yMin);
		//this.character = this.character.getSubimage(xMin, yMin, width, height);
	}
	
	private void resize(int width, int height)
	{
		BufferedImage resizedImage = new BufferedImage(width, height, this.character.getType());
		
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(this.character, 0, 0, width, height, null);
		g.dispose();
		this.character = resizedImage;
	}
	
	private void normalize()
	{
		for (int x = 0; x < this.character.getWidth(); ++x)
		{
			for (int y = 0; y < this.character.getHeight(); ++y)
			{
				if (this.character.getRGB(x, y) <= EColors.GREY.getRGB())
					this.character.setRGB(x, y, EColors.BLACK.getRGB());
				else
					this.character.setRGB(x, y, EColors.WHITE.getRGB());
			}
		}
	}
	
	public String getBinaryString()
	{
		String buffer = "";
		
		for (int x = 0; x < this.character.getWidth(); ++x)
			for (int y = 0; y < this.character.getHeight(); ++y)
				buffer += (this.character.getRGB(x, y) != EColors.WHITE.getRGB()) ? "1.000000 " : "0.000000 ";
		return buffer + "\n";
	}
	
	public float[] getBinaryArray()
	{
		float[] array = new float[this.character.getWidth() * this.character.getHeight()];
	
		for (int x = 0; x < this.character.getWidth(); ++x)
			for (int y = 0; y < this.character.getHeight(); ++y)
				array[x * this.character.getWidth() + y] += (this.character.getRGB(x, y) == EColors.WHITE.getRGB()) ? -1.0 : 1.0;
		return array;
	}
	
	public boolean writeInFile(String outputFilename)
	{
		File output;
		String ext;
		
		if (outputFilename != null)
		{
    		output = new File(outputFilename);
    		ext = outputFilename.substring(outputFilename.lastIndexOf(".") + 1, outputFilename.length());
    		try
    		{
    			return ImageIO.write(this.character, ext, output);
    		}
    		catch (Exception e)
    		{
				System.out.println("Exception : " + e.toString());
    		}
		}
		return false;
	}
}
