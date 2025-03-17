import java.util.Scanner;

import ui.Ui;
import appointment.Appointment;
import exception.NurseSchedException;
import parser.ShiftParser;
import parser.ApptParser;
import parser.Parser;
import parser.PatientParser;
import patient.Patient;
import shift.Shift;

public class NurseSched {
    private final Ui ui;


    public NurseSched() {
        ui = new Ui();
    }

    public void run() {
        String input = null;
        boolean isExit = false;
        Scanner in = new Scanner(System.in);
        ui.greetingMessage();

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
                    break;
                }
            } catch (NurseSchedException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new NurseSched().run();
    }
}
