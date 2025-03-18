package command;

import appointment.Appointment;
import exception.NurseSchedException;
import parser.ApptParser;
import parser.Parser;
import parser.PatientParser;
import parser.ShiftParser;
import patient.Patient;
import shift.Shift;
import ui.Ui;

import java.util.Scanner;

public class Command {
    public static void executeCommands(boolean isExit, Scanner in, Ui ui) {
        String input;
        while (!isExit) {
            try {
                String line = ui.readCommand(in);
                String type = Parser.extractType(line);
                switch (type) {
                case "appt":
                    ApptParser apptParser = ApptParser.extractInputs(line);
                    if (apptParser == null) {
                        System.out.println("Invalid inputs for appointment based command!");
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
                    System.out.println("Command should start with \"appt\", \"pf\" or \"shift\"");
                    break;
                }
            } catch (NurseSchedException e) {
                ui.showError(e.getMessage());
            }
        }
    }
}
