package seedu.nursesched.shift;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.nursesched.exception.NurseSchedException;
import seedu.nursesched.parser.ShiftParser;
import seedu.nursesched.storage.ShiftStorage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShiftTest {
    static ArrayList<Shift> initialShiftList;
    private final List<Shift> shiftList = new ArrayList<>();

    @BeforeAll
    public static void saveInitialList() {
        initialShiftList = Shift.shiftList;
    }

    @AfterAll
    public static void restoreInitialList() {
        ShiftStorage.overwriteSaveFile(initialShiftList);
    }

    @BeforeEach
    void setUp() {
        Shift.shiftList = new ArrayList<>();
    }

    @Test
    void addShift_shiftListAdd_expectCorrectOutput() throws NurseSchedException {
        String inputString = "shift add s/10:00 e/11:00 d/2004-01-01 st/test";
        ShiftParser shiftParser = ShiftParser.extractInputs(inputString);
        assertNotNull(shiftParser);

        shiftList.add(new Shift(
                shiftParser.getStartTime(),
                shiftParser.getEndTime(),
                shiftParser.getDate(),
                shiftParser.getShiftTask()
        ));

        LocalTime startTime = shiftParser.getStartTime();
        LocalTime endTime = shiftParser.getEndTime();
        LocalDate date = shiftParser.getDate();
        String shiftTask = String.valueOf(shiftParser.getShiftTask());

        assertEquals(1, shiftList.size());
        Shift addedShift = shiftList.get(0);
        assertEquals(startTime, addedShift.getStartTime());
        assertEquals(endTime, addedShift.getEndTime());
        assertEquals(date, addedShift.getDate());
        assertEquals(shiftTask, addedShift.getShiftTask());
    }

    @Test
    void deleteShiftByIndex() throws NurseSchedException {
        String inputStringDelete = "shift del id/1";
        String inputStringAdd = "shift add s/10:00 e/11:00 d/2004-01-01 st/test";

        ShiftParser shiftParserAdd = ShiftParser.extractInputs(inputStringAdd);
        assertNotNull(shiftParserAdd);

        shiftList.add(new Shift(
                shiftParserAdd.getStartTime(),
                shiftParserAdd.getEndTime(),
                shiftParserAdd.getDate(),
                shiftParserAdd.getShiftTask()
        ));

        assertEquals(1, shiftList.size());

        ShiftParser shiftParserDelete = ShiftParser.extractInputs(inputStringDelete);
        shiftList.remove(shiftParserDelete.getIndex());

        assertEquals(0, shiftList.size());
    }

    @Test
    void listShifts_emptyList_printsNoShiftsMessage() {
        Shift.getShiftList().clear();
        Shift.listShifts();
    }

    @Test
    void getShiftList_containsAddedShift() throws NurseSchedException {
        Shift.getShiftList().clear();

        LocalDate futureDate = LocalDate.now().plusDays(1);

        Shift.addShift(
                LocalTime.of(8, 0),
                LocalTime.of(10, 0),
                futureDate,
                "Routine check"
        );

        ArrayList<Shift> list = Shift.getShiftList();
        assertEquals(1, list.size());
        assertEquals("Routine check", list.get(0).getShiftTask());
    }

    @Test
    void testToString() throws NurseSchedException {
        String inputString = "shift add s/10:00 e/11:00 d/2004-01-01 st/test";

        ShiftParser shiftParserAdd = ShiftParser.extractInputs(inputString);
        assertNotNull(shiftParserAdd);

        shiftList.add(new Shift(
                shiftParserAdd.getStartTime(),
                shiftParserAdd.getEndTime(),
                shiftParserAdd.getDate(),
                shiftParserAdd.getShiftTask()
        ));

        String expected = "[ ] From: 10:00, To: 11:00, Date: 2004-01-01, shiftTask: test";

        assertEquals(expected, shiftList.get(0).toString(), "Shift toString output is incorrect!");
    }

    @Test
    void markAndUnmarkShift_statusChangesCorrectly() throws NurseSchedException {
        ShiftParser parser = ShiftParser.extractInputs("shift add s/10:00 e/11:00 d/2004-01-01 st/test");
        Shift shift = new Shift(
                parser.getStartTime(),
                parser.getEndTime(),
                parser.getDate(),
                parser.getShiftTask()
        );
        shiftList.add(shift);

        shift.setDone(true);
        assertEquals(true, shift.getStatus(), "Shift should be marked as done");

        shift.setDone(false);
        assertEquals(false, shift.getStatus(), "Shift should be unmarked (not done)");
    }

    @Test
    void shiftGetters_returnCorrectValues() throws NurseSchedException {
        ShiftParser parser = ShiftParser.extractInputs("shift add s/08:00 e/10:00 d/2004-01-01 st/rounds");
        Shift shift = new Shift(
                parser.getStartTime(),
                parser.getEndTime(),
                parser.getDate(),
                parser.getShiftTask()
        );

        assertEquals(LocalTime.of(8, 0), shift.getStartTime());
        assertEquals(LocalTime.of(10, 0), shift.getEndTime());
        assertEquals(LocalDate.of(2004, 1, 1), shift.getDate());
        assertEquals("rounds", shift.getShiftTask());
        assertEquals(false, shift.getStatus());
    }

    @Test
    void editShift_validIndex_shiftUpdatedCorrectly() throws NurseSchedException {
        Shift.getShiftList().clear();

        LocalTime originalStart = LocalTime.of(10, 0);
        LocalTime originalEnd = LocalTime.of(11, 0);
        LocalDate originalDate = LocalDate.now().plusDays(1);
        String originalTask = "Initial shift";

        Shift.addShift(originalStart, originalEnd, originalDate, originalTask);
        assertEquals(1, Shift.getShiftList().size());

        LocalTime newStart = LocalTime.of(12, 0);
        LocalTime newEnd = LocalTime.of(13, 0);
        LocalDate newDate = LocalDate.now().plusDays(2);
        String newTask = "Updated shift";

        Shift.editShift(0, newStart, newEnd, newDate, newTask);

        Shift editedShift = Shift.getShiftList().get(0);
        assertEquals(newStart, editedShift.getStartTime());
        assertEquals(newEnd, editedShift.getEndTime());
        assertEquals(newDate, editedShift.getDate());
        assertEquals(newTask, editedShift.getShiftTask());
    }

    @Test
    void sortShiftsChronologically_shiftsSortedCorrectly() throws NurseSchedException {
        Shift.getShiftList().clear();

        LocalDate date1 = LocalDate.now().plusDays(1);
        LocalDate date2 = LocalDate.now().plusDays(2);

        Shift.addShift(LocalTime.of(10, 0), LocalTime.of(12, 0), date2, "Late shift");
        Shift.addShift(LocalTime.of(8, 0), LocalTime.of(9, 0), date1, "Early shift");
        Shift.addShift(LocalTime.of(9, 0), LocalTime.of(10, 0), date2, "Mid shift");

        Shift.sortShiftsChronologically();

        assertEquals("Early shift", Shift.getShiftList().get(0).getShiftTask());
        assertEquals("Mid shift", Shift.getShiftList().get(1).getShiftTask());
        assertEquals("Late shift", Shift.getShiftList().get(2).getShiftTask());
    }

    @Test
    void sortShiftsChronologically_emptyList_throwsException() throws NurseSchedException {
        Shift.getShiftList().clear();

        NurseSchedException thrown = assertThrows(
                NurseSchedException.class,
                Shift::sortShiftsChronologically,
                "Expected sortShiftsChronologically() to throw"
        );

        assertEquals("List is empty. Nothing to sort.", thrown.getMessage());
    }

    @Test
    void logOvertime_validInput_logsCorrectly() throws NurseSchedException {
        Shift.getShiftList().clear();

        LocalDate futureDate = LocalDate.now().plusDays(1);

        Shift.addShift(
                LocalTime.of(10, 0),
                LocalTime.of(12, 0),
                futureDate,
                "Shift with OT"
        );

        Shift.logOvertime(0, 2.5);
        double loggedHours = Shift.getShiftList().get(0).getOvertimeHours();
        assertEquals(2.5, loggedHours);
    }

    @Test
    void markShift_alreadyMarked_shouldGiveWarningMessage() throws NurseSchedException {
        Shift.getShiftList().clear();

        // Add a new shift and mark it
        Shift.addShift(
                LocalTime.of(14, 0),
                LocalTime.of(18, 0),
                LocalDate.now().plusDays(1),
                "Test shift"
        );
        Shift.markShift(0); // mark once

        // Try to mark it again
        Shift.markShift(0);  // Expected: warning message, no change
        assertEquals(true, Shift.getShiftList().get(0).getStatus());
    }

    @Test
    void unmarkShift_alreadyUnmarked_shouldGiveWarningMessage() throws NurseSchedException {
        Shift.getShiftList().clear();

        Shift.addShift(
                LocalTime.of(9, 0),
                LocalTime.of(12, 0),
                LocalDate.now().plusDays(1),
                "Test shift"
        );

        // Try to unmark an already unmarked shift
        Shift.unmarkShift(0);  // Expected: warning message, no change
        assertEquals(false, Shift.getShiftList().get(0).getStatus());
    }

    @Test
    void editShift_setToPastDate_shouldNotUpdate() throws NurseSchedException {
        Shift.getShiftList().clear();

        Shift.addShift(
                LocalTime.of(9, 0),
                LocalTime.of(11, 0),
                LocalDate.now().plusDays(1),
                "Future shift"
        );

        LocalTime newStart = LocalTime.of(8, 0);
        LocalTime newEnd = LocalTime.of(10, 0);
        LocalDate newDate = LocalDate.of(2000, 1, 1); // Invalid past date
        String newTask = "Past edit attempt";

        assertThrows(NurseSchedException.class, () ->
                Shift.editShift(0, newStart, newEnd, newDate, newTask)
        );

        // Verify the original data is unchanged
        Shift edited = Shift.getShiftList().get(0);
        assertEquals(LocalDate.now().plusDays(1), edited.getDate());
        assertEquals("Future shift", edited.getShiftTask());
    }

    @Test
    void editShift_preservesOvertimeHours() throws NurseSchedException {
        Shift.getShiftList().clear();

        Shift.addShift(
                LocalTime.of(9, 0),
                LocalTime.of(11, 0),
                LocalDate.now().plusDays(1),
                "Original task"
        );

        Shift.logOvertime(0, 2.5); // Add overtime

        // Edit task with overtime hours
        Shift.editShift(
                0,
                LocalTime.of(10, 0),
                LocalTime.of(12, 0),
                LocalDate.now().plusDays(1),
                "Updated task"
        );

        Shift shift = Shift.getShiftList().get(0);
        assertEquals("Updated task", shift.getShiftTask());
        assertEquals(2.5, shift.getOvertimeHours()); // Still there
    }

    @Test
    void addShift_overlappingWithExisting_shouldThrowException() throws NurseSchedException {
        Shift.getShiftList().clear();

        // Add first shift
        Shift.addShift(
                LocalTime.of(8, 0),
                LocalTime.of(10, 0),
                LocalDate.now().plusDays(1),
                "Morning shift"
        );

        // Overlapping shift
        assertThrows(NurseSchedException.class, () ->
                Shift.addShift(
                        LocalTime.of(9, 0),
                        LocalTime.of(11, 0),
                        LocalDate.now().plusDays(1),
                        "Overlapping shift"
                )
        );

        // Only one shift should exist
        assertEquals(1, Shift.getShiftList().size());
        assertEquals("Morning shift", Shift.getShiftList().get(0).getShiftTask());
    }

    @Test
    void addShift_adjacentToExisting_shouldSucceed() throws NurseSchedException {
        Shift.getShiftList().clear();

        LocalDate date = LocalDate.now().plusDays(1);
        Shift.addShift(LocalTime.of(8, 0), LocalTime.of(10, 0), date, "Morning shift");
        Shift.addShift(LocalTime.of(10, 0), LocalTime.of(12, 0), date, "Midday shift");

        assertEquals(2, Shift.getShiftList().size());
    }

    @Test
    void addShift_startAfterEnd_shouldThrowException() {
        Shift.getShiftList().clear();

        LocalDate date = LocalDate.now().plusDays(1);
        LocalTime start = LocalTime.of(12, 0);
        LocalTime end = LocalTime.of(10, 0); // Invalid: start > end

        assertThrows(NurseSchedException.class, () ->
                Shift.addShift(start, end, date, "Invalid time range")
        );
    }

    @Test
    void editShift_setStartAfterEnd_shouldThrowException() throws NurseSchedException {
        Shift.getShiftList().clear();

        // Add a valid shift first
        Shift.addShift(
                LocalTime.of(9, 0),
                LocalTime.of(11, 0),
                LocalDate.now().plusDays(1),
                "Initial shift"
        );

        // Now try to edit it to an invalid time
        LocalTime newStart = LocalTime.of(12, 0);
        LocalTime newEnd = LocalTime.of(10, 0); // Invalid range

        assertThrows(NurseSchedException.class, () ->
                Shift.editShift(0, newStart, newEnd, LocalDate.now().plusDays(1), "Updated task")
        );
    }
}
