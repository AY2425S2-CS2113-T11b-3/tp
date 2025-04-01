package seedu.nursesched.parser;

import org.junit.jupiter.api.Test;
import seedu.nursesched.exception.NurseSchedException;

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
    public void getAddTaskParser_missingInputs_throwsNurseSchedException() {
        String dateTmr = LocalDate.now().plusDays(1).toString();
        String input1 = "td/Prepare bed for Patient John t/13:00";
        String input2 = "d/" + dateTmr + " t/13:00";
        String input3 = "td/Update medicine supply" + dateTmr;
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
    public void getMarkUnmarkTaskParser_validInputs_taskIndexParsed() throws NurseSchedException {
        String index = "10";
        TaskParser taskParser = TaskParser.getMarkUnmarkTaskParser(index, "mark");
        assert taskParser != null;
        assertEquals(10, taskParser.getTaskIndex());
    }

    @Test
    public void getMarkUnmarkTaskParser_missingInputs_throwsNurseSchedException() {
        String input = "";
        assertThrows(NurseSchedException.class,
                () -> TaskParser.getMarkUnmarkTaskParser(input, "mark"));
    }

    @Test
    public void getMarkUnmarkTaskParser_negativeIndex_throwsNurseSchedException() {
        String input = "-1";
        assertThrows(NurseSchedException.class,
                () -> TaskParser.getMarkUnmarkTaskParser(input, "mark"));
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
}
