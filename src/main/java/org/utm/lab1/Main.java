package org.utm.lab1;

import org.utm.lab1.factory.ApplicationContext;

public class Main {

    public static void main(String[] args) {
        try {
            ApplicationContext applicationContext = new ApplicationContext("org.utm.lab1");
            applicationContext.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
