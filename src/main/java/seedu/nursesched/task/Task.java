package seedu.nursesched.task;

import seedu.nursesched.exception.ExceptionMessage;
import seedu.nursesched.exception.NurseSchedException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Level;

/**
 * Represents all to-do tasks.
 * It contains details such as the task description, completion status, due date and time.
 */
public class Task {
    protected static ArrayList<Task> taskList = new ArrayList<Task>();
    private static final Logger logr = Logger.getLogger("Task");

    private String description;
    private LocalDate byDate;
    private LocalTime byTime;
    private boolean isDone;

    static {
        try {
            File logDir = new File("logs/task");
            if (!logDir.exists()) {
                boolean isCreated = logDir.mkdirs(); //Creates the directory and any missing parent directories
                if (!isCreated) {
                    throw new IOException("Error creating log directory!");
                }
            }

            LogManager.getLogManager().reset();
            FileHandler fh = new FileHandler("logs/task/task.log", true);
            fh.setFormatter(new SimpleFormatter());
            logr.addHandler(fh);
            logr.setLevel(Level.ALL);
        } catch (IOException e) {
            logr.log(Level.SEVERE, "File logger not working", e);
        }
    }

    /**
     * Constructs a Task object with the specified details.
     *
     * @param description The task description.
     * @param byDate The task's due date.
     * @param byTime The task's due time.
     * @param isDone The task's completion status, whether it is completed or not.
     */
    public Task(String description, LocalDate byDate, LocalTime byTime, boolean isDone) {
        this.description = description;
        this.byDate = byDate;
        this.byTime = byTime;
        this.isDone = false;
        logr.info("Task object created");
    }

    /**
     * Adds a new task to the list of tasks.
     *
     * @param description The task description.
     * @param byDate The task's due date.
     * @param byTime The task's due time.
     * @param isDone The task's completion status, whether it is completed or not.
     * @throws NurseSchedException If the {@code byDate} and {@code byTime} is in the past.
     */
    public static void addTask(String description, LocalDate byDate, LocalTime byTime,
                               boolean isDone) throws NurseSchedException {
        assert description != null : "Task description cannot be null";
        LocalDate dateNow = LocalDate.now();
        LocalTime timeNow = LocalTime.now();
        if (byDate.isBefore(dateNow) || (byDate.isEqual(dateNow) && byTime.isBefore(timeNow))) {
            throw new NurseSchedException(ExceptionMessage.INVALID_DUE_DATE_TIME);
        }
        taskList.add(new Task(description, byDate, byTime, isDone));
        System.out.println("Task added: " + description);
        logr.info("Task added: " + description);
    }

    /**
     * Marks a task as done.
     *
     * @param index The index of the task according to the list of all tasks.
     * @throws NurseSchedException If task index is out of range or not an integer.
     */
    public static void markTask(int index) throws NurseSchedException {
        assert index > 0
                : "Task index should not be negative.";
        if (index > taskList.size()) {
            logr.warning("Task index out of range.");
            throw new NurseSchedException(ExceptionMessage.INVALID_TASK_NUMBER);
        }
        try {
            Task task = taskList.get(index - 1);
            task.setIsDone(true);
            System.out.println("Task marked: " + taskList.get(index - 1).toString());
            logr.info("Task marked: " + taskList.get(index - 1).description);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("There is no index " + index + " in the task list!");
            logr.warning("There is no index " + index + " in the task list!");
        }
    }

    /**
     * Unmarks a task as undone.
     *
     * @param index The index of the task according to the list of all tasks.
     * @throws NurseSchedException If task index is out of range or not an integer.
     */
    public static void unmarkTask(int index) throws NurseSchedException {
        assert index > 0
                : "Task index should not be negative.";
        if (index > taskList.size()) {
            logr.warning("Task index out of range.");
            throw new NurseSchedException(ExceptionMessage.INVALID_TASK_NUMBER);
        }
        try {
            Task task = taskList.get(index - 1);
            task.setIsDone(false);
            System.out.println("Task unmarked: " + taskList.get(index - 1).toString());
            logr.info("Task unmarked: " + taskList.get(index - 1).description);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("There is no index " + index + " in the task list!");
            logr.warning("There is no index " + index + " in the task list!");
        }
    }

    /**
     * Lists out all tasks in the task list.
     */
    public static void listTasks() {
        int listSize = taskList.size();
        for (int index = 0; index < listSize; index++) {
            System.out.println((index + 1) + ". " + taskList.get(index).toString());
        }
        System.out.println("You have " + listSize + (listSize == 1 ? " task!" : " tasks!"));
        logr.info("All tasks listed");
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
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

    public boolean getIsDone() {
        return isDone;
    }

    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        return (isDone ? "[X] " : "[ ] ")
                + description
                + ", By: " + byDate.format(dateFormatter)
                + ", " + byTime.format(timeFormatter);
    }
}
