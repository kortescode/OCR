package fann;

import java.io.File;
import java.io.IOException;
import bmp.Image;
import com.googlecode.fannj.Fann;

public class NeuralNetwork
{
	private Fann fann = null;
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
	
	private void setResult(float[] outputArray)
	{
		int nSaved = -1;
		float output = 0;
		
		for (int n = 0; n < INeuralNetworkConstants.OUTPUT_NEURON_NBR; ++n)
			if (outputArray[n] > output && n != 77) // sans le char 'espace'
			{
				output = outputArray[n];
				nSaved = n;
			}
		this.result += INeuralNetworkConstants.CHAR_ARRAY[nSaved];
		++this.result_nbr;
		this.reliability += outputArray[nSaved] * 100;
	}
	
	public boolean compute(String inputFileName) throws IOException
	{
		File file;
		bmp.Character[][] characters;
	
		if (inputFileName != null)
		{
			this.result = "";
			this.result_nbr = 0;
			this.reliability = 0;
			file = new File(inputFileName);
			if ((characters = (Image.getFromFile(file)).getCharacters()) != null)
				for (int i = 0; i < characters.length; ++i)
				{
					for (int j = 0; j < characters[i].length; ++j)
					{
						if (characters[i][j] != null)
							this.setResult(this.fann.run(characters[i][j].getBinaryArray()));
						else
							this.result += " ";
					}
					if (i + 1 < characters.length)
						this.result += "\n";
				}
			this.result = this.result.trim()
									 .replaceAll("\n", " ")
									 .replaceAll(" {100,}", " ")
									 .replaceAll(" {2,}", "");
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
}
