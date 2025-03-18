package seedu.nursesched.parser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import seedu.nursesched.exception.ExceptionMessage;
import seedu.nursesched.exception.NurseSchedException;

/**
 * The {@code ShiftParser} class is responsible for parsing shift-related commands.
 * It extracts the necessary details to create or delete a shift.
 */
public class ShiftParser extends Parser {
    private final String command;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final LocalDate date;
    private final String shiftTask;
    private final int shiftIndex;

    /**
     * Constructs a {@code ShiftParser} object with extracted shift details.
     *
     * @param command    The command type (either "add" or "del").
     * @param startTime  The start time of the shift.
     * @param endTime    The end time of the shift.
     * @param date       The date of the shift.
     * @param shiftTask  The task assigned during the shift.
     * @param shiftIndex The index of the shift (for deletion).
     */
    public ShiftParser(String command, LocalTime startTime, LocalTime endTime,
                       LocalDate date, String shiftTask, int shiftIndex) {
        this.command = command;
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
    public static ShiftParser extractInputs(String line) throws NurseSchedException {
        if (line == null || line.trim().isEmpty()) {
            throw new NurseSchedException(ExceptionMessage.INPUT_EMPTY);
        }

        line = line.trim().toLowerCase();
        String[] parts = line.split(" ", 2);
        if (parts.length < 2) {
            throw new NurseSchedException(ExceptionMessage.INVALID_FORMAT);
        }

        String remaining = parts[1];

        // Variables for extracted values
        String command = "";
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
                return getShiftAddParser(remaining, command, shiftIndex);
            } else if (command.equals("del")) {
                return getShiftDelParser(remaining, command, startTime, endTime, date, shiftTask);
            } else {
                throw new NurseSchedException(ExceptionMessage.INVALID_COMMAND);
            }
        } catch (Exception e) {
            throw new NurseSchedException(ExceptionMessage.PARSING_ERROR);
        }
    }

    private static ShiftParser getShiftDelParser(String remaining, String command, LocalTime startTime, LocalTime endTime, LocalDate date, String shiftTask) throws NurseSchedException {
        int shiftIndex;
        if (!remaining.contains("sn/")) {
            throw new NurseSchedException(ExceptionMessage.INVALID_SHIFTDEL_FORMAT);
        }
        try {
            shiftIndex = Integer.parseInt(extractValue(remaining, "sn/", null)) - 1;
            if (shiftIndex < 0) {
                throw new NurseSchedException(ExceptionMessage.INVALID_SHIFT_NUMBER);
            }
        } catch (NumberFormatException e) {
            throw new NurseSchedException(ExceptionMessage.INVALID_SHIFT_NUMBER);
        }

        return new ShiftParser(command, startTime, endTime, date, shiftTask, shiftIndex);
    }

    private static ShiftParser getShiftAddParser(String remaining, String command, int shiftIndex) throws NurseSchedException {
        LocalDate date;
        LocalTime startTime;
        LocalTime endTime;
        String shiftTask;

        // Ensure all required markers exist
        if (!remaining.contains("s/") || !remaining.contains("e/") ||
                !remaining.contains("d/") || !remaining.contains("st/")) {
            throw new NurseSchedException(ExceptionMessage.INVALID_SHIFTADD_FORMAT);
        }

        try {
            startTime = LocalTime.parse(extractValue(remaining, "s/", "e/"));
            endTime = LocalTime.parse(extractValue(remaining, "e/", "d/"));
        } catch (DateTimeParseException e) {
            throw new NurseSchedException(ExceptionMessage.INVALID_TIME_FORMAT);
        }

        try {
            date = LocalDate.parse(extractValue(remaining, "d/", "st/"));
        } catch (DateTimeParseException e) {
            throw new NurseSchedException(ExceptionMessage.INVALID_DATE_FORMAT);
        }

        shiftTask = extractValue(remaining, "st/", null);
        if (shiftTask.isEmpty()) {
            throw new NurseSchedException(ExceptionMessage.SHIFT_TASK_EMPTY);
        }

        // Validate start time before end time
        if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
            throw new NurseSchedException(ExceptionMessage.INVALID_START_TIME);
        }

        return new ShiftParser(command, startTime, endTime, date, shiftTask, shiftIndex);
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

    /**
     * Gets the command type.
     *
     * @return The command type as a {@code String}.
     */
    public String getCommand() {
        return command;
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

    public String getShiftTask() {
        return shiftTask;
    }
}
