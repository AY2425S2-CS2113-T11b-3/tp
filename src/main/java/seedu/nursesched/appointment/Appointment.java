package seedu.nursesched.appointment;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

import seedu.nursesched.exception.ExceptionMessage;
import seedu.nursesched.exception.NurseSchedException;
import seedu.nursesched.patient.Patient;
import seedu.nursesched.storage.AppointmentStorage;
import seedu.nursesched.ui.Ui;

/**
 * Represents all appointments.
 * It stores details such as the start time, end time, date, patient name and notes.
 */
public class Appointment {
    protected static ArrayList<Appointment> apptList;
    private static final Logger logr = Logger.getLogger("Appointment");

    private final String name;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final LocalDate date;
    private final String notes;
    private final int ID;
    private final int importance;
    private boolean isDone = false;

    static {
        try {

            File logDir = new File("logs/appointment");
            if (!logDir.exists()) {
                logDir.mkdirs();  // Creates the directory and any missing parent directories
            }

            LogManager.getLogManager().reset();
            FileHandler fh = new FileHandler("logs/appointment/appointment.log", true);
            fh.setFormatter(new SimpleFormatter());
            logr.addHandler(fh);
            logr.setLevel(Level.ALL);
        } catch (IOException e) {
            logr.log(Level.SEVERE, "File logger not working", e);
        }

        apptList = AppointmentStorage.readFile();
    }



    /**
     * Constructs an Appointment object with specified details.
     *
     * @param ID        The ID of the patient involved in the appointment.
     * @param startTime The start time of the appointment.
     * @param endTime   The end time of the appointment.
     * @param date      The date on which the appointment occurs.
     * @param notes     The notes for the specified appointment.
     */

    public Appointment(int ID, LocalTime startTime, LocalTime endTime,
                       LocalDate date, String notes, int importance) {
        this.ID = ID;
        this.name = findPatientName(ID);
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.notes = notes;
        this.importance = importance;
        logr.info("Appointment object created");
    }

    /**
     * Adds a new appointment to the appointment list.
     *
     * @param ID The name of the patient involved in the appointment.
     * @param startTime The start time of the appointment.
     * @param endTime   The end time of the appointment.
     * @param date      The date of the appointment.
     * @param notes     The notes for the appointment.
     */
    public static void addAppt(int ID,
                               LocalTime startTime, LocalTime endTime,
                               LocalDate date, String notes, int importance) throws NurseSchedException {
        LocalDate today = LocalDate.now();
        LocalTime todayTime = LocalTime.now();

        assert importance <=3 && importance >= 1 : "Importance has to be between 0 and 3!";

        Appointment possibleClash = findApptClashes(startTime, endTime, date);
        if (possibleClash != null) {
            System.out.println("There is another patient, " + possibleClash.name +
                    " with an appointment clashing with the given time and date! ");
            System.out.println("Please enter a different date or time outside of " +
                    possibleClash.getDate() + ": " + possibleClash.startTime+ "-" + possibleClash.endTime);
            logr.info("Appointment already exists, appointment not added");
            return;
        }
        if (findPatientName(ID) == null) {
            throw new NurseSchedException(ExceptionMessage.INVALID_PATIENT_APPT_ADD);
        }

        checkApptDateTime(date, startTime, endTime);
        assert (date.isAfter(today) || (date.isEqual(today) && startTime.isAfter(todayTime))) :
                "Appointment date cannot be in the past!";
        assert startTime.isBefore(endTime) : "Appointment's start time cannot be after its end time!";

        Appointment appt = new Appointment(ID, startTime, endTime, date, notes, importance);
        apptList.add(appt);
        AppointmentStorage.appendToFile(appt);
        System.out.println("Appointment added:");
        System.out.println(appt);
        logr.info("Appointment added: " + appt);
    }

    /**
     * Deletes aan appointment from the appointment list based on the given index.
     *
     * @param index The index of the appointment to be removed (1-based index).
     */
    public static void deleteAppt(int index) {
        assert index >= 1 && index < apptList.size() : "Index must be between 1 and " + (apptList.size());
        try{
            Appointment appt = apptList.get(index);
            System.out.println("Appointment deleted: " + appt);
            apptList.remove(index);
            AppointmentStorage.overwriteSaveFile(apptList);
            logr.info("Appointment deleted" + appt);
        } catch (IndexOutOfBoundsException e) {  // Catching out-of-bounds exception instead of NullPointerException
            System.out.println("There is no appointment with index: " + (index + 1));
            logr.warning("There is no appointment with index: " + (index + 1));
        }
    }

