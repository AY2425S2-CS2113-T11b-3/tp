package parser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * The {@code ShiftParser} class is responsible for parsing shift-related commands.
 * It extracts the necessary details to create or delete a shift.
 */
public class ShiftParser extends Parser {
    private final String command;
    private final String nurseName;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final LocalDate date;
    private final String shiftTask;
    private final int shiftIndex;

    /**
     * Constructs a {@code ShiftParser} object with extracted shift details.
     *
     * @param command    The command type (either "add" or "del").
     * @param nurseName  The name of the nurse assigned to the shift.
     * @param startTime  The start time of the shift.
     * @param endTime    The end time of the shift.
     * @param date       The date of the shift.
     * @param shiftTask  The task assigned during the shift.
     * @param shiftIndex The index of the shift (for deletion).
     */
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

    /**
     * Parses the input command and extracts shift-related details.
     *
     * @param line The input command string.
     * @return A {@code ShiftParser} object containing the extracted shift details, or {@code null} if parsing fails.
     */
    public static ShiftParser extractInputs(String line) {
        if (line == null || line.trim().isEmpty()) {
            System.out.println("Input line cannot be empty!");
            return null;
        }

        line = line.trim().toLowerCase();
        String[] parts = line.split(" ", 2);
        if (parts.length < 2) {
            System.out.println("Invalid command format!");
            return null;
        }

        String remaining = parts[1];

        // Variables for extracted values
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
                // Ensure all required markers exist
                if (!remaining.contains("nn/") || !remaining.contains("s/") || !remaining.contains("e/") ||
                        !remaining.contains("d/") || !remaining.contains("st/")) {
                    System.out.println("Invalid 'shift add' format!");
                    return null;
                }

                // Extract values and validate them
                nurseName = extractValue(remaining, "nn/", "s/");
                if (nurseName.isEmpty()) {
                    System.out.println("Nurse name cannot be empty.");
                    return null;
                }

                try {
                    startTime = LocalTime.parse(extractValue(remaining, "s/", "e/"));
                    endTime = LocalTime.parse(extractValue(remaining, "e/", "d/"));
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid time format! Use HH:mm (e.g., 10:00).");
                    return null;
                }

                try {
                    date = LocalDate.parse(extractValue(remaining, "d/", "st/"));
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format! Use YYYY-MM-DD.");
                    return null;
                }

                shiftTask = extractValue(remaining, "st/", null);
                if (shiftTask.isEmpty()) {
                    System.out.println("Shift task cannot be empty.");
                    return null;
                }

                // Validate start time before end time
                if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
                    System.out.println("Start time must be before end time.");
                    return null;
                }

                return new ShiftParser(command, nurseName, startTime, endTime, date, shiftTask, shiftIndex);
            } else if (command.equals("del")) {
                if (!remaining.contains("sn/")) {
                    System.out.println("Invalid 'shift del' format! Missing 'sn/' marker.");
                    return null;
                }

                try {
                    shiftIndex = Integer.parseInt(extractValue(remaining, "sn/", null)) - 1;
                    if (shiftIndex < 0) {
                        System.out.println("Shift index must be a positive integer.");
                        return null;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid shift index! Must be a number.");
                    return null;
                }

                return new ShiftParser(command, nurseName, startTime, endTime, date, shiftTask, shiftIndex);
            } else {
                System.out.println("Invalid command type! Use 'add' or 'del'.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error parsing shift command: " + e.getMessage());
            return null;
        }
    }

    /**
     * Extracts a value from a command string between specified markers.
     *
     * @param input       The command string.
     * @param startMarker The starting marker for the value.
     * @param endMarker   The ending marker for the value (can be {@code null}).
     * @return The extracted value as a {@code String}, or an empty string if the value is not found.
     */
    private static String extractValue(String input, String startMarker, String endMarker) {
        int start = input.indexOf(startMarker);
        if (start == -1) {
            return "";
        }

        start += startMarker.length();
        int end = (endMarker != null) ? input.indexOf(endMarker, start) : -1;

        return (end == -1) ? input.substring(start).trim() : input.substring(start, end).trim();
    }

    // Getters

    /**
     * Gets the command type.
     *
     * @return The command type as a {@code String}.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets the name of the nurse.
     *
     * @return The nurse's name as a {@code String}.
     */
    public String getName() {
        return nurseName;
    }

    /**
     * Gets the start time of the shift.
     *
     * @return The start time as a {@code LocalTime} object.
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Gets the end time of the shift.
     *
     * @return The end time as a {@code LocalTime} object.
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Gets the date of the shift.
     *
     * @return The date as a {@code LocalDate} object.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Gets the task assigned during the shift.
     *
     * @return The shift task as a {@code String}.
     */
    public String getNotes() {
        return shiftTask;
    }

    /**
     * Gets the index of the shift.
     *
     * @return The shift index as an {@code int}.
     */
    public int getIndex() {
        return shiftIndex;
    }
}
