package fann;

import java.io.File;
import java.io.FileWriter;

import bmp.Image;

public class Dat
{
	public static boolean writeFilesFromDataset(boolean trainFile, boolean testFile, boolean tmpImageFiles, String... inputDirectories)
	{
		String buffer = "";
		int characters_nbr = 0;
		File[][] files;
		bmp.Character[][] characters;
		FileWriter trainFileWriter;
		FileWriter testFileWriter;
		
		files = new File[inputDirectories.length][];
		for (int n = 0; n < inputDirectories.length; ++n)
			files[n] = (new File(inputDirectories[n])).listFiles();
		
		try
		{
			trainFileWriter = new FileWriter(INeuralNetworkConstants.DAT_PATH + INeuralNetworkConstants.TRAIN_DAT_FILE);
			testFileWriter = new FileWriter(INeuralNetworkConstants.DAT_PATH + INeuralNetworkConstants.TEST_DAT_FILE);

			for (int i = 0; i < files.length; ++i)
				for (int j = 0; j < files[i].length; ++j)
						if (!files[i][j].getName().equals(".DS_Store"))
						{
								System.out.println(files[i][j].getName());
								characters_nbr += (Image.getFromFile(files[i][j])).getCharacterNbr();
						}
			
			buffer += characters_nbr + " " + INeuralNetworkConstants.INPUT_NEURON_NBR + " " + INeuralNetworkConstants.OUTPUT_NEURON_NBR + "\n";
			
			for (int i = 0; i < files.length; ++i)
				for (int j = 0, result = 0; j < files[i].length; ++j)
				{
					if (!files[i][j].getName().equals(".DS_Store") && (characters = (Image.getFromFile(files[i][j])).getCharacters()) != null)
					{
						if (tmpImageFiles)
							characters[0][0].writeInFile(INeuralNetworkConstants.IMG_TMP_PATH + files[i][j].getName());
					
						buffer += characters[0][0].getBinaryString();
						for (int n = 0; n < INeuralNetworkConstants.OUTPUT_NEURON_NBR; ++n)
							buffer += (n == result) ? "1.000000 " : "0.000000 ";
						buffer += "\n";
						++result;
					}
				}
			
			if (trainFile)
				trainFileWriter.write(buffer);
			if (testFile)
				testFileWriter.write(buffer);
			
			trainFileWriter.close();
			testFileWriter.close();
			return true;
		}
		catch (Exception e)
		{
			System.out.println("Exception : " + e.toString());
            e.printStackTrace();
		}
		return false;
	}
}
