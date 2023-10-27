package org.utm.lab2.behaviours;

import org.utm.lab2.services.FileChangeDetector;

import java.util.Scanner;

public class ApplicationContext {

    private final String folderPath;

    public ApplicationContext(String folderPath) {
        this.folderPath = folderPath;
    }

    public void run() {
        FileChangeDetector detector = new FileChangeDetector(folderPath);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter command (commit, info <filename>, status, exit):");
            String command = scanner.nextLine();
            switch (command) {
                case "commit" -> detector.commit();
                case "info" -> {
                    System.out.println("Enter file name:");
                    String fileName = scanner.nextLine();
                    detector.info(fileName);
                }
                case "status" -> detector.status();
                case "exit" -> {
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("Invalid command.");
            }
        }
    }

}
