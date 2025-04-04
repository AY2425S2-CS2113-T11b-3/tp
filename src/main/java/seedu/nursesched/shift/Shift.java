package seedu.nursesched.shift;

import seedu.nursesched.exception.ExceptionMessage;
import seedu.nursesched.exception.NurseSchedException;
import seedu.nursesched.storage.ShiftStorage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.logging.FileHandler;
import java.util.logging.Level;
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
    private boolean isDone = false;

    static {
        try {
            shiftList = ShiftStorage.readFile();
        } catch (Exception e) {
            shiftList = new ArrayList<>();
            System.out.println("Failed to load shifts. Starting with empty list.");
            logr.warning("ShiftStorage.readFile failed: " + e.getMessage());
        }

        try {
            File logDir = new File("logs/shift");
            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            FileHandler fh = new FileHandler("logs/shift/shift.log", true);
            fh.setFormatter(new SimpleFormatter());
            logr.addHandler(fh);
            logr.setLevel(Level.ALL);
        } catch (IOException e) {
            System.out.println("Logger setup failed: " + e.getMessage());
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
        ShiftStorage.overwriteSaveFile(shiftList);
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
        ShiftStorage.overwriteSaveFile(shiftList);
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
     * Marks a shift from the shift list as done based on the given index.
     *
     * @param index The index of the shift to be marked as done (0-based index).
     */
    public static void markShift(int index) {
        assert index >= 0 && index < shiftList.size() : "Index must be valid and within bounds!";
        try {
            shiftList.get(index).setDone(true);
            System.out.println("Marked shift as done!");
            System.out.println(shiftList.get(index));
            logr.info("Shift marked: " + shiftList.get(index).toString());
            ShiftStorage.overwriteSaveFile(shiftList);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("There is no shift with index: " + (index + 1));
            logr.warning("There is no shift with index: " + (index + 1));
        }
    }

    /**
     * Unmarks a shift from the shift list (sets it as not done) based on the given index.
     *
     * @param index The index of the shift to be unmarked (0-based index).
     */
    public static void unmarkShift(int index) {
        assert index >= 0 && index < shiftList.size() : "Index must be valid and within bounds!";
        try {
            shiftList.get(index).setDone(false);
            System.out.println("Marked shift as undone!");
            logr.info("Shift unmarked: " + shiftList.get(index).toString());
            ShiftStorage.overwriteSaveFile(shiftList);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("There is no shift with index: " + (index + 1));
            logr.warning("There is no shift with index: " + (index + 1));
        }
    }

    /**
     * Edits the details of an existing shift based on the given index.
     *
     * @param index        The index of the shift to be edited (0-based index).
     * @param newStartTime The new start time for the shift.
     * @param newEndTime   The new end time for the shift.
     * @param newDate      The new date for the shift.
     * @param newTask      The new task description for the shift.
     */
    public static void editShift(int index, LocalTime newStartTime, LocalTime newEndTime,
                                 LocalDate newDate, String newTask) throws NurseSchedException {
        assert index >= 0 && index < shiftList.size() : "Invalid shift index!";
        assert newStartTime != null
                && newEndTime != null
                && newDate != null
                && newTask != null : "New shift details cannot be null!";
        assert newStartTime.isBefore(newEndTime) : "Start time must be before end time";

        if (index < 0 || index >= shiftList.size()) {
            logr.warning("Attempted to edit shift with invalid index: " + index);
            throw new NurseSchedException(ExceptionMessage.INVALID_SHIFT_NUMBER);
        }

        Shift shift = shiftList.get(index);
        Shift updatedShift = new Shift(newStartTime, newEndTime, newDate, newTask);
        updatedShift.setDone(shift.getStatus());
        shiftList.set(index, updatedShift);
        ShiftStorage.overwriteSaveFile(shiftList);
        System.out.println("Shift updated:");
        System.out.println(updatedShift);
        logr.info("Shift updated at index " + index + ": " + updatedShift);
    }

    public void setDone(boolean done) {
        this.isDone = done;
    }

    public boolean getStatus() {
        return this.isDone;
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
        String markStatus = isDone ? "[X]" : "[ ]";

        return markStatus + " From: " + formattedStartTime + ", " +
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

    public static ArrayList<Shift> getShiftList() {
        return shiftList;
    }
}
