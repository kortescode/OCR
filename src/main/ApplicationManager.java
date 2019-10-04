package main;

import util.ArgumentsException;
import widget.Window;

final public class ApplicationManager {

    public static boolean             DEBUG     = false;
    private static ApplicationManager mInstance = null;
    private final Window              window    = new Window("OCR");

    private ApplicationManager() {
    }

    public ApplicationManager init(String[] args) throws ArgumentsException {
        if (args.length < 1) {
            if (DEBUG)
                throw new ArgumentsException();
            else new Ocr(window);
        } else
            new Ocr(window, args[0]);
        return (this);
    }

    public void run() {
        window.pack();
        window.run();
    }

    static public ApplicationManager getInstance() {
        return ((mInstance != null) ? (mInstance) : (mInstance = new ApplicationManager()));
    }
}
