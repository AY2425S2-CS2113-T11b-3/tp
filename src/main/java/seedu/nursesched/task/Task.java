package seedu.nursesched.task;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
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
     */
    public void addTask(String description, LocalDate byDate, LocalTime byTime,  boolean isDone) {
        assert byTime.isAfter(LocalTime.now())
                && (byDate.isEqual(LocalDate.now()) || byDate.isAfter(LocalDate.now()))
                : "Due date and time should not be set in the past!";

        taskList.add(new Task(description, byDate, byTime, isDone));
        System.out.println("Task added: " + description);
        logr.info("Task added: " + description);
    }

    /**
     * Marks a task as done.
     *
     * @param index The index of the task according to the list of all tasks.
     */
    public void markTask(int index) {
        assert index > 0 && index <= taskList.size()
                : "Task index should not be less than zero or greater than task list size!";
        Task task = taskList.get(index);
        task.setIsDone(true);
        System.out.println("Task marked: " + taskList.get(index).toString());
        logr.info("Task marked: " + taskList.get(index).description);
    }

    /**
     * Unmarks a task as undone.
     *
     * @param index The index of the task according to the list of all tasks.
     */
    public void unmarkTask(int index) {
        assert index > 0 && index <= taskList.size()
                : "Task index should not be less than zero or greater than task list size!";
        Task task = taskList.get(index);
        task.setIsDone(false);
        System.out.println("Task unmarked: " + taskList.get(index).toString());
        logr.info("Task unmarked: " + taskList.get(index).description);
    }

    /**
     * Lists out all tasks in the task list.
     */
    public void listTasks() {
        int listSize = taskList.size();
        for (int index = 0; index < listSize; index++) {
            System.out.println((index + 1) + ". " + taskList.get(index).toString());
        }
        System.out.println("You have " + listSize + " tasks!");
        logr.info("All tasks listed");
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        return isDone ? "[X] " : "[ ] "
                + "Task: " + description
                + ", By: " + byDate + " " + byTime;
    }
}
