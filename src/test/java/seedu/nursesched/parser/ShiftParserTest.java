package seedu.nursesched.parser;

import org.junit.jupiter.api.Test;
import seedu.nursesched.exception.NurseSchedException;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ShiftParserTest {

    @Test
    void testExtractInputs_addCommand_validInput() throws NurseSchedException {
        String input = "shift add s/08:00 e/10:00 d/2025-04-02 st/Prepare room";
        ShiftParser parser = ShiftParser.extractInputs(input);

        assertNotNull(parser);
        assertEquals("add", parser.getCommand());
        assertEquals(LocalTime.parse("08:00"), parser.getStartTime());
        assertEquals(LocalTime.parse("10:00"), parser.getEndTime());
        assertEquals(LocalDate.parse("2025-04-02"), parser.getDate());
        assertEquals("prepare room", parser.getShiftTask());
    }

    @Test
    void testExtractInputs_delCommand_validInput() throws NurseSchedException {
        String input = "shift del id/1";
        ShiftParser parser = ShiftParser.extractInputs(input);

        assertNotNull(parser);
        assertEquals("del", parser.getCommand());
        assertEquals(0, parser.getIndex()); // index is 0-based
    }

    @Test
    void testExtractInputs_markCommand_validInput() throws NurseSchedException {
        String input = "shift mark id/2";
        ShiftParser parser = ShiftParser.extractInputs(input);

        assertNotNull(parser);
        assertEquals("mark", parser.getCommand());
        assertEquals(1, parser.getIndex());
    }

    @Test
    void testExtractInputs_unmarkCommand_validInput() throws NurseSchedException {
        String input = "shift unmark id/2";
        ShiftParser parser = ShiftParser.extractInputs(input);

        assertNotNull(parser);
        assertEquals("unmark", parser.getCommand());
        assertEquals(1, parser.getIndex());
    }

    @Test
    void testExtractInputs_listCommand() throws NurseSchedException {
        String input = "shift list";
        ShiftParser parser = ShiftParser.extractInputs(input);

        assertNotNull(parser);
        assertEquals("list", parser.getCommand());
    }

    @Test
    void testExtractInputs_editCommand_validInput() throws NurseSchedException {
        String input = "shift edit id/1 s/09:00 e/11:00 d/2025-04-03 st/clean room";
        ShiftParser parser = ShiftParser.extractInputs(input);

        assertNotNull(parser);
        assertEquals("edit", parser.getCommand());
        assertEquals(0, parser.getIndex());
        assertEquals(LocalTime.of(9, 0), parser.getStartTime());
        assertEquals(LocalTime.of(11, 0), parser.getEndTime());
        assertEquals(LocalDate.of(2025, 4, 3), parser.getDate());
        assertEquals("clean room", parser.getShiftTask());
    }


    @Test
    void testExtractInputs_addCommand_missingFields() {
        String input = "shift add s/09:00 e/10:00 d/2025-04-02";
        assertThrows(NurseSchedException.class, () -> ShiftParser.extractInputs(input));
    }

    @Test
    void testExtractInputs_editCommand_invalidIndex() {
        String input = "shift edit id/abc s/09:00 e/10:00 d/2025-04-02 st/Test";
        assertThrows(NurseSchedException.class, () -> ShiftParser.extractInputs(input));
    }

    @Test
    void testExtractInputs_invalidCommand() {
        String input = "shift explode";
        assertThrows(NurseSchedException.class, () -> ShiftParser.extractInputs(input));
    }

    @Test
    void testExtractInputs_emptyInput() {
        String input = "";
        assertThrows(NurseSchedException.class, () -> ShiftParser.extractInputs(input));
    }
}
