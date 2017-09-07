package org.utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by msingh on 28/11/2016.
 */
public class LoggerUtil {

    public static Logger getLogger(Class glass) {
        Logger log = Logger.getLogger( glass.getName() );
        try {
            FileHandler fh = new FileHandler(glass.getName() + ".log", false);
            fh.setFormatter(new SimpleFormatter());
            log.addHandler(fh);
            log.setLevel(Level.INFO);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return log;
    }
}
