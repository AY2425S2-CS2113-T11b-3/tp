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
     * Extracts and parses the inputs from the given command for task-related operations.
     * This method supports 7 commands "add", "del", "mark", "unmark", "list", "edit" and "find".
     *
     * @param line The user's input command to be parsed.
     * @return An {@link TaskParser} object which contains the parsed commands and associated parameters.
     *         Returns {@code null} if the input parameters are missing or invalid.
     *
     * @throws NurseSchedException If any inputs are invalid.
     */
    public static TaskParser extractInputs(String line) throws NurseSchedException {
        assert line != null : "Input line should not be null";
        line = line.trim();
        line = line.substring(line.indexOf(" ") + 1);
        String command = "";
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
            return getAddTaskParser(line, command);
        case "mark", "unmark", "del":
            return getIndexParser(line, command);
        case "list":
            return getListTaskParser(line, command);
        case "edit":
            return getEditTaskParser(line, command);
        case "find":
            return getFindTaskParser(line, command);
        default:
            System.out.println("Unknown task command!");
            break;
        }
        logr.warning("Unknown command: " + command);
        return null;
    }

    /**
     * Parses the parameters for editing a task.
     *
     * @param line The user input containing the parameters of the command.
     * @param command Represents 1 of the 7 available commands. Add, del, mark, unmark, edit, find or list.
     * @return A TaskParser object with parameters to edit the task.
     * @throws NurseSchedException If the user input contains invalid parameters or has missing fields.
     */
    public static TaskParser getEditTaskParser(String line, String command) throws NurseSchedException {
        StringBuilder description = new StringBuilder();
        LocalDate byDate = null;
        LocalTime byTime = null;
        int taskIndex = 0;
        if (!line.contains("id/")) {
            logr.warning("Missing task index!");
            throw new NurseSchedException(ExceptionMessage.INVALID_TASK_EDIT_FORMAT);
        }
        try {
            String[] parameters = line.split("\\s+");
            String byDateString = "";
            String byTimeString = "";
            for (String parameter : parameters) {
                if (parameter.contains("id/")) {
                    taskIndex = Integer.parseInt(parameter.substring(3));
                } if (!line.contains("td/") && line.contains("d/") && !line.contains("t/")) {
                    logr.info("Empty edit parameters, no edits made.");
                    throw new NurseSchedException(ExceptionMessage.NO_EDITS_MADE);
                } else if (parameter.contains("td/")) {
                    description = new StringBuilder(parameter.substring(3));
                    if (description.isEmpty()) {
                        logr.warning("Invalid attempt to edit current " +
                                "task description with an empty description!");
                        throw new NurseSchedException(ExceptionMessage.EMPTY_TASK_DESCRIPTION);
                    } else if (description.toString().contains("|")) {
                        logr.warning("Attempt to edit task with a description containing \"|\"!");
                        throw new NurseSchedException(ExceptionMessage.INVALID_DESCRIPTION);
                    }
                } else if (parameter.contains("d/")) {
                    byDateString = parameter.substring(2);
                    if (byDateString.isEmpty()) {
                        logr.warning("Invalid attempt to edit current task due date with " +
                                "empty input.");
                        throw new NurseSchedException(ExceptionMessage.EMPTY_TASK_DATE_TIME);
                    }
                    byDate = LocalDate.parse(byDateString);
                } else if (parameter.contains("t/")) {
                    byTimeString = parameter.substring(2);
                    if (byTimeString.isEmpty()) {
                        logr.warning("Invalid attempt to edit current task due time with " +
                                "empty input.");
                        throw new NurseSchedException(ExceptionMessage.EMPTY_TASK_DATE_TIME);
                    }
                    byTime = LocalTime.parse(byTimeString);
                } else {
                    if (!description.isEmpty()) {
                        description.append(" ").append(parameter);
                    }
                }
            }
            if (taskIndex <= 0) {
                throw new NurseSchedException(ExceptionMessage.NEGATIVE_INDEX);
            }

            LocalDate dateNow = LocalDate.now();
            LocalTime timeNow = LocalTime.now();

            if ((byDate != null && byTime != null) && ((byDate.isBefore(dateNow))
                    || (byDate.isEqual(dateNow) && byTime.isBefore(timeNow)))) {
                logr.warning("Due date and time cannot be before current date and time.");
                throw new NurseSchedException(ExceptionMessage.INVALID_DUE_DATE_TIME);
            }
        } catch (NumberFormatException e) {
            logr.warning("Task index not an integer!");
            throw new NurseSchedException(ExceptionMessage.INVALID_TASK_INDEX);
        } catch (DateTimeParseException e) {
            logr.warning("Invalid date or time format.");
            throw new NurseSchedException(ExceptionMessage.INVALID_DATETIME_FORMAT);
        } catch (IndexOutOfBoundsException e) {
            logr.warning("Invalid or missing fields");
            throw new NurseSchedException(ExceptionMessage.INVALID_TASK_EDIT_FORMAT);
        }
        return new TaskParser(command, description.toString(), byDate, byTime, false, taskIndex);
    }

    public static TaskParser getIndexParser(String line, String command) throws NurseSchedException {
        int taskIndex;
        if (!line.contains("id/")) {
            logr.warning("Missing task index!");
            throw new NurseSchedException(ExceptionMessage.MISSING_INDEX);
        }
        try {
            taskIndex = Integer.parseInt(line.substring(line.indexOf("id/") + 3));
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
        return new TaskParser(command, "", null, null, false, taskIndex);
    }

    /**
     * Parses the parameters for adding a task.
     *
     * @param line The user input containing the parameters of the command.
     * @param command Represents 1 of the 7 available commands. Add, del, mark, unmark, edit, find or list.
     * @return A TaskParser object with parameters to add a task.
     * @throws NurseSchedException If the user input contains invalid parameters or has missing fields.
     */
    public static TaskParser getAddTaskParser(String line, String command) throws NurseSchedException {
        StringBuilder description;
        LocalDate byDate;
        LocalTime byTime;
        String dateString;
        String timeString;

        if (!line.contains("td/") || !line.contains(" d/") || !line.contains(" t/")) {
            logr.warning("Missing fields");
            throw new NurseSchedException(ExceptionMessage.INVALID_TASK_ADD_FORMAT);
        }

        try {
            //extracts task description
            description = new StringBuilder(line.substring(line.indexOf("td/") + 3, line.indexOf(" d/")));
            if (description.isEmpty()) {
                logr.warning("Attempt to add empty task description!");
                throw new NurseSchedException(ExceptionMessage.EMPTY_TASK_DESCRIPTION);
            } else if (description.toString().contains("|")) {
                logr.warning("Attempt to add task with a description containing \"|\"!");
                throw new NurseSchedException(ExceptionMessage.INVALID_DESCRIPTION);
            }

            //extracts task's due date
            dateString = line.substring(line.indexOf(" d/") + 3, line.indexOf("t/") - 1);
            //extracts task's due time
            timeString = line.substring(line.indexOf("t/") + 2);

            if (dateString.isEmpty() || timeString.isEmpty()) {
                logr.warning("Attempt to add empty task due date or time!");
                throw new NurseSchedException(ExceptionMessage.EMPTY_TASK_DATE_TIME);
            }

            byDate = LocalDate.parse(dateString);
            byTime = LocalTime.parse(timeString);

            if (byDate.isEqual(LocalDate.now()) && byTime.isBefore(LocalTime.now())) {
                logr.warning("Due date and time cannot be before current date and time.");
                throw new NurseSchedException(ExceptionMessage.INVALID_DUE_DATE_TIME);
            }
        } catch (DateTimeParseException e) {
            logr.warning("Invalid date or time format.");
            String msg = e.getMessage();
            //Find exactly where the error lies
            if (msg.contains("HourOfDay")) {
                throw new NurseSchedException(ExceptionMessage.INVALID_HOUR);
            } else if (msg.contains("MinuteOfHour")) {
                throw new NurseSchedException(ExceptionMessage.INVALID_MINUTE);
            } else if (msg.contains("MonthOfYear")) {
                throw new NurseSchedException(ExceptionMessage.INVALID_MONTH);
            } else if (msg.contains("DayOfMonth")) {
                throw new NurseSchedException(ExceptionMessage.INVALID_DAY);
            } else if (msg.contains("Invalid date")) {
                throw new NurseSchedException(ExceptionMessage.INVALID_DATE);
            }
            throw new NurseSchedException(ExceptionMessage.INVALID_DATETIME_FORMAT);
        } catch (IndexOutOfBoundsException e) {
            logr.warning("Invalid or missing fields");
            throw new NurseSchedException(ExceptionMessage.INVALID_TASK_ADD_FORMAT);
        }
        return new TaskParser(command, description.toString(), byDate, byTime, false, 0);
    }

    /**
     * Parses the parameters for finding a task.
     *
     * @param line The user input containing the parameters of the command.
     * @param command Represents 1 of the 7 available commands. Add, del, mark, unmark, edit, find or list.
     * @return A TaskParser object with parameters to find a task.
     * @throws NurseSchedException If the user input contains invalid parameters or has missing fields.
     */
    public static TaskParser getFindTaskParser(String line, String command) throws NurseSchedException {
        if (!line.contains("td/")) {
            logr.warning("Missing fields");
            throw new NurseSchedException(ExceptionMessage.INVALID_TASK_FIND_FIELDS);
        }
        String keyword = null;
        try {
            keyword = line.substring(line.indexOf("td/") + 3);
            if (keyword.isEmpty()) {
                logr.warning("Attempt to find task with empty keyword!");
                throw new NurseSchedException(ExceptionMessage.MISSING_TASK_KEYWORD);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new NurseSchedException(ExceptionMessage.MISSING_TASK_KEYWORD);
        }
        return new TaskParser(command, keyword, null, null, false, 0);
    }

    public static TaskParser getListTaskParser(String line, String command) throws NurseSchedException {
        if (!line.equals("list")) {
            throw new NurseSchedException(ExceptionMessage.INVALID_LIST_TASK);
        }
        return new TaskParser(command, null, null, null, false, 0);
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
