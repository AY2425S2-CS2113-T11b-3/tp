package seedu.nursesched.shift;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Represents a work shift assigned to a nurse.
 * It stores details such as the start time, end time, date, and the assigned task.
 */
public class Shift {
    protected static ArrayList<Shift> shiftList = new ArrayList<>();
    private static final Logger logr = Logger.getLogger("Shift");

    private final LocalTime startTime;
    private final LocalTime endTime;
    private final LocalDate date;
    private final String shiftTask;

    static {
        try {
            LogManager.getLogManager().reset();
            FileHandler fh = new FileHandler("logs/shift/shift.log", true);
            fh.setFormatter(new SimpleFormatter());
            logr.addHandler(fh);
            logr.setLevel(Level.ALL);
        } catch (IOException e) {
            logr.log(Level.SEVERE, "File logger not working", e);
        }
    }

    /**
     * Constructs a Shift object with specified details.
     *
     * @param startTime The start time of the shift.
     * @param endTime   The end time of the shift.
     * @param date      The date on which the shift occurs.
     * @param shiftTask The task assigned during the shift.
     */
    public Shift(LocalTime startTime, LocalTime endTime, LocalDate date, String shiftTask) {
        assert startTime != null : "Start time cannot be null";
        assert endTime != null : "End time cannot be null";
        assert date != null : "Date cannot be null";
        assert shiftTask != null && !shiftTask.isEmpty() : "Shift task cannot be null or empty";
        assert startTime.isBefore(endTime) : "Start time must be before end time";

        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.shiftTask = shiftTask;
        logr.info("Shift created: " + this);
    }

    /**
     * Adds a new shift to the shift list.
     *
     * @param startTime The start time of the shift.
     * @param endTime   The end time of the shift.
     * @param date      The date of the shift.
     * @param shiftTask The task assigned during the shift.
     */
    public static void addShift(LocalTime startTime, LocalTime endTime, LocalDate date,
                                String shiftTask) {
        assert startTime != null && endTime != null && date != null && shiftTask != null : "Invalid shift details";
        Shift shift = new Shift(startTime, endTime, date, shiftTask);
        shiftList.add(shift);
        logr.info("Shift added: " + shift);
        System.out.println("Shift added");
    }

    /**
     * Deletes a shift from the shift list based on the given index.
     *
     * @param index The index of the shift to be removed (0-based index).
     */
    public static void deleteShiftByIndex(int index) {
        assert index >= 0 : "Shift index cannot be negative";
        if (index < 0 || index >= shiftList.size()) {
            logr.warning("Attempted to delete shift with invalid index: " + index);
            System.out.println("Invalid shift index.");
            return;
        }
        Shift removedShift = shiftList.remove(index);
        logr.info("Shift deleted: " + removedShift);
        System.out.println("Shift deleted.");
    }

    /**
     * Displays all shifts currently stored in the shift list.
     * If no shifts are available, it notifies the user.
     */
    public static void listShifts() {
        if (shiftList.isEmpty()) {
            System.out.println("No shifts available.");
            return;
        }

        System.out.println("List of all shifts:");
        for (int i = 0; i < shiftList.size(); i++) {
            Shift shift = shiftList.get(i);
            System.out.printf("%d. %s %n", i + 1, shift);
        }
    }

    /**
     * Returns a formatted string representation of the shift details.
     *
     * @return A string describing the shift including start and end times, date, and task.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedStartTime = startTime.format(formatter);
        String formattedEndTime = endTime.format(formatter);

        return "From: " + formattedStartTime + ", " +
                "To: " + formattedEndTime + ", " +
                "Date: " + date + ", " +
                "shiftTask: " + shiftTask;
    }

    /**
     * Retrieves the start time of the shift.
     *
     * @return The start time as a {@link LocalTime} object.
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Retrieves the end time of the shift.
     *
     * @return The end time as a {@link LocalTime} object.
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Retrieves the date of the shift.
     *
     * @return The date as a {@link LocalDate} object.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Retrieves the task assigned to the shift.
     *
     * @return The task description as a {@link String}.
     */
    public String getShiftTask() {
        return shiftTask;
    }
}
