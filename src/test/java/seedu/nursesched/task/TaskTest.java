package seedu.nursesched.task;

import org.junit.jupiter.api.Test;
import seedu.nursesched.exception.NurseSchedException;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskTest {
    @Test
    public void addTask_validInputs_taskAdded() throws NurseSchedException {
        Task.taskList.clear();
        Task.addTask(
                "Prepare medication for Jean",
                LocalDate.of(2026, 2, 14),
                LocalTime.of(13, 0),
                false
        );
        Task task = Task.taskList.get(0);
        assertEquals("Prepare medication for Jean", task.getDescription());
        assertEquals(LocalDate.of(2026, 2, 14), task.getByDate());
        assertEquals(LocalTime.of(13, 0), task.getByTime());
        assertFalse(task.getIsDone());
    }

    @Test
    public void addTask_invalidDate_throwsNurseSchedException() {
        assertThrows(NurseSchedException.class,
                () -> Task.addTask(
                        "Prepare medication for Jean",
                        LocalDate.of(2025, 3, 14),
                        LocalTime.of(13, 0),
                        false
                        )
        );
    }

    @Test
    public void markTask_validIndex_taskMarked() throws NurseSchedException {
        Task.taskList.clear();
        Task.addTask(
            "Prepare medication for Jean",
            LocalDate.of(2026, 2, 14),
            LocalTime.of(13, 0),
            false
        );
        Task.markTask(1);
        assertTrue(Task.taskList.get(0).getIsDone());
    }

    @Test
    public void markTask_invalidIndex_throwsIndexOutOfBoundsException()
            throws NurseSchedException {
        Task.taskList.clear();
        Task.addTask(
                "Prepare medication for Jean",
                LocalDate.of(2026, 2, 14),
                LocalTime.of(13, 0),
                false
        );
        assertThrows(NurseSchedException.class,
                () -> Task.markTask(2));
    }

    @Test
    public void unmarkTask_validIndex_taskMarked() throws NurseSchedException {
        Task.taskList.clear();
        Task.addTask(
            "Prepare medication for Jean",
            LocalDate.of(2026, 2, 14),
            LocalTime.of(13, 0),
            true
        );
        Task.unmarkTask(1);
        assertFalse(Task.taskList.get(0).getIsDone());
    }

    @Test
    public void unmarkTask_invalidIndex_throwsIndexOutOfBoundsException()
            throws NurseSchedException {
        Task.taskList.clear();
        Task.addTask(
                "Prepare medication for Jean",
                LocalDate.of(2026, 2, 14),
                LocalTime.of(13, 0),
                true
        );
        assertThrows(NurseSchedException.class,
                () -> Task.markTask(2));
    }
}
