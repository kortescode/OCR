package main;

import gui.Window;

final public class ApplicationManager
{
    public static boolean             DEBUG     = false;
    private static ApplicationManager mInstance = null;
    private final Window              window    = new Window("OCR");

    private ApplicationManager() {
    }

    public ApplicationManager init(String[] args)
    {
        new Ocr(window);
        return (this);
    }

    public void run()
    {
        window.pack();
        window.run();
    }

    static public ApplicationManager getInstance()
    {
        return ((mInstance != null) ? (mInstance) : (mInstance = new ApplicationManager()));
    }
}
