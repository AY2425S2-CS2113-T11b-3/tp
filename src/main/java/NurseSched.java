import java.util.Scanner;
import java.util.ArrayList;

import parser.ShiftParser;
import parser.ApptParser;
import parser.Parser;
import shift.Shift;

import java.time.LocalDate;
import java.time.LocalTime;

public class NurseSched {
    /**
     * Main entry-point for the NurseSched application.
     */
    public static void main(String[] args) {

//        ArrayList<Appointment> apptList = new ArrayList<>();

        //test Appointment class methods
//        String name = "James Gosling";
//        String notes = "Test notes";
//        LocalTime startTime = LocalTime.now();
//        LocalDate date = LocalDate.now();

        Scanner in = new Scanner(System.in);
        String line = in.nextLine();

        while (true) {
            String type = Parser.extractType(line);
            switch (type) {
            case "appt":
                ApptParser apptParser = ApptParser.extractInputs(line);
                if (apptParser == null) {
                    System.out.println("Invalid inputs for Appointment based command!");
                    return;
                }
                String command = apptParser.getCommand();
                if (command.equals("add")) {
                    System.out.println("Appointment added");
                    return;
                }
                if (command.equals("del")) {
                    System.out.println("Appointment deleted");
                    return;
                }
                if (command.equals("mark")) {
                    System.out.println("Appointment marked");
                    return;
                }
                break;
            case "pf":
                //Todo
                break;
            case "shift":
                ShiftParser shiftParser = ShiftParser.extractInputs(line);
                if (shiftParser == null) {
                    System.out.println("Invalid inputs for Appointment based command!");
                    return;
                }
                String shift = shiftParser.getCommand();
                if (shift.equals("add")) {
                    Shift.addShift(
                            shiftParser.getName(),
                            shiftParser.getStartTime(),
                            shiftParser.getEndTime(),
                            shiftParser.getDate(),
                            shiftParser.getNotes()
                    );
                    System.out.println("Shift added");
                    Shift.listShifts();
                    return;
                }
                break;
            default:
                System.out.println("Unknown command!");
                break;
            }
        }

        /*Appointment.addAppt(name, startTime, startTime, date, notes);
        System.out.println("List after adding:" + apptList);

        System.out.println("Status of appointment: " + apptList.get(0).getStatus());
        Appointment.markApptByIndex(0);
        System.out.println("Status of appointment after marking by index: " + apptList.get(0).getStatus());

        Appointment.unmarkApptByIndex(0);
        System.out.println("Status of appointment after unmarking by index: " + apptList.get(0).getStatus());

        Appointment.markApptByPatient(name, startTime,date);
        System.out.println("Status of appointment after marking by Patient name: " + apptList.get(0).getStatus());

        Appointment.unmarkApptByPatient(name, startTime,date);
        System.out.println("Status of appointment after unmarking by index: " + apptList.get(0).getStatus());

        Appointment.deleteApptByPatient(name, startTime,date);
        System.out.println("List after deleting: " + apptList);

        boolean isRunning = false;
        while (isRunning) {
            Scanner input = new Scanner(System.in);
        }*/
    }
}
