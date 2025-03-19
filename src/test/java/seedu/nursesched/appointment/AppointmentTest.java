package seedu.nursesched.appointment;

import org.junit.jupiter.api.Test;
import seedu.nursesched.exception.NurseSchedException;
import seedu.nursesched.parser.ApptParser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AppointmentTest {

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
                apptParser.getNotes()
        );

        Appointment appointment = Appointment.apptList.get(0);
        assertEquals("jean doe", appointment.getName());
        assertEquals("13:00", appointment.getStartTime());
        assertEquals("14:00", appointment.getEndTime());
        assertEquals("2026-02-15", appointment.getDate());
        assertEquals("needs a wheelchair", appointment.getNotes());
    }

    @Test
    void testToString_AppointmentAsString() throws NurseSchedException {
        String input = "appt add p/Jean doe s/15:00 e/16:00 d/2026-02-15 n/Needs a wheelchair";
        ApptParser apptParser = ApptParser.extractInputs(input);

        assertNotNull(apptParser);

        Appointment.addAppt(
                apptParser.getName(),
                apptParser.getStartTime(),
                apptParser.getEndTime(),
                apptParser.getDate(),
                apptParser.getNotes()
        );

        String expected = "Name: jean doe, From: 15:00, To: 16:00, Date: 2026-02-15, Notes: needs a wheelchair";
        assertEquals(expected, Appointment.apptList.get(1).toString());
    }
}
