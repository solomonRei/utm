package org.utm.lab1.utils;

import org.utm.lab1.repository.FileManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private static final FileManager fileManager = new FileManager("logs.txt");

    public static void log(String message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = dateFormat.format(new Date());
        String formattedMessage = "[" + timestamp + "] " + message + "\n";
        fileManager.saveData(formattedMessage, true);
    }
}