    /**
     * Mark an appointment from the appointment list as done based on the given index.
     *
     * @param index The index of the appointment to be marked (1-based index).
     */
    public static void markAppt(int index) throws NurseSchedException {
        assert index >= 0 && index < apptList.size() : "Index must be between 1 and " + (apptList.size() - 1);
        try{
            if (apptList.get(index).getStatus()){
                throw new NurseSchedException(ExceptionMessage.MARKING_MARKED_APPT);
            }
            apptList.get(index).setDone(true);
            AppointmentStorage.overwriteSaveFile(apptList);
            System.out.println("Marked appointment as done!");
            logr.info("Appointment marked: " + apptList.get(index).toString());
        }catch (IndexOutOfBoundsException e) {
            System.out.println("There is no appointment with index: " + (index + 1));
            logr.warning("There is no appointment with index: " + (index + 1));
        }
    }


    /**
     * Unmark an appointment from the appointment list based on the given index.
     *
     * @param index The index of the appointment to be unmarked (1-based index).
     */
    public static void unmarkAppt(int index) throws NurseSchedException {
        assert index>0 && index < apptList.size() : "Index must be between 1 and " + (apptList.size() - 1);
        try{
            if (!apptList.get(index).getStatus()){
                throw new NurseSchedException(ExceptionMessage.UNMARKING_UNMARKED_APPT);
            }
            apptList.get(index).setDone(false);
            AppointmentStorage.overwriteSaveFile(apptList);
            System.out.println("Marked appointment as undone!");
            logr.info("Appointment unmarked: " + apptList.get(index).toString());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("There is no appointment with index: " + (index+1));
            logr.warning("There is no appointment with index: " + (index+1));
        }
    }

    /**
     * Finds and returns an appointment from the appointment list that could clash
     * with the given start time, end time and date.
     * @param startTime The start time of the appointment.
     * @param date      The date of the appointment.
     * @return          The matching appointment if found, otherwise return null.
     */
    public static Appointment findApptClashes(LocalTime startTime, LocalTime endTime, LocalDate date) {
        for (Appointment appointment : apptList) {
            if (appointment.date.equals(date) &&
                    (appointment.startTime.isBefore(endTime) && appointment.endTime.isAfter(startTime))) {
                return appointment;
            }
        }
        return null;
    }

    public static String findPatientName(int ID){
        ArrayList<Patient> pfList = Patient.getPatientsList();
        for (Patient p : pfList) {
            if (Integer.parseInt(p.getId()) == ID){
                return p.getName();
            }
        }
        return null;
    }

    /**
     * Filter for appointments by patient names.
     * @param patientName The keyword to search for in patient name.
     */
    public static void findApptByName(String patientName) {
        ArrayList<Appointment> searchResults = new ArrayList<>();
        for (Appointment appointment : apptList) {
            if (appointment.getName().toLowerCase().contains(patientName.toLowerCase())) {
                searchResults.add(appointment);
            }
        }
        Ui.printSearchResults(searchResults, patientName);
    }

    public static void findApptByID(String ID) {
        ArrayList<Appointment> searchResults = new ArrayList<>();
        for (Appointment appointment : apptList) {
            if (appointment.getID() == Integer.parseInt(ID)) {
                searchResults.add(appointment);
            }
        }
        Ui.printSearchResults(searchResults, ID);
    }

    public static void editAppt(int index, int ID,
                                       LocalTime startTime, LocalTime endTime,
                                       LocalDate date, String notes, int importance) throws NurseSchedException {
        if (index < 0 || index >= apptList.size()) {
            throw new NurseSchedException(ExceptionMessage.INVALID_APPT_NUMBER);
        }
        assert index >= 0 && index < apptList.size() : "Index must be valid and within bounds!";
        try {
            Appointment prevAppt = apptList.get(index);

            // If optional fields are empty, keep previous fields
            if (ID == -1){
                ID = prevAppt.ID;
            }
            if (endTime == null) {
                endTime = prevAppt.endTime;
            }
            if (date == null) {
                date = prevAppt.date;
            }
            if (startTime == null) {
                startTime = prevAppt.startTime;
            }
            if (notes == null) {
                notes = prevAppt.notes;
            }
            if (importance == -1) {
                importance = prevAppt.importance;
            }

            if (findPatientName(ID) == null) {
                throw new NurseSchedException(ExceptionMessage.INVALID_PATIENT_APPT_ADD);
            }

            checkApptDateTime(date, startTime, endTime);

            // Temporarily remove the current appointment from the list
            Appointment removedAppt = apptList.remove(index);

            // Check for clashes with the temporary state of the list (without the current appointment)
            Appointment possibleClash = findApptClashes(startTime, endTime, date);

            // Put the removed appointment back
            apptList.add(index, removedAppt);

            if (possibleClash != null && possibleClash != prevAppt) {
                System.out.println("There is another patient, " + possibleClash.name +
                        " with an appointment clashing with the given time and date! " +
                        "Please enter a different date or time outside of " +
                        possibleClash.getDate() + ": " + possibleClash.startTime+ "-" + possibleClash.endTime);
                logr.info("Appointment already exists, appointment not added");
                return;
            }


            Appointment updatedAppt = new Appointment(ID, startTime, endTime, date, notes, importance);
            apptList.set(index, updatedAppt);

            System.out.println("Appointment updated:");
            System.out.println(updatedAppt);
            logr.info("Appointment edited at index " + index + ": " + updatedAppt);

            AppointmentStorage.overwriteSaveFile(apptList);

        } catch (IndexOutOfBoundsException e) {
            System.out.println("There is no appointment with index: " + (index + 1));
            logr.warning("Edit failed. Invalid index: " + (index + 1));
        }
    }

