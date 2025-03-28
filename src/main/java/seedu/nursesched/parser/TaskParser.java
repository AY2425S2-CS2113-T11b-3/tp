package seedu.nursesched.parser;

import seedu.nursesched.exception.ExceptionMessage;
import seedu.nursesched.exception.NurseSchedException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

public class TaskParser extends Parser {
    private static final Logger logr = Logger.getLogger("ApptParser");

    private String command;
    private String description;
    private LocalDate byDate;
    private LocalTime byTime;
    private boolean isDone;
    private int taskIndex;

    static {
        try {
            File logDir = new File("logs/parser");
            if (!logDir.exists()) {
                boolean isCreated = logDir.mkdirs();  // Creates the directory and any missing parent directories
                if (!isCreated) {
                    throw new IOException("Error creating log directory!");
                }
            }

            LogManager.getLogManager().reset();
            FileHandler fh = new FileHandler("logs/parser/taskParser.log", true);
            fh.setFormatter(new SimpleFormatter());
            logr.addHandler(fh);
            logr.setLevel(Level.ALL);
        } catch (IOException e) {
            logr.log(Level.SEVERE, "File logger not working", e);
        }
    }

    public TaskParser(String command, String description, LocalDate byDate,
                      LocalTime byTime, boolean isDone, int taskIndex) {
        this.command = command;
        this.description = description;
        this.byDate = byDate;
        this.byTime = byTime;
        this.isDone = isDone;
        this.taskIndex = taskIndex;
    }

    /**
     * Extracts and parses the inputs from the given command for appointment-related operations.
     * This method supports 4 commands "add", "mark", "unmark" and "list.
     *
     * @param line The user's input command to be parsed.
     * @return An {@link TaskParser} object which contains the parsed commands and associated parameters.
     *         Returns {@code null} if the input parameters are missing or invalid.
     *
     * @throws NurseSchedException if any inputs are invalid.
     * @throws IndexOutOfBoundsException If the input line does not contain the expected parameters.
     * @throws DateTimeParseException If the input time or date is not of the expected format.
     */
    public static TaskParser extractInputs(String line) throws NurseSchedException {
        assert line != null : "Input line should not be null";
        line = line.trim();
        line = line.substring(line.indexOf(" ") + 1);
        String command = "";
        String description = "";
        LocalDate byDate = null;
        LocalTime byTime = null;
        boolean isDone = false;
        int taskIndex = 0;

        try {
            if (line.contains(" ")) {
                command = line.substring(0, line.indexOf(" "));
                line = line.substring(line.indexOf(" ") + 1);
            } else {
                command = line;
            }
        } catch (IndexOutOfBoundsException e) {
            logr.warning("Invalid command: " + command);
            System.out.println("Invalid inputs! Please try again.");
            return null;
        }

        switch (command) {
        case "add":
            if (!line.contains("td/") || !line.contains("d/") || !line.contains("t/")) {
                logr.warning("Missing fields");
                throw new NurseSchedException(ExceptionMessage.INVALID_TASKADD_FORMAT);
            }

            try {
                //extracts task description
                description = line.substring(line.indexOf("td/") + 3, line.indexOf(" d/"));

                //extracts task's due date
                byDate = LocalDate.parse(line.substring(line.indexOf(" d/") + 3, line.indexOf("t/") - 1));

                //extracts task's due time
                byTime = LocalTime.parse(line.substring(line.indexOf("t/") + 2));
                if (byDate.isEqual(LocalDate.now()) && byTime.isBefore(LocalTime.now())) {
                    logr.warning("Due date and time cannot be before current date and time.");
                    throw new NurseSchedException(ExceptionMessage.INVALID_DUE_DATE_TIME);
                }
            } catch (DateTimeParseException e) {
                logr.warning("Invalid date or time format.");
                throw new NurseSchedException(ExceptionMessage.INVALID_DATETIME_FORMAT);
            } catch (IndexOutOfBoundsException e) {
                logr.warning("Invalid or missing fields");
                throw new NurseSchedException(ExceptionMessage.INVALID_TASKADD_FORMAT);
            }
            return new TaskParser(command, description, byDate, byTime, isDone, taskIndex);
        case "mark", "unmark":
            try {
                System.out.println(line);
                taskIndex = Integer.parseInt(line);
                if (taskIndex <= 0) {
                    throw new NurseSchedException(ExceptionMessage.NEGATIVE_INDEX);
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Task index is missing!");
                logr.warning("Task index missing.");
                return null;
            } catch (NumberFormatException e) {
                logr.warning("Task index not an integer!");
                throw new NurseSchedException(ExceptionMessage.INVALID_TASK_INDEX);
            }
            return new TaskParser(command, description, byDate, byTime, isDone, taskIndex);
        case "list":
            return new TaskParser(command, description, byDate, byTime, isDone, taskIndex);
        default:
            System.out.println("Unknown command: " + command);
            break;
        }

        logr.warning("Invalid command: " + command);
        return null;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getByDate() {
        return byDate;
    }

    public LocalTime getByTime() {
        return byTime;
    }

    public boolean isDone() {
        return isDone;
    }

    public int getTaskIndex() {
        return taskIndex;
    }
}
