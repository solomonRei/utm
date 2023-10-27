package org.utm.lab2.behaviours;

import java.util.Scanner;

public class ApplicationContext {

    private final DetectionThread detectionThread;

    public ApplicationContext(String folderPath) {
        this.detectionThread = new DetectionThread(folderPath);
    }

    public void run() {
        detectionThread.startScheduler();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter command (commit, info, status, exit):");
            String command = scanner.nextLine();

            switch (command) {
                case "commit" -> detectionThread.commitForConsole();
                case "info" -> {
                    System.out.println("Enter file name:");
                    String fileName = scanner.nextLine();
                    detectionThread.infoForConsole(fileName);
                }
                case "status" -> detectionThread.statusForConsole();
                case "exit" -> {
                    detectionThread.stop();
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("Invalid command.");
            }
        }
    }

}


