package seedu.nursesched.parser;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import seedu.nursesched.exception.ExceptionMessage;
import seedu.nursesched.exception.NurseSchedException;

/**
 * The {@code ShiftParser} class is responsible for parsing shift-related commands.
 * It extracts the necessary details to create, delete, or list shifts.
 */
public class ShiftParser extends Parser {
    private static final Logger logr = Logger.getLogger("ShiftParser");

    private final String command;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final LocalDate date;
    private final String shiftTask;
    private final int shiftIndex;

    static {
        try {
            LogManager.getLogManager().reset();
            FileHandler fh = new FileHandler("logs/parser/shiftParser.log", true);
            fh.setFormatter(new SimpleFormatter());
            logr.addHandler(fh);
            logr.setLevel(Level.ALL);
        } catch (IOException e) {
            logr.log(Level.SEVERE, "File logger not working", e);
        }
    }

    /**
     * Constructs a {@code ShiftParser} object with extracted shift details.
     *
     * @param command    The command type (e.g., "add", "del", "list").
     * @param startTime  The start time of the shift (nullable for "del" and "list").
     * @param endTime    The end time of the shift (nullable for "del" and "list").
     * @param date       The date of the shift (nullable for "del" and "list").
     * @param shiftTask  The task assigned during the shift (nullable for "del" and "list").
     * @param shiftIndex The index of the shift (only used for "del").
     */
    public ShiftParser(String command, LocalTime startTime, LocalTime endTime,
                       LocalDate date, String shiftTask, int shiftIndex) throws NurseSchedException {
        assert command != null : "Command should not be null";
        this.command = command;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.shiftTask = shiftTask;
        this.shiftIndex = shiftIndex;

        logr.info("ShiftParser created: " + this);
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
        assert input != null : "Input string must not be null";
        assert startMarker != null : "Start marker must not be null";

        int start = input.indexOf(startMarker);
        if (start == -1) {
            return "";
        }

        start += startMarker.length();
        int end = (endMarker != null) ? input.indexOf(endMarker, start) : -1;

        return (end == -1) ? input.substring(start).trim() : input.substring(start, end).trim();
    }

    /**
     * Parses the input command and extracts shift-related details.
     *
     * @param line The input command string.
     * @return A {@code ShiftParser} object containing the extracted shift details.
     * @throws NurseSchedException If the input is invalid or incorrectly formatted.
     */
    public static ShiftParser extractInputs(String line) throws NurseSchedException {
        assert line != null : "Input line should not be null";
        logr.info("Extracting inputs from: " + line);

        if (line == null || line.trim().isEmpty()) {
            logr.warning("Input is empty.");
            throw new NurseSchedException(ExceptionMessage.INPUT_EMPTY);
        }

        line = line.trim().toLowerCase();
        String[] parts = line.split(" ", 2);
        if (parts.length < 2) {
            logr.warning("Invalid input format: " + line);
            throw new NurseSchedException(ExceptionMessage.INVALID_FORMAT);
        }

        String remaining = parts[1];

        // Extract actual command ("add", "del", "list")
        String[] commandParts = remaining.split(" ", 2);
        String command = (commandParts.length > 0) ? commandParts[0] : "";

        if (command.equals("add")) {
            return getShiftAddParser(remaining, command);
        } else if (command.equals("del")) {
            return getShiftDelParser(remaining, command);
        } else if (command.equals("list")) {
            return getShiftListParser();
        } else {
            logr.warning("Invalid command: " + command);
            throw new NurseSchedException(ExceptionMessage.INVALID_COMMAND);
        }
    }

    /**
     * Parses a delete shift command and extracts the shift index.
     */
    private static ShiftParser getShiftDelParser(String remaining, String command) {
        logr.info("Parsing delete command: " + remaining);
        int shiftIndex = Integer.parseInt(extractValue(remaining, "sn/", null)) - 1;

        if (!remaining.contains("sn/")) {
            logr.warning("Invalid delete format.");
            return null; // Instead of throwing an exception, return null to indicate failure
        }

        try {
            return new ShiftParser(command, null, null, null, null, shiftIndex);
        } catch (NurseSchedException e) {
            logr.severe("Error creating ShiftParser for list command: " + e.getMessage());
            return null; // Or handle it in another way
        }
    }

    /**
         * Parses an add shift command and extracts the shift details.
         */
    private static ShiftParser getShiftAddParser(String remaining, String command) throws NurseSchedException {
        logr.info("Parsing add command: " + remaining);

        LocalDate date;
        LocalTime startTime;
        LocalTime endTime;
        String shiftTask;

        if (!remaining.contains("s/") || !remaining.contains("e/") ||
                !remaining.contains("d/") || !remaining.contains("st/")) {
            logr.warning("Invalid add format.");
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
            logr.warning("Shift task is empty.");
            throw new NurseSchedException(ExceptionMessage.SHIFT_TASK_EMPTY);
        }

        return new ShiftParser(command, startTime, endTime, date, shiftTask, -1);
    }

    /**
     * Gets the start time of the shift.
     * @return The start time as a {@code LocalTime} object.
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Gets the end time of the shift.
     * @return The end time as a {@code LocalTime} object.
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Gets the date of the shift.
     * @return The date as a {@code LocalDate} object.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Gets the task assigned during the shift.
     * @return The shift task as a {@code String}.
     */
    public String getNotes() {
        return shiftTask;
    }


    /**
     * Handles the "list" shift command.
     */
    private static ShiftParser getShiftListParser() {
        logr.info("Parsing list command.");

        String command = "list";

        try {
            return new ShiftParser(command, null, null, null, null, -1);
        } catch (NurseSchedException e) {
            logr.severe("Error creating ShiftParser for list command: " + e.getMessage());
            return null;
        }
    }

    public String getCommand() {
        return command;
    }

    public int getIndex() {
        return shiftIndex;
    }
}
