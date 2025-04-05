package seedu.nursesched.ui;

import seedu.nursesched.appointment.Appointment;
import java.util.ArrayList;
import java.util.Scanner;

public class Ui {

    public void showGreetingMessage() {
        System.out.println("Welcome to NurseSched!");
    }

    public static void printSearchResults(ArrayList searchResults, String keyword) {
        System.out.println("You have " + searchResults.size() + " search results for keyword: " + keyword);
        for (int i = 0; i < searchResults.size(); i++) {
            System.out.println((i+1) + ". " + searchResults.get(i));
        }
    }

    public static void printAppointmentList(ArrayList<Appointment> apptList) {
        int index = 1;
        boolean isDone = false;
        String statusBadge = "[ ]";
        for (Appointment appointment : apptList) {

            isDone = appointment.getStatus();
            if (isDone) {
                statusBadge = "[X]";
            }else{
                statusBadge = "[ ]";
            }

            System.out.println(index+ ". "+ statusBadge + appointment);
            index += 1;
        }
        System.out.println("You have " + apptList.size() + " appointment(s)");
    }

    public void exitMessage() {
        System.out.println("Goodbye!");
    }

    public String readCommand(Scanner in) {
        System.out.println("----------------------------");
        System.out.println("Please enter your command: ");
        return in.nextLine();
    }

    public void showResults(){
        System.out.println("\nResult:");
    }

    public void showError(String message) {
        System.out.println(message);
    }

}
