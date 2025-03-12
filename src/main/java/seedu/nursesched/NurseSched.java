package seedu.nursesched;

import java.util.ArrayList;
import java.util.Scanner;
import appointment.Appointment;

public class NurseSched {
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        System.out.println("Hello from NurseSched!\n");
        System.out.println("What is your name?");
        ArrayList<Appointment> apptList = new ArrayList<>();

        Scanner in = new Scanner(System.in);
        System.out.println("Hello " + in.nextLine());

    }
}
