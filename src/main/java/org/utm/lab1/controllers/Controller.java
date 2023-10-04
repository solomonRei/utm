package org.utm.lab1.controllers;

import org.utm.lab1.factory.annotations.Command;
import org.utm.lab1.factory.annotations.Component;
import org.utm.lab1.utils.ConsoleOutputMessages;

@Component
public class Controller {

    @Command(name = "b")
    public void backMessageCommand() {
        ConsoleOutputMessages.clearConsole();
        ConsoleOutputMessages.greetingMessage();
    }

}
