package seedu.nursesched.task;

import seedu.nursesched.exception.ExceptionMessage;
import seedu.nursesched.exception.NurseSchedException;
import seedu.nursesched.storage.TaskStorage;

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
    private static ArrayList<Task> taskList;
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
        taskList = TaskStorage.readFile();

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
        this.isDone = isDone;
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
            logr.warning("Due date and time cannot be in the past!");
            throw new NurseSchedException(ExceptionMessage.INVALID_DUE_DATE_TIME);
        }
        taskList.add(new Task(description, byDate, byTime, isDone));
        TaskStorage.overwriteSaveFile(taskList);
        System.out.println("Task added: " + description);
        logr.info("Task added: " + description);
    }

    /**
     * Deletes a task from the task list.
     *
     * @param index The index of the task to be deleted from the list.
     * @throws NurseSchedException If the task index is out of range.
     */
    public static void deleteTask(int index) throws NurseSchedException {
        assert index > 0
                : "Task index should not be negative.";
        if (index > taskList.size()) {
            logr.warning("Task index out of range.");
            throw new NurseSchedException(ExceptionMessage.TASK_INDEX_OUT_OF_BOUNDS);
        }
        try {
            taskList.remove(index - 1);
            TaskStorage.overwriteSaveFile(taskList);
            System.out.println("Task deleted successfully!");
            logr.info("Task deleted.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("There is no index " + index + " in the task list!");
            logr.warning("There is no index " + index + " in the task list!");
        }
    }

    /**
     * Marks a task as done.
     *
     * @param index The index of the task according to the list of all tasks.
     * @throws NurseSchedException If task index is out of range.
     */
    public static void markTask(int index) throws NurseSchedException {
        assert index > 0
                : "Task index should not be negative.";
        if (index > taskList.size()) {
            logr.warning("Task index out of range.");
            throw new NurseSchedException(ExceptionMessage.TASK_INDEX_OUT_OF_BOUNDS);
        }
        try {
            Task task = taskList.get(index - 1);
            if (task.getIsDone()) {
                logr.warning("Invalid attempt to mark an already marked task.");
                throw new NurseSchedException(ExceptionMessage.MARKING_A_MARKED_TASK);
            }
            task.setIsDone(true);
            TaskStorage.overwriteSaveFile(taskList);
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
     * @throws NurseSchedException If task index is out of range.
     */
    public static void unmarkTask(int index) throws NurseSchedException {
        assert index > 0
                : "Task index should not be negative.";
        if (index > taskList.size()) {
            logr.warning("Task index out of range.");
            throw new NurseSchedException(ExceptionMessage.TASK_INDEX_OUT_OF_BOUNDS);
        }
        try {
            Task task = taskList.get(index - 1);
            if (!task.getIsDone()) {
                logr.warning("Invalid attempt to unmark an already unmarked task.");
                throw new NurseSchedException(ExceptionMessage.UNMARKING_AN_UNMARKED_TASK);
            }
            task.setIsDone(false);
            TaskStorage.overwriteSaveFile(taskList);
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
        if (taskList.isEmpty()) {
            System.out.println("You have 0 tasks.");
            return;
        }
        int listSize = taskList.size();
        for (int index = 0; index < listSize; index++) {
            System.out.println((index + 1) + ". " + taskList.get(index).toString());
        }
        System.out.println("You have " + listSize + (listSize == 1 ? " task!" : " tasks!"));
        logr.info("All tasks listed");
    }

    /**
     * Edits a task's {@code description}, {@code byDate} and/or {@code byTime}.
     *
     * @param index The index of the task to be edited.
     * @param description The new description of the task.
     * @param byDate The new due date of the task.
     * @param byTime The new due time of the task.
     * @throws NurseSchedException If task index is out of range or not an integer,
     *                             or if the {@code byDate} and {@code byTime} is in the past.
     */
    public static void editTask(int index, String description,
                                LocalDate byDate, LocalTime byTime) throws NurseSchedException {
        assert index > 0
                : "Task index should not be negative.";
        logr.info("Attempting to edit task " + index);

        if (index > taskList.size()) {
            logr.warning("Task index out of range.");
            System.out.println("Task could not be edited!");
            throw new NurseSchedException(ExceptionMessage.TASK_INDEX_OUT_OF_BOUNDS);
        }

        Task task = taskList.get(index - 1);

        LocalDate originalDate = task.getByDate();
        LocalTime originalTime = task.getByTime();
        LocalDate dateNow = LocalDate.now();
        LocalTime timeNow = LocalTime.now();
        System.out.println("Before edit: " + task.toString());

        if (byDate != null && byTime != null) {
            task.setByDate(byDate);
            task.setByTime(byTime);
        } else if (byDate != null) {
            //Check validity if only due date and not due time needs to be edited
            if (byDate.isAfter(dateNow) || (byDate.isEqual(dateNow) && originalTime.isAfter(timeNow))) {
                task.setByDate(byDate);
            } else {
                logr.warning("Due date and time cannot be in the past!");
                System.out.println("Task could not be edited!");
                throw new NurseSchedException(ExceptionMessage.INVALID_DUE_DATE_TIME);
            }
        } else if (byTime != null) {
            //Check validity if only due time and not due date needs to be edited
            if (originalDate.isAfter(dateNow) || (originalDate.isEqual(dateNow) && byTime.isAfter(timeNow))) {
                task.setByTime(byTime);
            } else {
                logr.warning("Due date and time cannot be in the past!");
                System.out.println("Task could not be edited!");
                throw new NurseSchedException(ExceptionMessage.INVALID_DUE_DATE_TIME);
            }
        }
        if (!description.isEmpty()) {
            task.setDescription(description);
        }
        TaskStorage.overwriteSaveFile(taskList);
        System.out.println("After edit: " + task.toString());
        logr.info("Task edited successfully!");
    }

    /**
     * Displays all tasks which contain the keyword in its description.
     *
     * @param keyword The keyword or phrase to be searched for.
     */
    public static void findTask(String keyword) {
        assert !keyword.isEmpty() : "Keyword to find cannot be empty.";
        keyword = keyword.toLowerCase();
        int totalFound = 0;
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            if (task.getDescription().toLowerCase().contains(keyword)) {
                totalFound++;
                System.out.println((i + 1) + ". " + task.toString());
            }
        }
        if (totalFound == 0) {
            System.out.println("There are no tasks with the keyword \"" + keyword + "\"");
        } else {
            System.out.println("There are " + totalFound + " tasks with the keyword \"" + keyword + "\"");
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getByDate() {
        return byDate;
    }

    public void setByDate(LocalDate byDate) {
        this.byDate = byDate;
    }

    public LocalTime getByTime() {
        return byTime;
    }

    public void setByTime(LocalTime byTime) {
        this.byTime = byTime;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public static ArrayList<Task> getTaskList() {
        return taskList;
    }

    public static void resetTaskList() {
        taskList = new ArrayList<Task>();
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
