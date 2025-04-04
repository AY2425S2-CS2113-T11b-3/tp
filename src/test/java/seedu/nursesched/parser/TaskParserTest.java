package seedu.nursesched.parser;

import org.junit.jupiter.api.Test;
import seedu.nursesched.exception.NurseSchedException;
import seedu.nursesched.task.Task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskParserTest {
    @Test
    public void getAddTaskParser_validInputs_taskParametersParsed() throws NurseSchedException {
        String dateTmr = LocalDate.now().plusDays(1).toString();
        String timeNow = LocalTime.now().toString();
        String input = "td/Prepare bed for Patient John "
                + "d/"
                + dateTmr
                + " t/" + timeNow;
        TaskParser taskParser = TaskParser.getAddTaskParser(input, "add");
        assertEquals("Prepare bed for Patient John", taskParser.getDescription());
        assertEquals(dateTmr, taskParser.getByDate().toString());
        assertEquals(timeNow, taskParser.getByTime().toString());
    }

    @Test
    public void addTaskParser_missingParameters_throwsNurseSchedException() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        String dateTomorrow = LocalDate.now().plusDays(1).format(dateFormatter);
        String timeNow = LocalTime.now().format(timeFormatter);
        Task.getTaskList().clear();
        //Missing description
        String input1 = "task add d/" + dateTomorrow + " t/" + timeNow;
        //Missing date
        String input2 = "task add td/Register new dr t/" + timeNow;
        //Missing time
        String input3 = "task add td/Register new dr d/" + dateTomorrow;

        assertThrows(NurseSchedException.class,
                () -> TaskParser.getAddTaskParser(input1, "add"));
        assertThrows(NurseSchedException.class,
                () -> TaskParser.getAddTaskParser(input2, "add"));
        assertThrows(NurseSchedException.class,
                () -> TaskParser.getAddTaskParser(input3, "add"));
    }

    @Test
    public void getAddTaskParser_invalidDateFormat_throwsNurseSchedException() {
        LocalDate date = LocalDate.now().plusDays(1);
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateTmr1 = date.format(formatter1);
        String dateTmr2 = date.format(formatter2);
        String timeNow = LocalTime.now().toString();
        String input1 = "td/Prepare bed for Patient John d/" + dateTmr1 + " t/" + timeNow;
        String input2 = "td/Prepare bed for Patient John d/" + dateTmr2 + " t/" + timeNow;
        assertThrows(NurseSchedException.class,
                () -> TaskParser.getAddTaskParser(input1, "add"));
        assertThrows(NurseSchedException.class,
                () -> TaskParser.getAddTaskParser(input2, "add"));
    }

    @Test
    public void getAddTaskParser_invalidTimeFormat_throwsNurseSchedException() {
        LocalTime time = LocalTime.now();
        String dateTmr = LocalDate.now().plusDays(1).toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String timeNow = time.format(formatter);
        String input = "td/Prepare bed for Patient John d/" + dateTmr + " t/" + timeNow;
        assertThrows(NurseSchedException.class,
                () -> TaskParser.getAddTaskParser(input, "add"));
    }

    @Test
    public void getIndexParser_validInputs_returnsIndexParser() throws NurseSchedException {
        String input1 = "task mark id/1";
        String input2 = "task unmark id/1";
        String input3 = "task del id/1";
        TaskParser taskParser1 = TaskParser.getIndexParser(input1, "mark");
        TaskParser taskParser2 = TaskParser.getIndexParser(input2, "unmark");
        TaskParser taskParser3 = TaskParser.getIndexParser(input3, "del");
        assert taskParser1 != null;
        assert taskParser2 != null;
        assert taskParser3 != null;
        assertEquals(1, taskParser1.getTaskIndex());
        assertEquals(1, taskParser2.getTaskIndex());
        assertEquals(1, taskParser3.getTaskIndex());
    }

    @Test
    public void getIndexParser_missingInputs_throwsIndexOutOfBoundsException() {
        String input1 = "mark 1";
        String input2 = "mark id/";
        assertThrows(NurseSchedException.class,
                () -> TaskParser.getIndexParser(input1, "mark"));
        assertThrows(NurseSchedException.class,
                () -> TaskParser.getIndexParser(input2, "mark"));
    }

    @Test
    public void getIndexParser_negativeIndex_throwsNurseSchedException() {
        String input = "-1";
        assertThrows(NurseSchedException.class,
                () -> TaskParser.getIndexParser(input, "mark"));
    }

    @Test
    public void markTaskParser_invalidStringInput_throwsNumberFormatException()
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
        String input = "task mark id/one";
        assertThrows(NurseSchedException.class,
                () -> TaskParser.getIndexParser(input, "mark"));
    }

    @Test
    public void unmarkTaskParser_invalidStringInput_throwsNumberFormatException()
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
        String input = "task unmark id/one";
        assertThrows(NurseSchedException.class,
                () -> TaskParser.getIndexParser(input, "unmark"));
    }

    @Test
    public void getEditTaskParser_validInputs_newTaskDetailsParsed() throws NurseSchedException {
        String dateTmr = LocalDate.now().plusDays(1).toString();
        String timeNow = LocalTime.now().toString();
        String line = "id/1 td/Get ready to see Patient John d/" + dateTmr + " t/" + timeNow;
        TaskParser taskParser = TaskParser.getEditTaskParser(line, "edit");
        assertEquals("Get ready to see Patient John", taskParser.getDescription());
        assertEquals(dateTmr, taskParser.getByDate().toString());
        assertEquals(timeNow, taskParser.getByTime().toString());
    }

    @Test
    public void editTaskParser_invalidTaskIndex_throwsNurseSchedException() throws NurseSchedException {
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
        String input1 = "task edit id/-1 td/Prepare medication for John";
        String input2 = "task edit id/0 td/Prepare medication for John";

        assertThrows(NurseSchedException.class,
                () -> TaskParser.getEditTaskParser(input1, "edit"));
        assertThrows(NurseSchedException.class,
                () -> TaskParser.getEditTaskParser(input2, "edit"));
    }

    @Test
    public void editTaskParser_missingInputs_throwsNurseSchedException() throws NurseSchedException {
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
        String input = "task edit";
        assertThrows(NurseSchedException.class,
                () -> TaskParser.getEditTaskParser(input, "edit"));
    }

    @Test
    public void getEditTaskParser_invalidDateFormat_throwsNurseSchedException() {
        LocalDate date = LocalDate.now().plusDays(1);
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateTmr1 = date.format(formatter1);
        String dateTmr2 = date.format(formatter2);
        String timeNow = LocalTime.now().toString();
        String input1 = "td/Prepare bed for Patient John d/" + dateTmr1 + " t/" + timeNow;
        String input2 = "td/Prepare bed for Patient John d/" + dateTmr2 + " t/" + timeNow;
        assertThrows(NurseSchedException.class,
                () -> TaskParser.getEditTaskParser(input1, "add"));
        assertThrows(NurseSchedException.class,
                () -> TaskParser.getAddTaskParser(input2, "add"));
    }

    @Test
    public void getEditTaskParser_invalidTimeFormat_throwsNurseSchedException() {
        LocalTime time = LocalTime.now();
        String dateTmr = LocalDate.now().plusDays(1).toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String timeNow = time.format(formatter);
        String input = "td/Prepare bed for Patient John d/" + dateTmr + " t/" + timeNow;
        assertThrows(NurseSchedException.class,
                () -> TaskParser.getEditTaskParser(input, "add"));
    }

    @Test
    public void findTaskParser_validInputs_keywordParsed() throws NurseSchedException {
        String input = "task find td/Jean";
        TaskParser taskParser = TaskParser.getFindTaskParser(input, "find");
        assertEquals("Jean", taskParser.getDescription());
    }

    @Test
    public void findTaskParser_missingFields_throwsNurseSchedException() {
        String input = "task find Jean";
        assertThrows(NurseSchedException.class,
                () -> TaskParser.getFindTaskParser(input, "find"));
    }

    @Test
    public void deleteTaskParser_invalidStringInput_throwsNumberFormatException()
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
        String input = "task del id/one";
        assertThrows(NurseSchedException.class,
                () -> TaskParser.getIndexParser(input, "del"));
    }
}
