package bmp;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image
{
	private BufferedImage image = null;
	private Character[][] characters = null;
	
	public Image(BufferedImage image)
	{
		this.image = copyImage(image);
		this.generateCharacters();
	}
	
	public Image(File file) throws IOException
	{
		this.image = ImageIO.read(file);
		this.generateCharacters();
	}
	
	public static Image getFromImage(BufferedImage image)
	{
		return new Image(image);
	}
	
	public static Image getFromFile(File file) throws IOException
	{
		return new Image(file);
	}
	
	public BufferedImage getImage()
	{
		return this.image;
	}
	
	public Character[][] getCharacters()
	{
		return this.characters;
	}
	
	public int getCharacterNbr()
	{
		int count = 0;
		
		for (int i = 0; this.characters != null && i < this.characters.length; ++i)
			for (int j = 0; this.characters[i] != null && j < this.characters[i].length; ++j)
				++count;
		return count;
	}
	
	private void generateCharacters()
	{
		this.characters = Characters.getFromImage(this.image).getCharacters();
	}
	
	public static BufferedImage copyImage(BufferedImage image)
	{
		ColorModel colorModel = image.getColorModel();

		return new BufferedImage(colorModel, image.copyData(null), colorModel.isAlphaPremultiplied(), null);
	}
}
