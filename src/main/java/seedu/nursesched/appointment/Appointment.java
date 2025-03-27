package seedu.nursesched.appointment;

import seedu.nursesched.exception.NurseSchedException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

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
     * @param name      The name of the patient involved in the appointment.
     * @param startTime The start time of the appointment.
     * @param endTime   The end time of the appointment.
     * @param date      The date on which the appointment occurs.
     * @param notes     The notes for the specified appointment.
     */

    public Appointment(String name, LocalTime startTime, LocalTime endTime, LocalDate date, String notes) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.notes = notes;
        logr.info("Appointment object created");
    }

    /**
     * Adds a new appointment to the appointment list.
     *
     * @param name The name of the patient involved in the appointment.
     * @param startTime The start time of the appointment.
     * @param endTime   The end time of the appointment.
     * @param date      The date of the appointment.
     * @param notes     The notes for the appointment.
     */
    public static void addAppt(String name,
                               LocalTime startTime, LocalTime endTime, LocalDate date, String notes) {
        LocalDate today = LocalDate.now();
        assert !name.isEmpty() : "Name should not be empty!";
        assert startTime.isBefore(endTime) : "Appointment's start time cannot be after its end time!";
        assert date.isEqual(today) || date.isAfter(today) : "Appointment date cannot be in the past!";

        Appointment possibleClash = findAppointment(startTime, date);
        if (possibleClash != null) {
            System.out.println("There is another patient, " + possibleClash.name +
                    " with the same appointment time and date! " +
                    "Please enter a different time/date");
            logr.info("Appointment already exists, appointment not added");
            return;
        }

        Appointment appt = new Appointment(name, startTime, endTime, date, notes);
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
    public static void deleteAppt(int index) throws NurseSchedException {
        assert index >= 1 && index < apptList.size() : "Index must be between 1 and " + (apptList.size() - 1);
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
     * @param index The index of the appointment to be removed (1-based index).
     */
    public static void markAppt(int index) throws NurseSchedException {
        assert index >= 0 && index < apptList.size() : "Index must be between 1 and " + (apptList.size() - 1);
        try{
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
     * @param index The index of the appointment to be removed (1-based index).
     */
    public static void unmarkAppt(int index) {
        assert index>0 && index < apptList.size() : "Index must be between 1 and " + (apptList.size() - 1);
        try{
            apptList.get(index).setDone(false);
            AppointmentStorage.overwriteSaveFile(apptList);
            System.out.println("Marked appointment as undone!");
            logr.info("Appointment unmarked: " + apptList.get(index).toString());
        }catch (IndexOutOfBoundsException e) {
            System.out.println("There is no appointment with index: " + (index+1));
            logr.warning("There is no appointment with index: " + (index+1));
        }
    }

    /**
     * Finds and returns an appointment from the appointment list that matches
     * the given patient name, start time, and date.
     * @param startTime The start time of the appointment.
     * @param date      The date of the appointment.
     * @return          The matching appointment if found, otherwise return null.
     */
    public static Appointment findAppointment(LocalTime startTime, LocalDate date) {
        for (Appointment appointment : apptList) {
            if (appointment.startTime.equals(startTime)
                    && appointment.date.equals(date)) {
                return appointment;
            }
        }
        return null;
    }

    /**
     * Filter for appointments by patient names.
     * @param patientName The keyword to search for in patient name.
     */
    public static void filterAppointment(String patientName) {
        ArrayList<Appointment> searchResults = new ArrayList<>();
        for (Appointment appointment : apptList) {
            if (appointment.getName().toLowerCase().contains(patientName.toLowerCase())) {
                searchResults.add(appointment);
            }
        }
        Ui.printSearchResults(searchResults, patientName);
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

        return "Name: " + name + ", " +
                "From: " + formattedStartTime + ", " +
                "To: " + formattedEndTime + ", " +
                "Date: " + date + ", " +
                "Notes: " + notes;
    }

    public String getName() {
        return name;
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
