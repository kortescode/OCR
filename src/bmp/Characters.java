package bmp;

import java.awt.image.BufferedImage;

public class Characters
{
	private boolean areNormalized = false;
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
	
	public boolean areNormalized()
	{
		return this.areNormalized;
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
					//this.characters[y / ICharactersConstants.MATRIX_MULT_REDUCTION][x / ICharactersConstants.MATRIX_MULT_REDUCTION].writeInFile("test_" + y / ICharactersConstants.MATRIX_MULT_REDUCTION + "_" + x / ICharactersConstants.MATRIX_MULT_REDUCTION + ".jpg");
				}
			}
		}
		
		normalizeCharacters();
		
		return this.characters;
	}
	
	private Limit getExistingCharacterLimit(int x, int y)
	{
		for (int n = 0; n < this.characterLimitNbr; ++n)
		{
			if (this.characterLimits[n] != null && x >= this.characterLimits[n].xMin
										 		&& x <= this.characterLimits[n].xMax
										 		&& y >= this.characterLimits[n].yMin
										 		&& y <= this.characterLimits[n].yMax)
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
		Limit limit = new Limit(currentX - 1, currentX + 1, currentY - 1 , currentY + 1);

		while (!limit.equals(previousLimit))
		{
			previousLimit = Limit.copy(limit);
			
			for (y = currentY; y <= limit.yMax && y < this.image.getHeight(); ++y)
			{
				empty = true;
				for (x = currentX; x <= limit.xMax && x < this.image.getWidth(); ++x)
				{
					if (this.image.getRGB(x, y) <= EColors.GREY.getRGB())
					{
						empty = false;
						limit.xMax = (x + 1 > limit.xMax) ? x + 1 : limit.xMax;
					}
				}
				if (!empty)
					limit.yMax = (y + 1 > limit.yMax) ? y + 1 : limit.yMax;
				else
					break;
			}
			
			for (y = currentY; y <= limit.yMax && y < this.image.getHeight(); ++y)
			{
				empty = true;
				for (x = currentX; x >= limit.xMin && x > 0; --x)
				{
					if (this.image.getRGB(x, y) <= EColors.GREY.getRGB())
					{
						empty = false;
						limit.xMin = (x - 1 < limit.xMin) ? x - 1 : limit.xMin;
					}
				}
				if (!empty)
					limit.yMax = (y + 1 > limit.yMax) ? y + 1 : limit.yMax;
				else
					break;
			}
			
			for (y = currentY; y >= limit.yMin && y > 0; --y)
			{
				empty = true;
				for (x = currentX; x <= limit.xMax && x < this.image.getWidth(); ++x)
				{
					if (this.image.getRGB(x, y) <= EColors.GREY.getRGB())
					{
						empty = false;
						limit.xMax = (x + 1 > limit.xMax) ? x + 1 : limit.xMax;
					}
				}
				if (!empty)
				{
					limit.yMin = (y - 1 < limit.yMin) ? y - 1 : limit.yMin;
				}
				else
				{
					break;
				}
			}
			
			for (y = currentY; y >= limit.yMin && y > 0; --y)
			{
				empty = true;
				for (x = currentX; x >= limit.xMin && x > 0; --x)
				{
					if (this.image.getRGB(x, y) <= EColors.GREY.getRGB())
					{
						empty = false;
						limit.xMin = (x - 1 < limit.xMin) ? x - 1 : limit.xMin;
					}
				}
				if (!empty)
				{
					limit.yMin = (y - 1 < limit.yMin) ? y - 1 : limit.yMin;
				}
				else
				{
					break;
				}
			}
		}
		
		return limit;
	}
	
	private void normalizeCharacters()
	{
		boolean blank = true;
		int normalizedX = 0;
		int normalizedY = 0;
		Character[][] normalizedCharacters = new Character[this.image.getHeight() / ICharactersConstants.MATRIX_MULT_REDUCTION][this.image.getWidth() / ICharactersConstants.MATRIX_MULT_REDUCTION * (ICharactersConstants.MATRIX_SPACE_SENSI_Y + 1)];
		
		if (!this.areNormalized)
		{	
			this.areNormalized = true;
			
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
	}
}
