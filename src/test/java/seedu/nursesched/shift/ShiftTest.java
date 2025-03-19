package seedu.nursesched.shift;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.nursesched.exception.NurseSchedException;
import seedu.nursesched.parser.ShiftParser;

class ShiftTest {
    private final List<Shift> shiftList = new ArrayList<>();

    @Test
    void addShift_shiftListAdd_expectCorrectOutput() throws NurseSchedException {
        String inputString = "shift add s/10:00 e/11:00 d/2004-01-01 st/test";
        ShiftParser shiftParser = ShiftParser.extractInputs(inputString);
        assertNotNull(shiftParser);

        shiftList.add(new Shift(shiftParser.getStartTime(), shiftParser.getEndTime(), shiftParser.getDate(),
                shiftParser.getNotes()));

        LocalTime startTime = shiftParser.getStartTime();
        LocalTime endTime = shiftParser.getEndTime();
        LocalDate date = shiftParser.getDate();
        String shiftTask = String.valueOf(shiftParser.getNotes());

        assertEquals(1, shiftList.size());
        Shift addedShift = shiftList.get(0);
        assertEquals(startTime, addedShift.getStartTime());
        assertEquals(endTime, addedShift.getEndTime());
        assertEquals(date, addedShift.getDate());
        assertEquals(shiftTask, addedShift.getShiftTask());
    }

    @Test
    void deleteShiftByIndex() throws NurseSchedException {
        String inputStringDelete = "shift del sn/1";
        String inputStringAdd = "shift add s/10:00 e/11:00 d/2004-01-01 st/test";

        ShiftParser shiftParserAdd = ShiftParser.extractInputs(inputStringAdd);
        assertNotNull(shiftParserAdd);

        shiftList.add(new Shift(shiftParserAdd.getStartTime(), shiftParserAdd.getEndTime(), shiftParserAdd.getDate(),
                shiftParserAdd.getNotes()));

        assertEquals(1, shiftList.size());

        ShiftParser shiftParserDelete = ShiftParser.extractInputs(inputStringDelete);
        shiftList.remove(shiftParserDelete.getIndex());

        assertEquals(0, shiftList.size());
    }

    @Test
    void listShifts() {
    }

    @Test
    void testToString() throws NurseSchedException {
        String inputString = "shift add s/10:00 e/11:00 d/2004-01-01 st/test";

        ShiftParser shiftParserAdd = ShiftParser.extractInputs(inputString);
        assertNotNull(shiftParserAdd);

        shiftList.add(new Shift(shiftParserAdd.getStartTime(), shiftParserAdd.getEndTime(), shiftParserAdd.getDate(),
                shiftParserAdd.getNotes()));

        String expected = "From: 10:00, To: 11:00, Date: 2004-01-01, shiftTask: test";

        assertEquals(expected, shiftList.get(0).toString(), "Shift toString output is incorrect!");
    }
}
