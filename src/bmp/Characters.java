package bmp;

import static main.ApplicationManager.DEBUG;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Characters
{
	private int characterLimitNbr = 0;
	private BufferedImage image = null;
	private Character[][] characters = null;
	private Limit[] characterLimits = null;

	public Characters(BufferedImage image)
	{
		this.image = image;
	}
	
	public static Characters getFromImage(BufferedImage image)
	{
		return new Characters(image);
	}
	
	public Character[][] getCharacters()
	{
		Limit limit = null;
		
		this.characterLimitNbr = 0;
		this.characterLimits = new Limit[this.image.getWidth() * this.image.getHeight() / ICharactersConstants.MATRIX_MULT_REDUCTION];
		this.characters = new Character[this.image.getHeight() / ICharactersConstants.MATRIX_MULT_REDUCTION][this.image.getWidth() / ICharactersConstants.MATRIX_MULT_REDUCTION];
		
		for (int y = 0; y < this.image.getHeight(); ++y)
		{
			for (int x = 0; x < this.image.getWidth(); ++x)
			{
				if ((limit = getExistingCharacterLimit(x, y)) != null)
				{
					x = limit.xMax + 1;
				}
				else if (this.image.getRGB(x, y) <= EColors.GREY.getRGB())
				{
					limit = getNewCharacterLimit(x, y);
					
					this.characterLimits[this.characterLimitNbr++] = limit;
					this.characters[y / ICharactersConstants.MATRIX_MULT_REDUCTION][x / ICharactersConstants.MATRIX_MULT_REDUCTION] = Character.getFromImage(this.image.getSubimage(limit.xMin + 1, limit.yMin + 1, limit.xMax - limit.xMin - 1, limit.yMax - limit.yMin - 1));
					
					if (DEBUG)
					{
						// Create .jpg with recognized characters
						this.characters[y / ICharactersConstants.MATRIX_MULT_REDUCTION][x / ICharactersConstants.MATRIX_MULT_REDUCTION].writeInFile("test_" + y / ICharactersConstants.MATRIX_MULT_REDUCTION + "_" + x / ICharactersConstants.MATRIX_MULT_REDUCTION + ".jpg");
					}
				}
			}
		}
		
		normalizeCharacters();
		
		drawCharacterLimitsOnImage();
		
		return this.characters;
	}
	
	private Limit getExistingCharacterLimit(int x, int y)
	{
		for (int n = 0; n < this.characterLimitNbr; ++n)
		{
			if (this.characterLimits[n] != null && x >= this.characterLimits[n].xMin && x <= this.characterLimits[n].xMax && y >= this.characterLimits[n].yMin && y <= this.characterLimits[n].yMax)
				return this.characterLimits[n];
		}
		return null;
	}
	
	private Limit getNewCharacterLimit(int currentX, int currentY)
	{
		int x;
		int y;
		boolean empty;
		Limit previousLimit = null;
		Limit currentLimit = new Limit(currentX, currentX, currentY, currentY);

		while (!currentLimit.equals(previousLimit))
		{
			previousLimit = Limit.copy(currentLimit);
			
			for (y = currentLimit.yMin; y <= currentLimit.yMax && y < this.image.getHeight(); ++y)
			{
				empty = true;
				for (x = currentLimit.xMin; x <= currentLimit.xMax && x < this.image.getWidth(); ++x)
				{
					if (this.image.getRGB(x, y) <= EColors.GREY.getRGB())
					{
						empty = false;
						currentLimit.xMax = (x + 1 > currentLimit.xMax) ? x + 1 : currentLimit.xMax;
					}
				}
				if (!empty)
					currentLimit.yMax = (y + 1 > currentLimit.yMax) ? y + 1 : currentLimit.yMax;
				else
					break;
			}
			
			for (y = currentLimit.yMin; y <= currentLimit.yMax && y < this.image.getHeight(); ++y)
			{
				empty = true;
				for (x = currentLimit.xMax; x >= currentLimit.xMin && x > 0; --x)
				{
					if (this.image.getRGB(x, y) <= EColors.GREY.getRGB())
					{
						empty = false;
						currentLimit.xMin = (x - 1 < currentLimit.xMin) ? x - 1 : currentLimit.xMin;
					}
				}
				if (!empty)
					currentLimit.yMax = (y + 1 > currentLimit.yMax) ? y + 1 : currentLimit.yMax;
				else
					break;
			}
			
			for (y = currentLimit.yMax; y >= currentLimit.yMin && y > 0; --y)
			{
				empty = true;
				for (x = currentLimit.xMin; x <= currentLimit.xMax && x < this.image.getWidth(); ++x)
				{
					if (this.image.getRGB(x, y) <= EColors.GREY.getRGB())
					{
						empty = false;
						currentLimit.xMax = (x + 1 > currentLimit.xMax) ? x + 1 : currentLimit.xMax;
					}
				}
				if (!empty)
					currentLimit.yMin = (y - 1 < currentLimit.yMin) ? y - 1 : currentLimit.yMin;
				else
					break;
			}
			
			for (y = currentLimit.yMax; y >= currentLimit.yMin && y > 0; --y)
			{
				empty = true;
				for (x = currentLimit.xMax; x >= currentLimit.xMin && x > 0; --x)
				{
					if (this.image.getRGB(x, y) <= EColors.GREY.getRGB())
					{
						empty = false;
						currentLimit.xMin = (x - 1 < currentLimit.xMin) ? x - 1 : currentLimit.xMin;
					}
				}
				if (!empty)
					currentLimit.yMin = (y - 1 < currentLimit.yMin) ? y - 1 : currentLimit.yMin;
				else
					break;
			}
		}
		
		--currentLimit.yMin; //selection d'1 pixel plus haut
		
		return currentLimit;
	}
	
	private void normalizeCharacters()
	{
		boolean blank = true;
		int normalizedX = 0;
		int normalizedY = 0;
		Character[][] normalizedCharacters = new Character[this.image.getHeight() / ICharactersConstants.MATRIX_MULT_REDUCTION][this.image.getWidth() / ICharactersConstants.MATRIX_MULT_REDUCTION * (ICharactersConstants.MATRIX_SPACE_SENSI_Y + 1)];
		
		for (int y = 0; y < this.image.getHeight() / ICharactersConstants.MATRIX_MULT_REDUCTION; ++y)
		{
			blank = true;
			while (blank)				
			{
				for (int xTmp = 0; xTmp < this.image.getWidth() / ICharactersConstants.MATRIX_MULT_REDUCTION; ++xTmp)
				{
					if (this.characters[y][xTmp] != null)
					{
						blank = false;
						break;
					}
				}
				y += blank ? 1 : 0;
				if (blank && y >= this.image.getHeight() / ICharactersConstants.MATRIX_MULT_REDUCTION)
					blank = false;
			}
			
			blank = true;
			normalizedX = 0;
			for (int x = 0; x < this.image.getWidth() / ICharactersConstants.MATRIX_MULT_REDUCTION; ++x)
			{
				for (int sensiY = 0; sensiY <= ICharactersConstants.MATRIX_SPACE_SENSI_Y && y + sensiY < this.image.getHeight() / ICharactersConstants.MATRIX_MULT_REDUCTION; ++sensiY)
				{
					if (this.characters[y + sensiY][x] != null)
					{
						blank = false;
					}
					normalizedCharacters[normalizedY][normalizedX++] = this.characters[y + sensiY][x];
				}
			}
			
			normalizedY += blank ? 0 : 1;
			y += ICharactersConstants.MATRIX_SPACE_SENSI_Y;
		}
		
		this.characters = normalizedCharacters;
	}

	private void drawCharacterLimitsOnImage()
	{
		Graphics2D graphics = null;
		
		for (int n = 0; n < this.characterLimitNbr; ++n)
		{
			if (this.characterLimits[n] != null)
			{
				graphics = this.image.createGraphics();
				
				graphics.setColor(Color.red);
				graphics.setStroke(new BasicStroke(3));
				
				graphics.drawLine(this.characterLimits[n].xMin, this.characterLimits[n].yMin, this.characterLimits[n].xMax, this.characterLimits[n].yMin);
				graphics.drawLine(this.characterLimits[n].xMin, this.characterLimits[n].yMax, this.characterLimits[n].xMax, this.characterLimits[n].yMax);
				
				graphics.drawLine(this.characterLimits[n].xMin, this.characterLimits[n].yMin, this.characterLimits[n].xMin, this.characterLimits[n].yMax);
				graphics.drawLine(this.characterLimits[n].xMax, this.characterLimits[n].yMin, this.characterLimits[n].xMax, this.characterLimits[n].yMax);
			}
		}
	}
}