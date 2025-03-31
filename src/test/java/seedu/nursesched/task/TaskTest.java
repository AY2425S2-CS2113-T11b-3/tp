package seedu.nursesched.task;

import org.junit.jupiter.api.Test;
import seedu.nursesched.exception.NurseSchedException;
import seedu.nursesched.parser.TaskParser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskTest {
    //Tests related to adding of tasks
    @Test
    public void addTask_validInputs_taskAdded() throws NurseSchedException {
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);
        LocalTime timeNow = LocalTime.now();
        Task.taskList.clear();

        //Add a task which is due 24 hours later
        Task.addTask(
                "Prepare medication for Jean",
                dateTomorrow,
                timeNow,
                false
        );

        Task task = Task.taskList.get(0);
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

    @Test
    public void addTask_missingParameters_throwsNurseSchedException() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        String dateTomorrow = LocalDate.now().plusDays(1).format(dateFormatter);
        String timeNow = LocalTime.now().format(timeFormatter);
        Task.taskList.clear();
        String input1 = "task add d/" + dateTomorrow + " t/" + timeNow;
        String input2 = "task add td/Register new dr t/" + timeNow;
        String input3 = "task add td/Register new dr d/" + dateTomorrow;

        assertThrows(NurseSchedException.class,
                () -> TaskParser.extractInputs(input1));
        assertThrows(NurseSchedException.class,
                () -> TaskParser.extractInputs(input2));
        assertThrows(NurseSchedException.class,
                () -> TaskParser.extractInputs(input3));
    }

    //Tests related to marking of tasks
    @Test
    public void markTask_validIndex_taskMarked() throws NurseSchedException {
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);
        LocalTime timeNow = LocalTime.now();
        Task.taskList.clear();

        //Add a task which is due 24 hours later
        Task.addTask(
                "Prepare medication for Jean",
                dateTomorrow,
                timeNow,
                false
        );
        Task.markTask(1);
        assertTrue(Task.taskList.get(0).getIsDone());
    }

    @Test
    public void markTask_invalidIndex_throwsIndexOutOfBoundsException()
            throws NurseSchedException {
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);
        LocalTime timeNow = LocalTime.now();
        Task.taskList.clear();

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
    public void markTaskParser_invalidStringInput_throwsNumberFormatException()
            throws NurseSchedException {
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);
        LocalTime timeNow = LocalTime.now();
        Task.taskList.clear();

        //Add a task which is due 24 hours later
        Task.addTask(
                "Prepare medication for Jean",
                dateTomorrow,
                timeNow,
                false
        );
        String input = "task mark one";
        assertThrows(NurseSchedException.class,
                () -> TaskParser.extractInputs(input));
    }

    //Tests related to unmarking of tasks
    @Test
    public void unmarkTask_validIndex_taskMarked() throws NurseSchedException {
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);
        LocalTime timeNow = LocalTime.now();
        Task.taskList.clear();

        //Add a task which is due 24 hours later
        Task.addTask(
                "Prepare medication for Jean",
                dateTomorrow,
                timeNow,
                false
        );
        Task.unmarkTask(1);
        assertFalse(Task.taskList.get(0).getIsDone());
    }

    @Test
    public void unmarkTask_invalidIndex_throwsIndexOutOfBoundsException()
            throws NurseSchedException {
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);
        LocalTime timeNow = LocalTime.now();
        Task.taskList.clear();

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
    public void unmarkTaskParser_invalidStringInput_throwsNumberFormatException()
            throws NurseSchedException {
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);
        LocalTime timeNow = LocalTime.now();
        Task.taskList.clear();

        //Add a task which is due 24 hours later
        Task.addTask(
                "Prepare medication for Jean",
                dateTomorrow,
                timeNow,
                false
        );
        String input = "task unmark one";
        assertThrows(NurseSchedException.class,
                () -> TaskParser.extractInputs(input));
    }

    //Tests related to editing tasks
    @Test
    public void editTask_validInputs_taskEdited() throws NurseSchedException {
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);
        LocalTime timeNow = LocalTime.now();
        LocalDate dateAfterTomorrow = dateTomorrow.plusDays(1);
        LocalTime oneHourAfterTimeNow = LocalTime.now().plusHours(1);
        Task.taskList.clear();
        Task.addTask(
                "Prepare medication for Jean",
                dateTomorrow,
                timeNow,
                false
        );
        Task.editTask(1, "Prepare medication for John", dateAfterTomorrow, oneHourAfterTimeNow);
        Task task = Task.taskList.get(0);
        assertEquals("Prepare medication for John", task.getDescription());
        assertEquals(dateAfterTomorrow, task.getByDate());
        assertEquals(oneHourAfterTimeNow, task.getByTime());
    }

    @Test
    public void editTask_onlyEditDescription_taskEdited() throws NurseSchedException {
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);
        LocalTime timeNow = LocalTime.now();
        Task.taskList.clear();
        Task.addTask(
                "Prepare medication for Jean",
                dateTomorrow,
                timeNow,
                false
        );
        Task.editTask(1, "Prepare medication for John", null, null);
        Task task = Task.taskList.get(0);
        assertEquals("Prepare medication for John", task.getDescription());
        assertEquals(dateTomorrow, task.getByDate());
        assertEquals(timeNow, task.getByTime());
    }

    @Test
    public void editTask_onlyEditDate_taskEdited() throws NurseSchedException {
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);
        LocalDate dateAfterTomorrow = dateTomorrow.plusDays(1);
        LocalTime timeNow = LocalTime.now();
        Task.taskList.clear();
        Task.addTask(
                "Prepare medication for Jean",
                dateTomorrow,
                timeNow,
                false
        );
        Task.editTask(1, "", dateAfterTomorrow, null);
        Task task = Task.taskList.get(0);
        assertEquals("Prepare medication for Jean", task.getDescription());
        assertEquals(dateAfterTomorrow, task.getByDate());
        assertEquals(timeNow, task.getByTime());
    }

    @Test
    public void editTask_onlyEditTime_taskEdited() throws NurseSchedException {
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);
        LocalTime timeNow = LocalTime.now();
        LocalTime oneHourAfterTimeNow = LocalTime.now().plusHours(1);
        Task.taskList.clear();
        Task.addTask(
                "Prepare medication for Jean",
                dateTomorrow,
                timeNow,
                false
        );
        Task.editTask(1, "", null, oneHourAfterTimeNow);
        Task task = Task.taskList.get(0);
        assertEquals("Prepare medication for Jean", task.getDescription());
        assertEquals(dateTomorrow, task.getByDate());
        assertEquals(oneHourAfterTimeNow, task.getByTime());
    }

    @Test
    public void editTaskParser_invalidTaskIndex_throwsNurseSchedException() throws NurseSchedException {
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);
        LocalTime timeNow = LocalTime.now();
        Task.taskList.clear();

        //Add a task which is due 24 hours later
        Task.addTask(
                "Prepare medication for Jean",
                dateTomorrow,
                timeNow,
                false
        );
        String newDescription = "Prepare medication for John";
        assertThrows(NurseSchedException.class,
                () -> Task.editTask(2, newDescription, null, null));
        assertThrows(AssertionError.class,
                () -> Task.editTask(-1, newDescription, null, null));
        assertThrows(AssertionError.class,
                () -> Task.editTask(0, newDescription, null, null));
    }

    @Test
    public void editTaskParser_missingInputs_throwsNurseSchedException() throws NurseSchedException {
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);
        LocalTime timeNow = LocalTime.now();
        Task.taskList.clear();

        //Add a task which is due 24 hours later
        Task.addTask(
                "Prepare medication for Jean",
                dateTomorrow,
                timeNow,
                false
        );
        String input1 = "task edit";
        String input2 = "task edit id/1 td/";
        String input3 = "task edit id/1 t/";
        String input4 = "task edit td/ t/";
        assertThrows(NurseSchedException.class,
                () -> TaskParser.extractInputs(input1));
        assertThrows(NurseSchedException.class,
                () -> TaskParser.extractInputs(input2));
        assertThrows(NurseSchedException.class,
                () -> TaskParser.extractInputs(input3));
        assertThrows(NurseSchedException.class,
                () -> TaskParser.extractInputs(input4));
    }
}
