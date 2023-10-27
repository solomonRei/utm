package org.utm.lab2;

import org.utm.lab2.behaviours.ApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContext("src/main/resources/lab2/");
        applicationContext.run();
    }
}
