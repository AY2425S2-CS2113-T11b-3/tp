package seedu.nursesched.appointment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.nursesched.exception.NurseSchedException;
import seedu.nursesched.parser.ApptParser;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AppointmentTest {
    @BeforeEach
    void setUp() {
        Appointment.apptList = new ArrayList<>();  // Reset appointment list
    }

    @Test
    void testAddAppt_apptAddedToList() throws NurseSchedException {
        String input = "appt add p/Jean doe s/13:00 e/14:00 d/2026-02-15 n/Needs a wheelchair";
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

        String expected = "Name: jean doe, From: 15:00, To: 16:00, Date: 2026-02-15, Importance: MEDIUM, Notes: needs a wheelchair";
        assertEquals(expected, Appointment.apptList.get(Appointment.apptList.size()-1).toString());
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
}
