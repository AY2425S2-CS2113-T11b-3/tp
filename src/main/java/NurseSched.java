import java.util.Scanner;

import appointment.Appointment;
import parser.ShiftParser;
import parser.ApptParser;
import parser.Parser;
import parser.PatientParser;
import patient.Patient;
import shift.Shift;

public class NurseSched {
    /**
     * Main entry-point for the NurseSched application.
     */
    public static void main(String[] args) {
        String input = null;

        Scanner in = new Scanner(System.in);

        greetingMessage();

<<<<<<< Updated upstream
        while(true) {
            String line = in.nextLine();
            String type = Parser.extractType(line);
            switch (type) {
            case "appt":
                ApptParser apptParser = ApptParser.extractInputs(line);
                if (apptParser == null) {
=======
        while (!isExit) {
            try {
                String line = ui.readCommand(in);
                String type = Parser.extractType(line);
                switch (type) {
                case "appt":
                    ApptParser apptParser = ApptParser.extractInputs(line);
                    if (apptParser == null) {
                        break;
                    }
                    String command = apptParser.getCommand();
                    switch (command) {
                    case "add":
                        Appointment.addAppt(
                                apptParser.getName(),
                                apptParser.getStartTime(),
                                apptParser.getEndTime(),
                                apptParser.getDate(),
                                apptParser.getNotes()
                        );
                        break;
                    case "del":
                        Appointment.deleteApptByIndex(
                                apptParser.getIndex()
                        );
                        break;
                    case "mark":
                        Appointment.markApptByIndex(
                                apptParser.getIndex()
                        );
                        break;
                    case "unmark":
                        Appointment.unmarkApptByIndex(
                                apptParser.getIndex()
                        );
                        break;
                    case "list":
                        Appointment.list();
                        break;
                    default:
                        System.out.println("Invalid appointment based command!");
                        break;
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
                    ShiftParser shiftParser = ShiftParser.extractInputs(line);
                    String shift = shiftParser.getCommand();
                    if (shift.equals("add")) {
                        Shift.addShift(
                                shiftParser.getStartTime(),
                                shiftParser.getEndTime(),
                                shiftParser.getDate(),
                                shiftParser.getNotes()
                        );
                        System.out.println("Shift added");
                        Shift.listShifts();
                    }
                    if (shift.equals("del")) {
                        Shift.deleteShiftByIndex(
                                shiftParser.getIndex()
                        );
                        System.out.println("Shift deleted");
                        Shift.listShifts();
                    }
                    break;

                // Exit command "exit ns"
                case "exit":
                    in.close();
                    ui.exitMessage();
                    isExit = true;
                    break;

                default:
                    System.out.println("Unknown command!");
>>>>>>> Stashed changes
                    break;
                }
                String command = apptParser.getCommand();
                switch (command) {
                case "add":
                    Appointment.addAppt(
                            apptParser.getName(),
                            apptParser.getStartTime(),
                            apptParser.getEndTime(),
                            apptParser.getDate(),
                            apptParser.getNotes()
                    );
                    break;
                case "del":
                    Appointment.deleteApptByPatient(
                            apptParser.getName(),
                            apptParser.getStartTime(),
                            apptParser.getDate()
                    );
                    break;
                case "mark":
                    Appointment.markApptByPatient(
                            apptParser.getName(),
                            apptParser.getStartTime(),
                            apptParser.getDate()
                    );
                    break;
                default:
                    System.out.println("Invalid appointment based command!");
                    break;
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
            // Exit command "exit ns"
            case "exit":
                in.close();
                exitMessage();
                return;
            default:
                System.out.println("Unknown command!");
                break;
            }
        }
    }

    public static void greetingMessage() {
        System.out.println("Welcome to Nurse Sched!");
        System.out.println("Please enter your command: ");
    }

    public static void exitMessage() {
        System.out.println("Goodbye!");
    }
}
