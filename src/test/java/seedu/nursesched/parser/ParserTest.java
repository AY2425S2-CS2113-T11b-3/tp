package seedu.nursesched.parser;

import seedu.nursesched.exception.NurseSchedException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ParserTest {

    @Test
    public void testExtractInputs_appointmentUnmarkCommand() throws NurseSchedException {
        String input = "appt unmark id/1";
        ApptParser apptParser = ApptParser.extractInputs(input);

        assertNotNull(apptParser);
        assertEquals("unmark", apptParser.getCommand());
        assertEquals(0, apptParser.getIndex());
    }

    @Test
    public void testExtractInputs_appointmentInvalidCommand() throws NurseSchedException {
        String input = "appt invalid";
        ApptParser apptParser = ApptParser.extractInputs(input);
        assertNull(apptParser);
    }

    @Test
    public void testExtractInputs_appointmentMissingParameters() {
        String input = "appt add p/Jean Doe s/13:00";

        assertThrows(NurseSchedException.class, () -> {
            ApptParser.extractInputs(input);
        });
    }

    @Test
    public void testExtractInputs_appointmentAddCommand() throws NurseSchedException {
        String input = "appt add p/Jean doe s/13:00 e/14:00 d/2025-02-15 n/Needs a wheelchair. Very annoying!";
        ApptParser apptParser = ApptParser.extractInputs(input);

        assertNotNull(apptParser);
        assertEquals("add", apptParser.getCommand());
        assertEquals("Jean doe", apptParser.getName());
        assertEquals(LocalTime.parse("13:00"), apptParser.getStartTime());
        assertEquals(LocalTime.parse("14:00"), apptParser.getEndTime());
        assertEquals(LocalDate.parse("2025-02-15"), apptParser.getDate());
        assertEquals("Needs a wheelchair. Very annoying!", apptParser.getNotes());
    }

    @Test
    public void testExtractInputs_appointmentDeleteCommand() throws NurseSchedException {
        String input = "appt del id/1";
        ApptParser apptParser = ApptParser.extractInputs(input);

        assertNotNull(apptParser);
        assertEquals("del", apptParser.getCommand());
        assertEquals(0, apptParser.getIndex());
    }

    @Test
    public void testExtractInputs_appointmentMarkCommand() throws NurseSchedException {
        String input = "appt mark id/1";
        ApptParser apptParser = ApptParser.extractInputs(input);

        assertNotNull(apptParser);
        assertEquals("mark", apptParser.getCommand());
        assertEquals(0, apptParser.getIndex());
    }

    @Test
    public void testExtractInputs_appointmentInvalidTime() {
        String input1 = "appt add p/Jean doe s/1pm e/2pm d/2025-02-15 n/Needs wheelchair.";
        String input2 = "appt del p/Jean doe s/1300 d/2025-02-15";
        String input3 = "appt mark p/Jean doe s/24:00 d/2025-02-15";

        assertThrows(NurseSchedException.class, () -> ApptParser.extractInputs(input1));
        assertThrows(NurseSchedException.class, () -> ApptParser.extractInputs(input2));
        assertThrows(NurseSchedException.class, () -> ApptParser.extractInputs(input3));
    }

    @Test
    public void testExtractInputs_appointmentInvalidDate() {
        String input1 = "appt add p/Jean doe s/15:00 e/17:00 d/05 June 2025 n/Needs wheelchair.";
        String input2 = "appt del p/Jean doe s/15:00 d/12-02-2025";
        String input3 = "appt mark p/Jean doe s/15:00 d/2025-15-02";

        assertThrows(NurseSchedException.class, () -> ApptParser.extractInputs(input1));
        assertThrows(NurseSchedException.class, () -> ApptParser.extractInputs(input2));
        assertThrows(NurseSchedException.class, () -> ApptParser.extractInputs(input3));
    }

    @Test
    public void testExtractInputs_appointmentNoNotes() throws NurseSchedException {
        String input = "appt add p/Jean Doe s/13:00 e/14:00 d/2025-02-15 n/";
        ApptParser apptParser = ApptParser.extractInputs(input);

        assertNotNull(apptParser);
        assertEquals("add", apptParser.getCommand());
        assertEquals("Jean Doe", apptParser.getName());
        assertEquals(LocalTime.parse("13:00"), apptParser.getStartTime());
        assertEquals(LocalTime.parse("14:00"), apptParser.getEndTime());
        assertEquals(LocalDate.parse("2025-02-15"), apptParser.getDate());
        assertEquals("", apptParser.getNotes());
    }

    @Test
    public void testExtractInputs_caseInsensitiveAddCommand() throws NurseSchedException {
        String input1 = "appt add p/Jean Doe s/13:00 e/14:00 d/2025-02-15 n/Lowercase";
        String input2 = "appt ADD p/Jean Doe s/13:00 e/14:00 d/2025-02-15 n/Uppercase";
        String input3 = "appt aDd p/Jean Doe s/13:00 e/14:00 d/2025-02-15 n/Mixed case";

        ApptParser apptParser1 = ApptParser.extractInputs(input1);
        ApptParser apptParser2 = ApptParser.extractInputs(input2);
        ApptParser apptParser3 = ApptParser.extractInputs(input3);

        assertNotNull(apptParser1);
        assertNotNull(apptParser2);
        assertNotNull(apptParser3);

        assertEquals("add", apptParser1.getCommand());
        assertEquals("add", apptParser2.getCommand());
        assertEquals("add", apptParser3.getCommand());
    }
}
