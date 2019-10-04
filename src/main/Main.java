package main;

import static main.ApplicationManager.DEBUG;
import fann.INeuralNetworkConstants;

public class Main
{
    public static void main(String argv[])
    {
        String[] sample = new String[] { INeuralNetworkConstants.IMG_EXAMPLE_PATH + "a_small.bmp" };

        try {
            ApplicationManager.getInstance()
            .init(argv.length < 1 && DEBUG ? sample : argv)
            .run();
        } catch (Exception e) {
            if (DEBUG)
                System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
