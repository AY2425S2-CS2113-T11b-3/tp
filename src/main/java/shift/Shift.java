package shift;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Represents a shift assigned to a nurse.
 * Stores shift details including nurse name, start and end times, date, and task description.
 */
public class Shift {
    protected static ArrayList<Shift> shiftList = new ArrayList<>();
    private final String nurseName;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final LocalDate date;
    private final String shiftTask;

    /**
     * Constructs a Shift object.
     *
     * @param nurseName The name of the nurse assigned to the shift.
     * @param startTime The start time of the shift.
     * @param endTime   The end time of the shift.
     * @param date      The date of the shift.
     * @param shiftTask The task assigned during the shift.
     */
    public Shift(String nurseName, LocalTime startTime, LocalTime endTime, LocalDate date, String shiftTask) {
        this.nurseName = nurseName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.shiftTask = shiftTask;
    }

    /**
     * Adds a new shift to the shift list.
     *
     * @param nurseName The name of the nurse assigned to the shift.
     * @param startTime The start time of the shift.
     * @param endTime   The end time of the shift.
     * @param date      The date of the shift.
     * @param shiftTask The task assigned during the shift.
     */
    public static void addShift(String nurseName, LocalTime startTime, LocalTime endTime, LocalDate date, String shiftTask) {
        shiftList.add(new Shift(nurseName, startTime, endTime, date, shiftTask));
    }

    /**
     * Deletes a shift from the shift list by index.
     *
     * @param index The index of the shift to be removed.
     */
    public static void deleteShiftByIndex(int index) {
        if (index < 0 || index >= shiftList.size()) {
            System.out.println("Invalid shift index.");
            return;
        }
        shiftList.remove(index);
    }

    /**
     * Lists all shifts currently stored in the shift list.
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
     * Returns a formatted string representation of the shift.
     *
     * @return A string describing the shift details.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedStartTime = startTime.format(formatter);
        String formattedEndTime = endTime.format(formatter);

        return "nurseName: " + nurseName + ", " +
                "From: " + formattedStartTime + ", " +
                "To: " + formattedEndTime + ", " +
                "Date: " + date + ", " +
                "shiftTask: " + shiftTask;
    }
}
