package seedu.nursesched.storage;

import seedu.nursesched.appointment.Appointment;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Provides persistent storage operations for lists.
 * <p>
 * This class handles reading appointments from a local save file, writing appointments to a file,
 * and formatting appointment data for storage.
 * </p>
 */
public class AppointmentStorage {
    private static final String FILE_PATH = "data/Appt.txt";

    /**
     * Reads all appointments from the appointment save file.
     * <p>
     * If the save file doesn't exist, it creates the necessary directories and returns an empty ArrayList.
     * </p>
     *
     * @return An ArrayList containing all appointments read from the storage file.
     */
    public static ArrayList<Appointment> readFile() {
        File taskFile = new File(FILE_PATH); // create a File for the given file path
        ArrayList<Appointment> apptList = new ArrayList<Appointment>();

        if (!taskFile.exists()) {
            taskFile.getParentFile().mkdirs();
            return apptList;
        }

        try (Scanner fileScanner = new Scanner(taskFile)) {
            while (fileScanner.hasNext()) {
                String currentLine = fileScanner.nextLine();
                Appointment appointment = getDetails(currentLine);

                apptList.add(appointment);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found at: " + FILE_PATH);
        }
        return apptList;
    }


    /**
     * Extracts appointment details from a string from the save file.
     * <p>
     * Extracts status, patient name, start time, end time, date and notes.
     * </p>
     *
     * @param currentLine The formatted string containing appointment information.
     * @return new Appointment object created with parsed information.
     */
    private static Appointment getDetails(String currentLine) {
        String[] parts = currentLine.split(" \\| ");

        String status = parts[0];
        String patientName = parts[1];
        String startTimeString = parts[2];
        String endTimeString = parts[3];
        String dateString = parts[4];
        String notes = parts[5];
        LocalTime startTime = LocalTime.parse(startTimeString);
        LocalTime endTime = LocalTime.parse(endTimeString);
        LocalDate date = LocalDate.parse(dateString);
        Appointment appointment = new Appointment(patientName, startTime, endTime, date, notes);
        if (status.equals("true")) {
            appointment.setDone(true);
        }
        return appointment;
    }

    /**
     * Formats an Appointment object into a string for storage.
     * <p>
     * String format:
     * [Status] | [Patient Name] | [Start Time] | [End Time] | [Date] | [Notes]
     * </p>
     *
     * @param appointment The appointment object to format.
     * @return Formatted string for save file.
     */
    public static String formatString(Appointment appointment) {
        String patientName = appointment.getName();
        boolean apptStatus = appointment.getStatus();
        String apptStartTime = appointment.getStartTime();
        String apptEndTime = appointment.getEndTime();
        String apptDate = appointment.getDate();
        String apptNotes = appointment.getNotes();


        return apptStatus + " | " + patientName + " | " + apptStartTime
                + " | " + apptEndTime + " | " + apptDate + " | " + apptNotes;
    }


    /**
     * Overwrites the storage file with the current ArrayList of tasks.
     *
     * @param apptList The ArrayList of Task objects to replace the save file with.
     */
    public static void overwriteSaveFile(ArrayList<Appointment> apptList) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            for (Appointment appt : apptList) {
                writer.write(formatString(appt) + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Appends a single appointment to the storage file.
     * <p>
     * Does not modify existing content.
     * </p>
     *
     * @param appt Appointment object to append to the storage file.
     */
    public static void appendToFile(Appointment appt) {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) { // create a FileWriter in append mode
            writer.write(formatString(appt) + "\n");
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

}

