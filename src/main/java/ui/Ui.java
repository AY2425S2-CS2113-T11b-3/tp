package ui;

import java.util.Scanner;

public class Ui {

    public void greetingMessage() {
        System.out.println("Welcome to Nurse Sched!");
        System.out.println("Please enter your command: ");
    }

    public void exitMessage() {
        System.out.println("Goodbye!");
    }

    public String readCommand(Scanner in) {
        return in.nextLine();
    }

    public void showError(String message) {
        System.out.println(message);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }


}
