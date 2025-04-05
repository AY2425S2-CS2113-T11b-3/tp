package seedu.nursesched.task;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import seedu.nursesched.exception.NurseSchedException;
import seedu.nursesched.storage.TaskStorage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskTest {
    static ArrayList<Task> originalList;

    @BeforeAll
    public static void saveOriginalList() {
        originalList = Task.getTaskList();
    }

    @AfterAll
    public static void restoreOriginalList() {
        TaskStorage.overwriteSaveFile(originalList);
    }

    //Tests related to adding of tasks
    @Test
    public void addTask_validInputs_taskAdded() throws NurseSchedException {
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);
        LocalTime timeNow = LocalTime.now();
        Task.getTaskList().clear();

        //Add a task which is due 24 hours later
        Task.addTask(
                "Prepare medication for Jean",
                dateTomorrow,
                timeNow,
                false
        );

        Task task = Task.getTaskList().get(0);
        assertEquals("Prepare medication for Jean", task.getDescription());
        assertEquals(dateTomorrow, task.getByDate());
        assertEquals(timeNow, task.getByTime());
        assertFalse(task.getIsDone());
    }

    @Test
    public void addTask_dateSetInThePast_throwsNurseSchedException() {
        assertThrows(NurseSchedException.class,
                () -> Task.addTask(
                        "Prepare medication for Jean",
                        LocalDate.of(2025, 3, 14),
                        LocalTime.of(13, 0),
                        false
                )
        );
    }

    //Tests related to marking of tasks
    @Test
    public void markTask_validIndex_taskMarked() throws NurseSchedException {
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);
        LocalTime timeNow = LocalTime.now();
        Task.getTaskList().clear();

        //Add a task which is due 24 hours later
        Task.addTask(
                "Prepare medication for Jean",
                dateTomorrow,
                timeNow,
                false
        );
        Task.markTask(1);
        assertTrue(Task.getTaskList().get(0).getIsDone());
    }

    @Test
    public void markTask_zeroOrNegativeIndex_throwsAssertionError()
            throws NurseSchedException {
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);
        LocalTime timeNow = LocalTime.now();
        Task.getTaskList().clear();
        //Add a task which is due 24 hours later
        Task.addTask(
                "Prepare medication for Jean",
                dateTomorrow,
                timeNow,
                false
        );
        assertThrows(AssertionError.class,
                () -> Task.markTask(0));
        assertThrows(AssertionError.class,
                () -> Task.markTask(-1));
    }

    @Test
    public void markTask_indexOutOfBounds_throwsNurseSchedException()
            throws NurseSchedException {
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);
        LocalTime timeNow = LocalTime.now();
        Task.getTaskList().clear();
        //Add a task which is due 24 hours later
        Task.addTask(
                "Prepare medication for Jean",
                dateTomorrow,
                timeNow,
                false
        );
        assertThrows(NurseSchedException.class,
                () -> Task.markTask(2));
    }

    @Test
    public void markTask_taskAlreadyMarked_throwsNurseSchedException() throws NurseSchedException {
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);
        LocalTime timeNow = LocalTime.now();
        Task.getTaskList().clear();
        //Add a marked task which is due 24 hours later
        Task.addTask(
                "Prepare medication for Jean",
                dateTomorrow,
                timeNow,
                true
        );
        assertThrows(NurseSchedException.class,
                () -> Task.markTask(1));
    }

    //Tests related to unmarking of tasks
    @Test
    public void unmarkTask_validIndex_taskUnmarked() throws NurseSchedException {
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);
        LocalTime timeNow = LocalTime.now();
        Task.getTaskList().clear();

        //Adds a marked task which is due 24 hours later
        Task.addTask(
                "Prepare medication for Jean",
                dateTomorrow,
                timeNow,
                true
        );
        Task.unmarkTask(1);
        assertFalse(Task.getTaskList().get(0).getIsDone());
    }

    @Test
    public void unmarkTask_zeroOrNegativeIndex_throwsAssertionError()
            throws NurseSchedException {
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);
        LocalTime timeNow = LocalTime.now();
        Task.getTaskList().clear();
        //Add a task which is due 24 hours later
        Task.addTask(
                "Prepare medication for Jean",
                dateTomorrow,
                timeNow,
                false
        );
        assertThrows(AssertionError.class,
                () -> Task.unmarkTask(0));
        assertThrows(AssertionError.class,
                () -> Task.unmarkTask(-1));
    }

    @Test
    public void unmarkTask_taskAlreadyUnmarked_throwsNurseSchedException() throws NurseSchedException {
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);
        LocalTime timeNow = LocalTime.now();
        Task.getTaskList().clear();
        //Adds an unmarked task which is due 24 hours later
        Task.addTask(
                "Prepare medication for Jean",
                dateTomorrow,
                timeNow,
                false
        );
        assertThrows(NurseSchedException.class,
                () -> Task.unmarkTask(1));
    }

    @Test
    public void unmarkTask_indexOutOfBounds_throwsNurseSchedException()
            throws NurseSchedException {
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);
        LocalTime timeNow = LocalTime.now();
        Task.getTaskList().clear();

        //Add a task which is due 24 hours later
        Task.addTask(
                "Prepare medication for Jean",
                dateTomorrow,
                timeNow,
                false
        );
        assertThrows(NurseSchedException.class,
                () -> Task.markTask(2));
    }

    //Tests for editing a task
    @Test
    public void editTask_validInputs_taskEdited() throws NurseSchedException {
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);
        LocalTime timeNow = LocalTime.now();
        LocalDate dateAfterTomorrow = dateTomorrow.plusDays(1);
        LocalTime oneHourAfterTimeNow = LocalTime.now().plusHours(1);
        Task.getTaskList().clear();
        Task.addTask(
                "Prepare medication for Jean",
                dateTomorrow,
                timeNow,
                false
        );
        Task.editTask(1, "Prepare medication for John", dateAfterTomorrow, oneHourAfterTimeNow);
        Task task = Task.getTaskList().get(0);
        assertEquals("Prepare medication for John", task.getDescription());
        assertEquals(dateAfterTomorrow, task.getByDate());
        assertEquals(oneHourAfterTimeNow, task.getByTime());
    }

    @Test
    public void editTask_onlyEditDescription_taskEdited() throws NurseSchedException {
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);
        LocalTime timeNow = LocalTime.now();
        Task.getTaskList().clear();
        Task.addTask(
                "Prepare medication for Jean",
                dateTomorrow,
                timeNow,
                false
        );
        Task.editTask(1, "Prepare medication for John", null, null);
        Task task = Task.getTaskList().get(0);
        assertEquals("Prepare medication for John", task.getDescription());
        assertEquals(dateTomorrow, task.getByDate());
        assertEquals(timeNow, task.getByTime());
    }

    @Test
    public void editTask_onlyEditDate_taskEdited() throws NurseSchedException {
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);
        LocalDate dateAfterTomorrow = dateTomorrow.plusDays(1);
        LocalTime timeNow = LocalTime.now();
        Task.getTaskList().clear();
        Task.addTask(
                "Prepare medication for Jean",
                dateTomorrow,
                timeNow,
                false
        );
        Task.editTask(1, "", dateAfterTomorrow, null);
        Task task = Task.getTaskList().get(0);
        assertEquals("Prepare medication for Jean", task.getDescription());
        assertEquals(dateAfterTomorrow, task.getByDate());
        assertEquals(timeNow, task.getByTime());
    }

    @Test
    public void editTask_onlyEditTime_taskEdited() throws NurseSchedException {
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);
        LocalTime timeNow = LocalTime.now();
        LocalTime oneHourAfterTimeNow = LocalTime.now().plusHours(1);
        Task.getTaskList().clear();
        Task.addTask(
                "Prepare medication for Jean",
                dateTomorrow,
                timeNow,
                false
        );
        Task.editTask(1, "", null, oneHourAfterTimeNow);
        Task task = Task.getTaskList().get(0);
        assertEquals("Prepare medication for Jean", task.getDescription());
        assertEquals(dateTomorrow, task.getByDate());
        assertEquals(oneHourAfterTimeNow, task.getByTime());
    }

    //Tests for deleting a task
    @Test
    public void deleteTask_validIndex_taskDeleted() throws NurseSchedException {
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);
        LocalTime timeNow = LocalTime.now();
        Task.getTaskList().clear();
        Task.addTask(
                "Prepare medication for Jean",
                dateTomorrow,
                timeNow,
                false
        );
        Task.deleteTask(1);
        assertEquals(0, Task.getTaskList().size());
    }

    @Test
    public void deleteTask_zeroOrNegativeIndex_throwsAssertionError() throws NurseSchedException {
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);
        LocalTime timeNow = LocalTime.now();
        Task.getTaskList().clear();
        Task.addTask(
                "Prepare medication for Jean",
                dateTomorrow,
                timeNow,
                false
        );
        assertThrows(AssertionError.class,
                () -> Task.deleteTask(0));
        assertThrows(AssertionError.class,
                () -> Task.deleteTask(-1));
    }

    @Test
    public void deleteTask_indexOutOfBounds_throwsNurseSchedException() throws NurseSchedException {
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);
        LocalTime timeNow = LocalTime.now();
        Task.getTaskList().clear();
        Task.addTask(
                "Prepare medication for Jean",
                dateTomorrow,
                timeNow,
                false
        );
        assertThrows(NurseSchedException.class,
                () -> Task.deleteTask(10));
    }
}
