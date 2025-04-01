package seedu.nursesched.appointment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.nursesched.exception.NurseSchedException;
import seedu.nursesched.parser.ApptParser;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AppointmentTest {
    @BeforeEach
    void setUp() {
        Appointment.apptList = new ArrayList<>();  // Reset appointment list
    }

    // Tests related to appt add feature
    @Test
    void testAddAppt_apptAddedToList() throws NurseSchedException {
        String input = "appt add p/Jean doe s/13:00 e/14:00 d/2026-02-15 im/2 n/Needs a wheelchair";
        ApptParser apptParser = ApptParser.extractInputs(input);

        assertNotNull(apptParser);

        Appointment.addAppt(
                apptParser.getName(),
                apptParser.getStartTime(),
                apptParser.getEndTime(),
                apptParser.getDate(),
                apptParser.getNotes(),
                apptParser.getImportance()
        );


        Appointment appointment = Appointment.apptList.get(Appointment.apptList.size()-1);
        assertEquals("jean doe", appointment.getName());
        assertEquals("13:00", appointment.getStartTime());
        assertEquals("14:00", appointment.getEndTime());
        assertEquals("2026-02-15", appointment.getDate());
        assertEquals("needs a wheelchair", appointment.getNotes());
        assertEquals(2, appointment.getImportance());
    }

    @Test
    void testToString_appointmentAsString() throws NurseSchedException {
        String input = "appt add p/Jean doe s/15:00 e/16:00 d/2026-02-15 im/2 n/Needs a wheelchair";
        ApptParser apptParser = ApptParser.extractInputs(input);

        assertNotNull(apptParser);

        Appointment.addAppt(
                apptParser.getName(),
                apptParser.getStartTime(),
                apptParser.getEndTime(),
                apptParser.getDate(),
                apptParser.getNotes(),
                apptParser.getImportance()
        );

        String expected = "Name: jean doe, From: 15:00, To: 16:00, Date: 2026-02-15, " +
                "Importance: MEDIUM, Notes: needs a wheelchair";
        assertEquals(expected, Appointment.apptList.get(Appointment.apptList.size()-1).toString());
    }

    @Test
    public void testAddAppt_missingParameters_shouldThrowException() {
        String input1 = "appt add s/15:00 e/16:00 d/2026-02-15 im/2 n/Needs a wheelchair";      // Missing name
        String input2 = "appt add p/Jean doe e/16:00 d/2026-02-15 im/2 n/Needs a wheelchair";   //Missing start time
        String input3 = "appt add s/15:00 d/2026-02-15 im/2 n/Needs a wheelchair";              // Missing end time
        String input4 = "appt add p/Jean doe s/15:00 e/16:00 im/2 n/Needs a wheelchair";        // Missing date

        assertThrows(NurseSchedException.class,
                () -> ApptParser.extractInputs(input1));
        assertThrows(NurseSchedException.class,
                () -> ApptParser.extractInputs(input2));
        assertThrows(NurseSchedException.class,
                () -> ApptParser.extractInputs(input3));
        assertThrows(NurseSchedException.class,
                () -> ApptParser.extractInputs(input4));

    }
    @Test
    void testAddAppt_invalidTimeFormat_shouldThrowException() {
        String input = "appt add p/Jean Doe s/25:00 e/26:00 d/2026-02-15 im/1 n/Invalid time";

        try {
            ApptParser.extractInputs(input);
            throw new AssertionError("Expected NurseSchedException to be thrown");
        } catch (NurseSchedException e) {
            assertEquals("Invalid date or time format! Input date as YYYY-MM-DD, input time as HH:mm", e.getMessage());
        }
    }

    @Test
    void testAddAppt_invalidDateFormat_shouldThrowException() {
        String input = "appt add p/Jean Doe s/10:00 e/11:00 d/15-02-2026 im/1 n/Invalid date";
        try {
            ApptParser.extractInputs(input);
            throw new AssertionError("Expected NurseSchedException to be thrown");
        } catch (NurseSchedException e) {
            assertEquals("Invalid date or time format! Input date as YYYY-MM-DD, input time as HH:mm", e.getMessage());
        }
    }

    @Test
    void testAddAppt_duplicateAppointments_shouldNotBeAdded() throws NurseSchedException {
        String input1 = "appt add p/Jean Doe s/13:00 e/14:00 d/2026-02-15 im/2 n/First appointment";
        ApptParser apptParser1 = ApptParser.extractInputs(input1);

        assertNotNull(apptParser1);

        Appointment.addAppt(
                apptParser1.getName(),
                apptParser1.getStartTime(),
                apptParser1.getEndTime(),
                apptParser1.getDate(),
                apptParser1.getNotes(),
                apptParser1.getImportance()
        );

        String input2 = "appt add p/John Doe s/13:00 e/14:00 d/2026-02-15 im/2 n/Conflicting appointment";
        ApptParser apptParser2 = ApptParser.extractInputs(input2);

        assertNotNull(apptParser2);

        int sizeBefore = Appointment.apptList.size();

        Appointment.addAppt(
                apptParser2.getName(),
                apptParser2.getStartTime(),
                apptParser2.getEndTime(),
                apptParser2.getDate(),
                apptParser2.getNotes(),
                apptParser2.getImportance()
        );

        int sizeAfter = Appointment.apptList.size();
        assertEquals(sizeBefore, sizeAfter, "Appointment with clashing start time should not be added.");
    }



    //Tests related to editing tasks
    @Test
    public void editAppt_validInputs_apptEdited() throws NurseSchedException {

        String input = "appt add p/Jean doe s/15:00 e/16:00 d/2026-02-15 im/2 n/Needs a wheelchair";
        ApptParser apptParser = ApptParser.extractInputs(input);
        Appointment.addAppt(
                apptParser.getName(),
                apptParser.getStartTime(),
                apptParser.getEndTime(),
                apptParser.getDate(),
                apptParser.getNotes(),
                apptParser.getImportance()
        );

        Appointment.editAppt(0, "edited name",
                LocalTime.parse("16:00"), LocalTime.parse("17:00"),
                LocalDate.parse("2026-03-15"), "edited note", 3);

        Appointment appt = Appointment.apptList.get(0);

        assertEquals("edited name", appt.getName());
        assertEquals("16:00", appt.getStartTime());
        assertEquals("17:00", appt.getEndTime());
        assertEquals("2026-03-15", appt.getDate());
        assertEquals(3, appt.getImportance());
        assertEquals("edited note", appt.getNotes());
    }

    @Test
    public void editAppt_onlyEditname_apptEdited() throws NurseSchedException {

        String input = "appt add p/Jean doe s/15:00 e/16:00 d/2026-02-15 im/2 n/Needs a wheelchair";
        ApptParser apptParser = ApptParser.extractInputs(input);
        Appointment.addAppt(
                apptParser.getName(),
                apptParser.getStartTime(),
                apptParser.getEndTime(),
                apptParser.getDate(),
                apptParser.getNotes(),
                apptParser.getImportance()
        );

        Appointment.editAppt(0, "edited name",
                null, null,
                null, null, -1);

        Appointment appt = Appointment.apptList.get(0);

        assertEquals("edited name", appt.getName());
        assertEquals("15:00", appt.getStartTime());
        assertEquals("16:00", appt.getEndTime());
        assertEquals("2026-02-15", appt.getDate());
        assertEquals(2, appt.getImportance());
        assertEquals("needs a wheelchair", appt.getNotes());
    }

    @Test
    public void editAppt_onlyEditDate_apptEdited() throws NurseSchedException {
        String input = "appt add p/Jean doe s/15:00 e/16:00 d/2026-02-15 im/2 n/Needs a wheelchair";
        ApptParser apptParser = ApptParser.extractInputs(input);
        Appointment.addAppt(
                apptParser.getName(),
                apptParser.getStartTime(),
                apptParser.getEndTime(),
                apptParser.getDate(),
                apptParser.getNotes(),
                apptParser.getImportance()
        );

        Appointment.editAppt(0, null,
                null, null,
                LocalDate.parse("2026-03-15"), null, -1);

        Appointment appt = Appointment.apptList.get(0);

        assertEquals("jean doe", appt.getName());
        assertEquals("15:00", appt.getStartTime());
        assertEquals("16:00", appt.getEndTime());
        assertEquals("2026-03-15", appt.getDate());
        assertEquals(2, appt.getImportance());
        assertEquals("needs a wheelchair", appt.getNotes());
    }

    @Test
    public void editAppt_onlyEditStartTime_apptEdited() throws NurseSchedException {
        String input = "appt add p/Jean doe s/15:00 e/16:00 d/2026-02-15 im/2 n/Needs a wheelchair";
        ApptParser apptParser = ApptParser.extractInputs(input);
        Appointment.addAppt(
                apptParser.getName(),
                apptParser.getStartTime(),
                apptParser.getEndTime(),
                apptParser.getDate(),
                apptParser.getNotes(),
                apptParser.getImportance()
        );

        Appointment.editAppt(0, null,
                LocalTime.parse("14:00"), null,
                null, null, -1);

        Appointment appt = Appointment.apptList.get(0);

        assertEquals("jean doe", appt.getName());
        assertEquals("14:00", appt.getStartTime());
        assertEquals("16:00", appt.getEndTime());
        assertEquals("2026-02-15", appt.getDate());
        assertEquals(2, appt.getImportance());
        assertEquals("needs a wheelchair", appt.getNotes());
    }

    @Test
    public void editAppt_onlyEditEndTime_apptEdited() throws NurseSchedException {
        String input = "appt add p/Jean doe s/15:00 e/16:00 d/2026-02-15 im/2 n/Needs a wheelchair";
        ApptParser apptParser = ApptParser.extractInputs(input);
        Appointment.addAppt(
                apptParser.getName(),
                apptParser.getStartTime(),
                apptParser.getEndTime(),
                apptParser.getDate(),
                apptParser.getNotes(),
                apptParser.getImportance()
        );

        Appointment.editAppt(0, null,
                null, LocalTime.parse("17:00"),
                null, null, -1);

        Appointment appt = Appointment.apptList.get(0);

        assertEquals("jean doe", appt.getName());
        assertEquals("15:00", appt.getStartTime());
        assertEquals("17:00", appt.getEndTime());
        assertEquals("2026-02-15", appt.getDate());
        assertEquals(2, appt.getImportance());
        assertEquals("needs a wheelchair", appt.getNotes());
    }

    @Test
    public void editAppt_onlyEditNotes_apptEdited() throws NurseSchedException {
        String input = "appt add p/Jean doe s/15:00 e/16:00 d/2026-02-15 im/2 n/Needs a wheelchair";
        ApptParser apptParser = ApptParser.extractInputs(input);
        Appointment.addAppt(
                apptParser.getName(),
                apptParser.getStartTime(),
                apptParser.getEndTime(),
                apptParser.getDate(),
                apptParser.getNotes(),
                apptParser.getImportance()
        );

        Appointment.editAppt(0, null,
                null, null,
                null, "edited note", -1);

        Appointment appt = Appointment.apptList.get(0);

        assertEquals("jean doe", appt.getName());
        assertEquals("15:00", appt.getStartTime());
        assertEquals("16:00", appt.getEndTime());
        assertEquals("2026-02-15", appt.getDate());
        assertEquals(2, appt.getImportance());
        assertEquals("edited note", appt.getNotes());
    }

    @Test
    public void editAppt_onlyEditImportance_apptEdited() throws NurseSchedException {
        String input = "appt add p/Jean doe s/15:00 e/16:00 d/2026-02-15 im/2 n/Needs a wheelchair";
        ApptParser apptParser = ApptParser.extractInputs(input);
        Appointment.addAppt(
                apptParser.getName(),
                apptParser.getStartTime(),
                apptParser.getEndTime(),
                apptParser.getDate(),
                apptParser.getNotes(),
                apptParser.getImportance()
        );

        Appointment.editAppt(0, null,
                null, null,
                null, null, 1);

        Appointment appt = Appointment.apptList.get(0);

        assertEquals("jean doe", appt.getName());
        assertEquals("15:00", appt.getStartTime());
        assertEquals("16:00", appt.getEndTime());
        assertEquals("2026-02-15", appt.getDate());
        assertEquals(1, appt.getImportance());
        assertEquals("needs a wheelchair", appt.getNotes());
    }
    @Test
    public void editAppt_invalidTaskIndex_throwsNurseSchedException() throws NurseSchedException {
        String input = "appt add p/Jean doe s/15:00 e/16:00 d/2026-02-15 im/2 n/Needs a wheelchair";
        ApptParser apptParser = ApptParser.extractInputs(input);
        Appointment.addAppt(
                apptParser.getName(),
                apptParser.getStartTime(),
                apptParser.getEndTime(),
                apptParser.getDate(),
                apptParser.getNotes(),
                apptParser.getImportance()
        );

        assertThrows(NurseSchedException.class,
                () -> Appointment.editAppt(2,null,
                        null, null,
                        null, null, 1));
        assertThrows(NurseSchedException.class,
                () -> Appointment.editAppt(-1,null,
                        null, null,
                        null, null, 1));

    }

    @Test
    public void editAppt_missingInputs_throwsNurseSchedException() {
        String input1 = "appt edit ";
        String input2 = "appt edit 1 ";

        // Expect extractInputs(input1) to throw NurseSchedException
        Exception exception1 = assertThrows(NurseSchedException.class, () -> {
            ApptParser.extractInputs(input1);
        });
        assertEquals("Invalid appointment edit format! Input as: appt edit [INDEX] [p/PATIENT_NAME]" +
                " [s/START_TIME] [e/END_TIME] [d/DATE] [im/IMPORTANCE] [n/NOTES]", exception1.getMessage());

        // Expect extractInputs(input2) to also throw NurseSchedException
        Exception exception2 = assertThrows(NurseSchedException.class, () -> {
            ApptParser.extractInputs(input2);
        });
        assertEquals("Invalid appointment edit format! Input as: appt edit [INDEX] [p/PATIENT_NAME]" +
                " [s/START_TIME] [e/END_TIME] [d/DATE] [im/IMPORTANCE] [n/NOTES]", exception2.getMessage());
    }


}
