package fann;

import java.awt.image.BufferedImage;
import java.io.IOException;
import bmp.Image;
import com.googlecode.fannj.Fann;

public class NeuralNetwork
{
	private Fann fann = null;
	private Image image = null;
	private String result = null;
	private int result_nbr = 0;
	private float reliability = 0;

	public NeuralNetwork(String confFile)
	{
		this.fann = new Fann(confFile);
	}
	
	protected void finalize() throws Throwable
	{
		if (this.fann != null)
			this.fann.close();
		super.finalize();
	}
	
	public static NeuralNetwork getFromConfFile(String confFile)
	{
		return new NeuralNetwork(confFile);
	}
	
	public boolean compute(BufferedImage image) throws IOException
	{
		bmp.Character[][] characters;
	
		if (image != null)
		{
			this.result = "";
			this.result_nbr = 0;
			this.reliability = 0;
			this.image = Image.getFromImage(image);
			
			if ((characters = this.image.getCharacters()) != null)
			{
				for (int i = 0; i < characters.length; ++i)
				{
					for (int j = 0; j < characters[i].length; ++j)
					{
						if (characters[i][j] != null)
							this.setResult(this.fann.run(characters[i][j].getBinaryArray()));
						else
							this.setResult(" ");
					}
					if (i + 1 < characters.length)
						this.setResult("\n");
				}
			}
			
			this.normalizeResult();
			
			return true;
		}
		return false;
	}
	
	public String getResult()
	{
		return this.result;
	}
	
	public float getReliability()
	{
		return this.reliability / this.result_nbr;
	}
	
	public BufferedImage getImage()
	{
		return this.image.getImage();
	}
	
	private void setResult(String result)
	{
		this.result += result;
	}
	
	private void setResult(float[] outputArray)
	{
		int nSaved = -1;
		float output = 0;
		
		for (int n = 0; n < INeuralNetworkConstants.OUTPUT_NEURON_NBR; ++n)
		{
			if (outputArray[n] > output && n != 77) // Without ' ' character
			{
				output = outputArray[n];
				nSaved = n;
			}
		}
		
		++this.result_nbr;
		
		this.result += INeuralNetworkConstants.CHAR_ARRAY[nSaved];
		this.reliability += outputArray[nSaved] * 100;
	}
	
	private void normalizeResult()
	{
		this.result = this.result.trim().replaceAll("\n", " ").replaceAll(" {" + INeuralNetworkConstants.MIN_SPACE_CHARS + ",}", " ").replaceAll(" {2,}", "");
	}
}
