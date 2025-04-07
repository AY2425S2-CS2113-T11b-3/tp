package seedu.nursesched.storage;

import seedu.nursesched.exception.ExceptionMessage;
import seedu.nursesched.exception.NurseSchedException;
import seedu.nursesched.task.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskStorage {
    private static final String FILE_PATH = "data/Task.txt";

    /**
     * Reads the task data from the storage file and returns a list of tasks.
     * If the file does not exist, it will create necessary directories and return an empty list.
     *
     * @return A list of tasks read from the file.
     */
    public static ArrayList<Task> readFile() {
        File taskFile = new File(FILE_PATH);
        ArrayList<Task> taskList = new ArrayList<>();

        if (!taskFile.exists()) {
            taskFile.getParentFile().mkdirs();
            return taskList;
        }

        try (Scanner fileScanner = new Scanner(taskFile)) {
            while (fileScanner.hasNext()) {
                String currentLine = fileScanner.nextLine();
                Task task = parseTask(currentLine);
                if (task != null) {
                    taskList.add(task);
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found at: " + FILE_PATH);
        }
        return taskList;
    }

    /**
     * Parses a string representing a task and returns a Task object.
     * The expected format is: "completion status | description | due date | due time"
     *
     * @param currentLine The line representing a task.
     * @return A Task object with the parsed values.
     * @throws IllegalArgumentException If the format is invalid.
     */
    private static Task parseTask(String currentLine) {
        try {
            String[] parts = currentLine.split(" \\| ");

            if (parts.length != 4) {
                System.out.println("Invalid task format in storage file: " + currentLine);
                return null;
            }

            String completionStatus = parts[0];
            if (!completionStatus.equals("[ ]") && !completionStatus.equals("[X]")) {
                System.out.println("Invalid task's completion status in storage file: " + currentLine);
                System.out.println("Completion status should be either [ ] or [X]");
                return null;
            }

            boolean isDone = completionStatus.equals("[X]");

            String description = parts[1];
            if (description.isEmpty()) {
                System.out.println("Invalid task description in storage file: " + currentLine);
                return null;
            }

            LocalDate byDate;
            LocalTime byTime;
            try {
                byDate = LocalDate.parse(parts[2]);
                byTime = LocalTime.parse(parts[3]);
            } catch (DateTimeParseException e) {
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
            }
            return new Task(description, byDate, byTime, isDone);
        } catch (Exception e) {
            System.out.println("Unexpected error while parsing tasks -> " + currentLine);
            return null;
        }
    }

    /**
     * Formats a Task object into a string representation suitable for saving.
     * The format is: "completion status | description | due date | due time"
     *
     * @param task The Task object to format.
     * @return A formatted string representation of the task.
     */
    public static String formatString(Task task) {
        return (task.getIsDone() ? "[X]" : "[ ]") + " | "
                + task.getDescription() + " | "
                + task.getByDate() + " | "
                + task.getByTime();
    }

    /**
     * Overwrites the storage file with the list of tasks.
     *
     * @param taskList The list of tasks to save to the file.
     */
    public static void overwriteSaveFile(ArrayList<Task> taskList) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            for (Task task : taskList) {
                writer.write(formatString(task) + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }
}
