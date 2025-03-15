import java.util.Scanner;
import java.util.ArrayList;
import appointment.Appointment;
import parser.ApptParser;
import parser.Parser;
import parser.PatientParser;
import patient.Patient;

import java.time.LocalDate;
import java.time.LocalTime;

public class NurseSched {
    /**
     * Main entry-point for the NurseSched application.
     */
    public static void main(String[] args) {

        ArrayList<Appointment> apptList = new ArrayList<>();

        String input = null;

        //test Appointment class methods
        String name = "James Gosling";
        String notes = "Test notes";
        LocalTime startTime = LocalTime.now();
        LocalDate date = LocalDate.now();

        Scanner in = new Scanner(System.in);

        greetingMessage();

        while(true) {
            String line = in.nextLine();
            String type = Parser.extractType(line);
            switch(type) {
            case "appt":;
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
                PatientParser patientParser = PatientParser.extractInputs(line);
                if (patientParser == null) {
                    System.out.println("Invalid inputs for Patient based command!");
                    break;
                }
                input = patientParser.getCommand();
                if (input.equals("add")) {
                    Patient newPatient = new Patient(patientParser.getName(), patientParser.getAge(),
                            patientParser.getNotes());
                    Patient.addPatient(newPatient);
                }
                if (input.equals("del")) {
                    Patient.removePatient(patientParser.getIndex());
                }
                if (input.equals("list")) {
                    Patient.printPatientInformation();
                }
                break;
            case "shift":
                //Todo
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

    public static void greetingMessage() {
        System.out.println("Welcome to Nurse Sched!");
        System.out.println("Please enter your command: ");
    }
}
