package seedu.nursesched.ui;

import seedu.nursesched.appointment.Appointment;

import javax.naming.directory.SearchResult;
import java.util.ArrayList;
import java.util.Scanner;

public class Ui {

    public void greetingMessage() {
        System.out.println("Welcome to Nurse Sched!");
        System.out.println("Please enter your command: ");
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
        }
        System.out.println("You have " + apptList.size() + " appointment(s)");
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
