package seedu.nursesched.task;

import org.junit.jupiter.api.Test;
import seedu.nursesched.exception.NurseSchedException;
import seedu.nursesched.parser.TaskParser;

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
    public void addTask_missingParameters_throwsNurseSchedException() {
        Task.taskList.clear();
        String input1 = "task add d/2026-02-02 t/13:00";
        String input2 = "task add td/Register new dr t/13:00";
        String input3 = "task add td/Register new dr d/2026-02-02";

        assertThrows(NurseSchedException.class,
                () -> TaskParser.extractInputs(input1));
        assertThrows(NurseSchedException.class,
                () -> TaskParser.extractInputs(input2));
        assertThrows(NurseSchedException.class,
                () -> TaskParser.extractInputs(input3));
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
    public void markTaskParser_invalidStringInput_throwsNumberFormatException()
            throws NurseSchedException {
        Task.taskList.clear();
        Task.addTask(
                "Prepare medication for Jean",
                LocalDate.of(2026, 2, 14),
                LocalTime.of(13, 0),
                false
        );
        String input = "task mark one";
        assertThrows(NurseSchedException.class,
                () -> TaskParser.extractInputs(input));
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

    @Test
    public void unmarkTaskParser_invalidStringInput_throwsNumberFormatException()
            throws NurseSchedException {
        Task.taskList.clear();
        Task.addTask(
                "Prepare medication for Jean",
                LocalDate.of(2026, 2, 14),
                LocalTime.of(13, 0),
                false
        );
        String input = "task unmark one";
        assertThrows(NurseSchedException.class,
                () -> TaskParser.extractInputs(input));
    }
}
