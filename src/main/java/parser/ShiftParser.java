package parser;

import java.time.LocalDate;
import java.time.LocalTime;

public class ShiftParser extends Parser {
    private final String command;
    private final String nurseName; // NURSE_NAME
    private final LocalTime startTime; // START_TIME
    private final LocalTime endTime; // END_TIME
    private final LocalDate date; // DATE
    private final String shiftTask; // SHIFT_TASK

    public ShiftParser(String command, String nurseName, LocalTime startTime, LocalTime endTime,
                       LocalDate date, String shiftTask) {
        this.command = command;
        this.nurseName = nurseName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.shiftTask = shiftTask;
    }

    // Extracts all input parameters for Appointment based command
    public static ShiftParser extractInputs(String line) {
        line = line.trim();
        line = line.toLowerCase();
        line = line.substring(line.indexOf(" ") + 1);
        String command = "";
        String nurseName = "";
        LocalTime startTime = null;
        LocalTime endTime = null;
        LocalDate date = null;
        String shiftTask = "";

        try {
            command = line.substring(0, line.indexOf(" "));
            line = line.substring(line.indexOf(" ") + 1);

            nurseName = line.substring(line.indexOf("nn/") + 3, line.indexOf("s/") - 1);
            line = line.substring(line.indexOf("s/"));

            startTime = LocalTime.parse(line.substring(2, line.indexOf(" ")));
            date = LocalDate.parse(line.substring(line.indexOf("d/") + 2, line.indexOf("d/") + 12));

            // line only stores input parameters from here on
            line = line.substring(line.indexOf(" ") + 1);
        } catch (Exception e) {
            System.out.println("Command: " + command);
            System.out.println("Line: " + line);
            System.out.println("Nurse's name: " + nurseName);
            System.out.println("Start: " + startTime);
            System.out.println("Date: " + date);
            System.out.println("Command or type not valid!");
            return null;
        }

        if (command.equals("add")) {
            try {
                endTime = LocalTime.parse(line.substring(line.indexOf("e/") + 2, line.indexOf("d/") - 1));
                shiftTask = line.substring(line.indexOf("st/") + 2);
            } catch (Exception e) {
                System.out.println("Invalid appointment time/notes format!");
                return null;
            }
            return new ShiftParser(command, nurseName, startTime, endTime, date, shiftTask);
        }
        return null;
    }

    //Getters
    public String getCommand() {
        return command;
    }

    public String getName() {
        return nurseName;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getNotes() {
        return shiftTask;
    }
}
