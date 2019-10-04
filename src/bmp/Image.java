package bmp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image
{
	private EImageType type = EImageType.UNKNOW;
	private BufferedImage image = null;
	private Character[][] characters = null;
	
	public Image(File file) throws IOException
	{
		this.image = ImageIO.read(file);
		this.initType();
		this.generateCharacters();
	}
	
	public static Image getFromFile(File file) throws IOException
	{
		return new Image(file);
	}
	
	private void initType()
	{
		if (this.image.getWidth() == ICharacterConstants.WIDTH && this.image.getHeight() == ICharacterConstants.HEIGHT)
			this.type = EImageType.CHAR;
		else if (this.image.getWidth() == IImageConstants.WIDTH && this.image.getHeight() == IImageConstants.HEIGHT)
			this.type = EImageType.IMG;		
	}
	
	private void generateCharacters()
	{
		if (this.type == EImageType.CHAR)
		{
			this.characters = new Character[1][1];
			this.characters[0][0] = Character.getFromImage(this.image);
		}
		else if (this.type == EImageType.IMG)
		{
			;// TO DO
		}
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
