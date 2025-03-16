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
    private final int shiftIndex;

    public ShiftParser(String command, String nurseName, LocalTime startTime, LocalTime endTime,
                       LocalDate date, String shiftTask, int shiftIndex) {
        this.command = command;
        this.nurseName = nurseName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.shiftTask = shiftTask;
        this.shiftIndex = shiftIndex;
    }

    public static ShiftParser extractInputs(String line) {
        line = line.trim().toLowerCase();

        String[] parts = line.split(" ", 2);
        if (parts.length < 2) {
            System.out.println("Invalid command format!");
            return null;
        }

        String remaining = parts[1];

        String command = "";
        String nurseName = "";
        int shiftIndex = -1;
        LocalTime startTime = null;
        LocalTime endTime = null;
        LocalDate date = null;
        String shiftTask = "";

        try {
            // Extract actual command ("add" or "del")
            String[] commandParts = remaining.split(" ", 2);
            command = commandParts[0];
            remaining = (commandParts.length > 1) ? commandParts[1] : "";

            if (command.equals("add")) {
                if (!remaining.contains("nn/") || !remaining.contains("s/") || !remaining.contains("e/") ||
                        !remaining.contains("d/") || !remaining.contains("st/")) {
                    System.out.println("Invalid add shift format!");
                    return null;
                }

                // Extract values based on markers
                nurseName = extractValue(remaining, "nn/", "s/");
                startTime = LocalTime.parse(extractValue(remaining, "s/", "e/"));
                endTime = LocalTime.parse(extractValue(remaining, "e/", "d/"));
                date = LocalDate.parse(extractValue(remaining, "d/", "st/"));
                shiftTask = extractValue(remaining, "st/", null);

                return new ShiftParser(command, nurseName, startTime, endTime, date, shiftTask, shiftIndex);
            } else if (command.equals("del")) {
                if (!remaining.contains("sn/")) {
                    System.out.println("Invalid delete shift format!");
                    return null;
                }
                shiftIndex = Integer.parseInt(extractValue(remaining, "sn/", null));
                return new ShiftParser(command, nurseName, startTime, endTime, date, shiftTask, shiftIndex-1);
            } else {
                System.out.println("Invalid command type!");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error parsing shift command: " + e.getMessage());
            return null;
        }
    }

    private static String extractValue(String input, String startMarker, String endMarker) {
        int start = input.indexOf(startMarker);
        if (start == -1) return "";

        start += startMarker.length();
        int end = (endMarker != null) ? input.indexOf(endMarker, start) : -1;

        return (end == -1) ? input.substring(start).trim() : input.substring(start, end).trim();
    }


    // Getters
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

    public int getIndex() {
        return shiftIndex;
    }
}
