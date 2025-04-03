package seedu.nursesched.storage;

import seedu.nursesched.task.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
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
                taskList.add(task);
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
        String[] parts = currentLine.split(" \\| ");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid task format in storage file: " + currentLine);
        }

        String completionStatus = parts[0];
        boolean isDone = completionStatus.equals("[X]");
        String description = parts[1];
        LocalDate byDate = LocalDate.parse(parts[2]);
        LocalTime byTime = LocalTime.parse(parts[3]);

        return new Task(description, byDate, byTime, isDone);
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
