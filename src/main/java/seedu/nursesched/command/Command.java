package seedu.nursesched.command;

import seedu.nursesched.appointment.Appointment;
import seedu.nursesched.exception.NurseSchedException;
import seedu.nursesched.medicine.Medicine;
import seedu.nursesched.parser.ApptParser;
import seedu.nursesched.parser.MedicineParser;
import seedu.nursesched.parser.PatientParser;
import seedu.nursesched.parser.ShiftParser;
import seedu.nursesched.parser.TaskParser;
import seedu.nursesched.parser.Parser;
import seedu.nursesched.patient.Patient;
import seedu.nursesched.shift.Shift;
import seedu.nursesched.task.Task;
import seedu.nursesched.ui.Ui;

import java.util.Scanner;

public class Command {
    /**
     * Performs the execution of commands after the input has been parsed.
     *
     * @param isExit A boolean which is true if the exit command is being executed.
     * @param in     The input command to be parsed.
     * @param ui     The user interface class for interacting with the user.
     */
    public static void executeCommands(boolean isExit, Scanner in, Ui ui) {
        String input;
        while (!isExit) {
            try {
                String line = ui.readCommand(in);
                ui.showResults();
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
                                apptParser.getID(),
                                apptParser.getStartTime(),
                                apptParser.getEndTime(),
                                apptParser.getDate(),
                                apptParser.getNotes(),
                                apptParser.getImportance()
                        );
                        break;
                    case "del":

                        Appointment.deleteAppt(
                                apptParser.getIndex()
                        );
                        break;
                    case "mark":
                        Appointment.markAppt(
                                apptParser.getIndex()
                        );
                        break;
                    case "unmark":
                        Appointment.unmarkAppt(
                                apptParser.getIndex()
                        );
                        break;
                    case "list":
                        Appointment.list();
                        break;
                    case "sort":
                        if (apptParser.getSortBy().equals("importance")) {
                            Appointment.sortByImportance();
                        } else {
                            Appointment.sortByTime();
                        }

                        break;
                    case "find":
                        String searchBy = apptParser.getSearchBy();
                        if (searchBy.equals("id")){
                            Appointment.findApptByID(apptParser.getSearchKeyword());
                        }

                        if (searchBy.equals("p")){
                            Appointment.findApptByName(apptParser.getSearchKeyword());
                        }


                        break;
                    case "edit":
                        Appointment.editAppt(
                                apptParser.getIndex(),
                                apptParser.getID(),
                                apptParser.getStartTime(),
                                apptParser.getEndTime(),
                                apptParser.getDate(),
                                apptParser.getNotes(),
                                apptParser.getImportance()
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
                        Patient newPatient = new Patient(
                                patientParser.getId(),
                                patientParser.getName(),
                                patientParser.getAge(),
                                patientParser.getGender(),
                                patientParser.getContact(),
                                patientParser.getNotes());
                        Patient.addPatient(newPatient);
                        break;
                    }
                    if (input.equals("del")) {
                        Patient.removePatient(patientParser.getId());
                        break;
                    }
                    if (input.equals("list")) {
                        Patient.listPatientInformation();
                        break;
                    }
                    if (input.equals("find")) {
                        Patient.printProfileWithID(patientParser.getId());
                        break;
                    }
                    if (input.equals("edit")) {
                        Patient.editPatientDetails(
                                patientParser.getId(),
                                patientParser.getName(),
                                patientParser.getAge(),
                                patientParser.getGender(),
                                patientParser.getContact(),
                                patientParser.getNotes());
                        break;
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
                        Shift.listShifts();
                    }
                    if (shift.equals("del")) {
                        Shift.deleteShiftByIndex(
                                shiftParser.getIndex()
                        );
                        Shift.listShifts();
                    }
                    if (shift.equals("edit")) {
                        Shift.editShift(
                                shiftParser.getIndex(),
                                shiftParser.getStartTime(),
                                shiftParser.getEndTime(),
                                shiftParser.getDate(),
                                shiftParser.getNotes()
                        );
                    }
                    if (shift.equals("mark")) {
                        Shift.markShift(
                                shiftParser.getIndex()
                        );
                    }
                    if (shift.equals("unmark")) {
                        Shift.unmarkShift(
                                shiftParser.getIndex()
                        );
                    }
                    if (shift.equals("sort")) {
                        Shift.sortShiftsChronologically();
                        Shift.listShifts();
                    }
                    if (shift.equals("logot")) {
                        double hours = Double.parseDouble(shiftParser.getShiftTask());
                        int index = shiftParser.getIndex();
                        Shift.logOvertime(index, hours);
                    }
                    if (shift.equals("list")) {
                        Shift.listShifts();
                    }
                    break;
                case "task":
                    TaskParser taskParser = TaskParser.extractInputs(line);
                    if (taskParser == null) {
                        break;
                    }
                    String taskCommand = taskParser.getCommand();
                    switch (taskCommand) {
                    case "add":
                        Task.addTask(
                                taskParser.getDescription(),
                                taskParser.getByDate(),
                                taskParser.getByTime(),
                                taskParser.isDone()
                        );
                        break;
                    case "del":
                        Task.deleteTask(taskParser.getTaskIndex());
                        break;
                    case "mark":
                        Task.markTask(taskParser.getTaskIndex());
                        break;
                    case "unmark":
                        Task.unmarkTask(taskParser.getTaskIndex());
                        break;
                    case "list":
                        Task.listTasks();
                        break;
                    case "edit":
                        Task.editTask(
                                taskParser.getTaskIndex(),
                                taskParser.getDescription(),
                                taskParser.getByDate(),
                                taskParser.getByTime());
                        break;
                    case "find":
                        Task.findTask(taskParser.getDescription());
                        break;
                    default:
                        System.out.println("Invalid task based command!");
                        break;
                    }
                    break;

                case "medicine":
                    MedicineParser medicineParser = MedicineParser.extractInputs(line);
                    if (medicineParser == null) {
                        break;
                    }
                    String medicineCommand = medicineParser.getCommand();
                    switch (medicineCommand) {
                    case "add":
                        Medicine.addMedicine(
                                medicineParser.getQuantity(),
                                medicineParser.getMedicineName()
                        );
                        break;
                    case "remove":
                        Medicine.removeMedicine(
                                medicineParser.getQuantity(),
                                medicineParser.getMedicineName()
                        );
                        break;
                    case "list":
                        Medicine.listMedicine();
                        break;
                    case "find":
                        Medicine.findMedicine(
                                medicineParser.getMedicineName()
                        );
                        break;
                    case "delete":
                        Medicine.deleteMedicine(
                                medicineParser.getMedicineName()
                        );
                        break;
                    case "edit":
                        Medicine.editMedicine(
                                medicineParser.getMedicineName(),
                                medicineParser.getUpdatedName(),
                                medicineParser.getQuantity()
                        );
                        break;
                    case "restock":
                        Medicine.restockMedicine(
                                medicineParser.getQuantity()
                        );
                        break;

                    default:
                        System.out.println("Invalid medicine based command!");
                        break;
                    }
                    break;

                // Exit command "exit ns"
                case "exit":
                    String exitCommand = line.trim().toLowerCase();
                    if (exitCommand.equals("exit ns")) {
                        in.close();
                        ui.exitMessage();
                        isExit = true;
                    } else {
                        System.out.println("Unknown command!");
                        System.out.println("Command should start with \"appt\", \"pf\", \"shift\", \"task\", " +
                                "\"medicine\" or \"exit ns\"");
                    }
                    break;


                default:
                    System.out.println("Unknown command!");
                    System.out.println("Command should start with \"appt\", \"pf\", \"shift\", \"task\", \"medicine\" "
                            + "or \"exit ns\"");
                    break;
                }
            } catch (NurseSchedException e) {
                ui.showError(e.getMessage());
            }
        }
    }
}
