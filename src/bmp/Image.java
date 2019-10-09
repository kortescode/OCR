package bmp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image
{
	private BufferedImage image = null;
	private Character[][] characters = null;
	
	public Image(File file) throws IOException
	{
		this.image = ImageIO.read(file);
		this.generateCharacters();
	}
	
	public static Image getFromFile(File file) throws IOException
	{
		return new Image(file);
	}
	
	private void generateCharacters() throws IOException
	{
		this.characters = Characters.getFromImage(this.image).getCharacters();
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
}
