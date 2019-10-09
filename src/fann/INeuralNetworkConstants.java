package fann;

import bmp.ICharacterConstants;

public interface INeuralNetworkConstants
{	
	public static final String[] CHAR_ARRAY = { "A", "a", "B", "b", "C", "c", "D", "d", "E", "e", "F", "f", "G", "g",
		"H", "h", "I", "i", "J", "j", "K", "k", "L", "l", "M", "m", "N", "n", "0", "1", "2", "3", "4", "5", "6", "7",
		"8", "9", "O", "o", "P", "p", "Q", "q", "R", "r", "S", "s", "&", "'", "@", "`", "\\", "^", ":", ",", "$", "=",
		"!", ">", "-", "{", "(", "[", "<", "#", "%", "|", "+", ".", "?", "\"", "}", ")", "]", ";", "/", " ", "*", "~",
		"_", "T", "t", "U", "u", "V", "v", "W", "w", "X", "x", "Y", "y", "Z", "z" };

	public static final int INPUT_NEURON_NBR = ICharacterConstants.NORM_WIDTH * ICharacterConstants.NORM_HEIGHT;
	public static final int OUTPUT_NEURON_NBR = CHAR_ARRAY.length;
	
	public static final String DAT_PATH = System.getProperty("user.dir") + "/dat/";	
	public static final String NET_PATH = System.getProperty("user.dir") + "/net/";
	public static final String IMG_EXAMPLE_PATH = System.getProperty("user.dir") + "/img/";
	public static final String IMG_TMP_PATH = System.getProperty("user.dir") + "/img/tmp/";

	public static final String TRAIN_DAT_FILE = "ocr-train.dat";
	public static final String TEST_DAT_FILE = "ocr-test.dat";	
	public static final String NET_FILE = "ocr3.net";
	
	public static final int MIN_SPACE_CHARS = 100;
}
