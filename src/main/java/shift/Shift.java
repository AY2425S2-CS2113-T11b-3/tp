package shift;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;

public class Shift {
    protected static ArrayList<Shift> shiftList = new ArrayList<>();
    private final String nurseName;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final LocalDate date;
    private final String shiftTask;
    //    private boolean isDone = false;

    public Shift(String nurseName, LocalTime startTime, LocalTime endTime, LocalDate date, String shiftTask) {
        this.nurseName = nurseName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.shiftTask = shiftTask;
    }

    public static void addShift(String nurseName,
                                LocalTime startTime, LocalTime endTime, LocalDate date, String shiftTask) {

        //TODO: throw error if start/end/date is invalid
        shiftList.add(new Shift(nurseName, startTime, endTime, date, shiftTask));
    }

    public static void deleteShiftByIndex(int index) {

        //TODO: throw error if index is invalid
        shiftList.remove(index);
    }

    // check if shifts are successfully added (will delete after list is implemented)
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
