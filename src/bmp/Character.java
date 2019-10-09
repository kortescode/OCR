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
			this.resize(ICharacterConstants.NORM_WIDTH, ICharacterConstants.NORM_HEIGHT);
			this.normalize();
		}
	}
	
	public static Character getFromImage(BufferedImage image)
	{
		return new Character(image);
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