    /**
     * Sorts the appointment list by importance level (HIGH to LOW).
     * For appointments with the same importance, they are sorted chronologically.
     * This method updates the apptList and saves the sorted list to the storage.
     */
    public static void sortByImportance() throws NurseSchedException {
        if (apptList.isEmpty()){
            logr.warning("Appointment list is empty. Nothing to sort.");
            throw new NurseSchedException(ExceptionMessage.INVALID_SORTING_LIST);
        }

        apptList.sort(Comparator.comparing(Appointment::getImportance).reversed() // Sort by importance (HIGH to LOW)
                .thenComparing(a -> a.date)                                      // Then by date
                .thenComparing(a -> a.startTime));                               // Then by start time

        AppointmentStorage.overwriteSaveFile(apptList);
        System.out.println("Appointments sorted by importance level (HIGH to LOW).");
        logr.info("Appointment list sorted by importance level");
    }

    /**
     * Sorts the appointment list in chronological order, first by date and then by start time.
     * This method updates the apptList and saves the sorted list to the storage.
     */
    public static void sortByTime() throws NurseSchedException {

        if (apptList.isEmpty()){
            String message = "Appointment list is empty. Nothing to sort.";
            System.out.println(message);
            logr.warning(message);
            throw new NurseSchedException(ExceptionMessage.INVALID_SORTING_LIST);
        }

        apptList.sort(Comparator.comparing((Appointment a) -> a.date) // First sort by dates
                .thenComparing(a -> a.startTime));  //Then sort by time

        AppointmentStorage.overwriteSaveFile(apptList);
        System.out.println("Appointments sorted chronologically.");
        logr.info("Appointment list sorted chronologically");
    }

    public static boolean isInPatientList(String name){
        ArrayList<Patient> pfList = Patient.getPatientsList();
        for (Patient p : pfList) {
            if (p.getName().toLowerCase().equals(name.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    public static void checkApptDateTime(LocalDate date, LocalTime startTime, LocalTime endTime)
            throws NurseSchedException {

        LocalTime todayTime = LocalTime.now();
        LocalDate today = LocalDate.now();

        if (date.isBefore(today) || (date.isEqual(today) &&
                (startTime.isBefore(todayTime) || endTime.isBefore(todayTime)))) {
            logr.warning("Command failed. Date time " + date + " " + startTime + " " + endTime
                    + " has already passed");
            throw new NurseSchedException(ExceptionMessage.INVALID_APPT_DATE_TIME);
        }

        if (startTime.isAfter(endTime)) {
            logr.warning("Command failed. Start time (" + startTime + ") is after end time (" + endTime + ")");
            throw new NurseSchedException(ExceptionMessage.INVALID_START_TIME);
        }

    }

    public static void removeAppointmentsForPatient(int patientId) {
        ArrayList<Appointment> appointmentsToRemove = new ArrayList<>();

        for (Appointment appointment : apptList) {
            if (appointment.getID() == patientId) {
                appointmentsToRemove.add(appointment);
            }
        }
        apptList.removeAll(appointmentsToRemove);
    }

    /**
     * Displays all appointment currently stored in the appointment list.
     * If no appointments are in the list, it notifies the user.
     */
    public static void list(){
        Ui.printAppointmentList(apptList);
    }

    public void setDone(boolean done) {
        this.isDone = done;
    }

    public boolean getStatus() {
        return this.isDone;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedStartTime = startTime.format(formatter);
        String formattedEndTime = endTime.format(formatter);

        String importanceString = switch (importance) {
        case 1 -> "LOW";
        case 2 -> "MEDIUM";
        case 3 -> "HIGH";
        default -> "";
        };

        return "ID: " + ID + ", " +
                "Name: " + name + ", " +
                "From: " + formattedStartTime + ", " +
                "To: " + formattedEndTime + ", " +
                "Date: " + date + ", " +
                "Importance: " + importanceString + ", " +
                "Notes: " + notes;
    }

    public String getName() {
        return name;
    }

    public int getImportance() {
        return importance;
    }

    public int getID() {
        return ID;
    }

    public String getStartTime() {
        return this.startTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getEndTime() {
        return this.endTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getDate() {
        return this.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String getNotes() {
        return notes;
    }
}
