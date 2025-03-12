import java.util.Scanner;
import java.util.ArrayList;
import appointment.Appointment;
import appointment.Parser;

import java.time.LocalDate;
import java.time.LocalTime;

public class NurseSched {
    /**
     * Main entry-point for the NurseSched application.
     */
    public static void main(String[] args) {

        ArrayList<Appointment> apptList = new ArrayList<>();

        //test Appointment class methods
        String name = "James Gosling";
        String notes = "Test notes";
        LocalTime startTime = LocalTime.now();
        LocalDate date = LocalDate.now();

        Scanner line = new Scanner(System.in);
        while(true) {
            Parser parser = Parser.extractCommand(line);
            if (parser == null) {
                System.out.println("Input is null");
                break;
            }
            String type = parser.getType();
            String command = parser.getCommand();
            switch (type) {
            case "appt":
                if (command.equals("add")) {
                    System.out.println("Appointment added");
                }
                if (command.equals("del")) {
                    System.out.println("Appointment deleted");
                }
                if (command.equals("mark")) {
                    System.out.println("Appointment marked");
                }
                break;
            case "pf":
                //Todo
                break;
            case "shift":
                //Todo
                break;
            default:
                System.out.println("Unknown command");
                break;
            }
        }

        Appointment.addAppt(apptList, name, startTime, startTime, date, notes);
        System.out.println("List after adding:" + apptList);

        System.out.println("Status of appointment: " + apptList.get(0).getStatus());
        Appointment.markApptByIndex(apptList,0);
        System.out.println("Status of appointment after marking by index: " + apptList.get(0).getStatus());

        Appointment.unmarkApptByIndex(apptList,0);
        System.out.println("Status of appointment after unmarking by index: " + apptList.get(0).getStatus());

        Appointment.markApptByPatient(apptList,name, startTime,date);
        System.out.println("Status of appointment after marking by Patient name: " + apptList.get(0).getStatus());

        Appointment.unmarkApptByPatient(apptList,name, startTime,date);
        System.out.println("Status of appointment after unmarking by index: " + apptList.get(0).getStatus());

        Appointment.deleteApptByPatient(apptList,name, startTime,date);
        System.out.println("List after deleting: " + apptList);

        boolean isRunning = false;
        while (isRunning) {
            Scanner input = new Scanner(System.in);
        }
    }
}
