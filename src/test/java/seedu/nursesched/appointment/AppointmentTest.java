package seedu.nursesched.appointment;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.nursesched.exception.NurseSchedException;
import seedu.nursesched.parser.ApptParser;
import seedu.nursesched.patient.Patient;
import seedu.nursesched.storage.AppointmentStorage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AppointmentTest {
    static ArrayList<Appointment> initialApptList;

    @BeforeAll
    public static void saveInitialList() {
        initialApptList = Appointment.apptList;
    }

    @AfterAll
    public static void restoreInitialList() {
        AppointmentStorage.overwriteSaveFile(initialApptList);
    }

    @BeforeEach
    void setUp() {
        Appointment.apptList = new ArrayList<>();  // Reset appointment list
    }

    // Method for adding patient profile first before adding appointment
    void addAppointment(String input) throws NurseSchedException {

        ApptParser apptParser = ApptParser.extractInputs(input);

        Patient patient = new Patient("1804", "Jean Doe", "25",
                "F", "12345678", "Allergic to penicillin");
        Patient.addPatient(patient);

        assertNotNull(apptParser);
        Appointment.addAppt(
                apptParser.getID(),
                apptParser.getStartTime(),
                apptParser.getEndTime(),
                apptParser.getDate(),
                apptParser.getNotes(),
                apptParser.getImportance()
        );

        Patient.removePatient("1804");
    }

    void addApptEditTests(String input) throws NurseSchedException {

        ApptParser apptParser = ApptParser.extractInputs(input);

        Patient patient = new Patient("1804", "Jean Doe", "25",
                "F", "12345678", "Allergic to penicillin");
        Patient.addPatient(patient);

        assertNotNull(apptParser);
        Appointment.addAppt(
                apptParser.getID(),
                apptParser.getStartTime(),
                apptParser.getEndTime(),
                apptParser.getDate(),
                apptParser.getNotes(),
                apptParser.getImportance()
        );
    }

    // Tests related to appt add feature
    @Test
    void testAddAppt_apptAddedToList() throws NurseSchedException {
        String input = "appt add id/1804 s/13:00 e/14:00 d/2026-02-15 " +
                "im/2 n/Needs a wheelchair";

        addAppointment(input);

        Appointment appointment = Appointment.apptList.get(Appointment.apptList.size()-1);
        assertEquals("Jean Doe", appointment.getName());
        assertEquals("13:00", appointment.getStartTime());
        assertEquals("14:00", appointment.getEndTime());
        assertEquals("2026-02-15", appointment.getDate());
        assertEquals("Needs a wheelchair", appointment.getNotes());
        assertEquals(2, appointment.getImportance());

    }

    @Test
    void testAddAppt_notInPatientList_throwsNurseSchedException() throws NurseSchedException {
        String input = "appt add id/1804 s/13:00 e/14:00 d/2026-02-15 " +
                "im/2 n/First appointment";
        ApptParser apptParser = ApptParser.extractInputs(input);
        assertNotNull(apptParser);

        assertThrows(NurseSchedException.class, () -> Appointment.addAppt(
                apptParser.getID(),
                apptParser.getStartTime(),
                apptParser.getEndTime(),
                apptParser.getDate(),
                apptParser.getNotes(),
                apptParser.getImportance())
        );
    }

    @Test
    void testToString_appointmentAsString() throws NurseSchedException {
        String input = "appt add id/1804 s/15:00 e/16:00 d/2026-02-15 im/2 n/Needs a wheelchair";
        addAppointment(input);

        String expected = "ID: 1804, Name: Jean Doe, From: 15:00, To: 16:00, Date: 2026-02-15, " +
                "Importance: MEDIUM, Notes: Needs a wheelchair";
        assertEquals(expected, Appointment.apptList.get(Appointment.apptList.size()-1).toString());
    }

    @Test
    public void testAddAppt_missingParameters_shouldThrowException() {
        String input1 = "appt add s/15:00 e/16:00 d/2026-02-15 im/2 n/Needs a wheelchair";      // Missing ID
        String input2 = "appt add id/1804 e/16:00 d/2026-02-15 im/2 n/Needs a wheelchair";   //Missing start time
        String input3 = "appt add s/15:00 d/2026-02-15 im/2 n/Needs a wheelchair";              // Missing end time
        String input4 = "appt add id/1804 s/15:00 e/16:00 im/2 n/Needs a wheelchair";        // Missing date

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
        String input = "appt add id/1804 s/25:00 e/26:00 d/2026-02-15 im/1 n/Invalid time";

        try {
            ApptParser.extractInputs(input);
            throw new AssertionError("Expected NurseSchedException to be thrown");
        } catch (NurseSchedException e) {
            assertEquals("Invalid date or time format! " +
                    "Input date as YYYY-MM-DD, input time as HH:mm", e.getMessage());
        }
    }

    @Test
    void testAddAppt_invalidDateFormat_shouldThrowException() {
        String input = "appt add id/1804 s/10:00 e/11:00 d/15-02-2026 im/1 n/Invalid date";
        try {
            ApptParser.extractInputs(input);
            throw new AssertionError("Expected NurseSchedException to be thrown");
        } catch (NurseSchedException e) {
            assertEquals("Invalid date or time format! Input date as YYYY-MM-DD, " +
                    "input time as HH:mm", e.getMessage());
        }
    }

    @Test
    void testAddAppt_duplicateAppointments_shouldNotBeAdded() throws NurseSchedException {
        String input1 = "appt add id/1804 s/13:00 e/14:00 d/2026-02-15 " +
                "im/2 n/First appointment";
        addAppointment(input1);

        int sizeBefore = Appointment.apptList.size();

        String input2 = "appt add id/1804 s/13:00 e/14:00 d/2026-02-15 " +
                "im/2 n/Conflicting appointment";
        addAppointment(input2);

        int sizeAfter = Appointment.apptList.size();
        assertEquals(sizeBefore, sizeAfter,
                "Appointment with clashing start time should not be added.");
    }



    //Tests related to editing tasks
    @Test
    public void editAppt_validInputs_apptEdited() throws NurseSchedException {

        String input = "appt add id/1804 s/15:00 e/16:00 d/2026-02-15 " +
                "im/2 n/Needs a wheelchair";
        addApptEditTests(input);

        Appointment.editAppt(0, -1,
                LocalTime.parse("16:00"), LocalTime.parse("17:00"),
                LocalDate.parse("2026-03-15"), "edited note", 3);

        Appointment appt = Appointment.apptList.get(0);

        assertEquals(1804, appt.getID());
        assertEquals("16:00", appt.getStartTime());
        assertEquals("17:00", appt.getEndTime());
        assertEquals("2026-03-15", appt.getDate());
        assertEquals(3, appt.getImportance());
        assertEquals("edited note", appt.getNotes());
        Patient.removePatient("1804");
    }

    @Test
    public void editAppt_onlyEditDate_apptEdited() throws NurseSchedException {
        String input = "appt add id/1804 s/15:00 e/16:00 d/2026-02-15 " +
                "im/2 n/Needs a wheelchair";
        addApptEditTests(input);

        Appointment.editAppt(0, -1,
                null, null,
                LocalDate.parse("2026-03-15"), null, -1);

        Appointment appt = Appointment.apptList.get(0);

        assertEquals("Jean Doe", appt.getName());
        assertEquals("15:00", appt.getStartTime());
        assertEquals("16:00", appt.getEndTime());
        assertEquals("2026-03-15", appt.getDate());
        assertEquals(2, appt.getImportance());
        assertEquals("Needs a wheelchair", appt.getNotes());
        Patient.removePatient("1804");
    }

    @Test
    public void editAppt_onlyEditStartTime_apptEdited() throws NurseSchedException {
        String input = "appt add id/1804 s/15:00 e/16:00 d/2026-02-15 " +
                "im/2 n/Needs a wheelchair";
        addApptEditTests(input);

        Appointment.editAppt(0, -1,
                LocalTime.parse("14:00"), null,
                null, null, -1);

        Appointment appt = Appointment.apptList.get(0);

        assertEquals("Jean Doe", appt.getName());
        assertEquals("14:00", appt.getStartTime());
        assertEquals("16:00", appt.getEndTime());
        assertEquals("2026-02-15", appt.getDate());
        assertEquals(2, appt.getImportance());
        assertEquals("Needs a wheelchair", appt.getNotes());
        Patient.removePatient("1804");
    }

    @Test
    public void editAppt_onlyEditEndTime_apptEdited() throws NurseSchedException {
        String input = "appt add id/1804 s/15:00 e/16:00 d/2026-02-15 " +
                "im/2 n/Needs a wheelchair";
        addApptEditTests(input);

        Appointment.editAppt(0, -1,
                null, LocalTime.parse("17:00"),
                null, null, -1);

        Appointment appt = Appointment.apptList.get(0);

        assertEquals("Jean Doe", appt.getName());
        assertEquals("15:00", appt.getStartTime());
        assertEquals("17:00", appt.getEndTime());
        assertEquals("2026-02-15", appt.getDate());
        assertEquals(2, appt.getImportance());
        assertEquals("Needs a wheelchair", appt.getNotes());
        Patient.removePatient("1804");
    }

    @Test
    public void editAppt_onlyEditNotes_apptEdited() throws NurseSchedException {
        String input = "appt add id/1804 s/15:00 e/16:00 d/2026-02-15 " +
                "im/2 n/Needs a wheelchair";
        addApptEditTests(input);

        Appointment.editAppt(0, -1,
                null, null,
                null, "edited note", -1);

        Appointment appt = Appointment.apptList.get(0);

        assertEquals("Jean Doe", appt.getName());
        assertEquals("15:00", appt.getStartTime());
        assertEquals("16:00", appt.getEndTime());
        assertEquals("2026-02-15", appt.getDate());
        assertEquals(2, appt.getImportance());
        assertEquals("edited note", appt.getNotes());
        Patient.removePatient("1804");
    }

    @Test
    public void editAppt_onlyEditImportance_apptEdited() throws NurseSchedException {
        String input = "appt add id/1804 s/15:00 e/16:00 d/2026-02-15 " +
                "im/2 n/Needs a wheelchair";
        addApptEditTests(input);

        Appointment.editAppt(0, -1,
                null, null,
                null, null, 1);

        Appointment appt = Appointment.apptList.get(0);

        assertEquals("Jean Doe", appt.getName());
        assertEquals("15:00", appt.getStartTime());
        assertEquals("16:00", appt.getEndTime());
        assertEquals("2026-02-15", appt.getDate());
        assertEquals(1, appt.getImportance());
        assertEquals("Needs a wheelchair", appt.getNotes());
        Patient.removePatient("1804");
    }
    @Test
    public void editAppt_invalidTaskIndex_throwsNurseSchedException() throws NurseSchedException {
        String input = "appt add id/1804 s/15:00 e/16:00 d/2026-02-15 " +
                "im/2 n/Needs a wheelchair";
        addApptEditTests(input);

        assertThrows(NurseSchedException.class,
                () -> Appointment.editAppt(2,-1,
                        null, null,
                        null, null, 1));
        assertThrows(NurseSchedException.class,
                () -> Appointment.editAppt(-1,-1,
                        null, null,
                        null, null, 1));
        Patient.removePatient("1804");
    }

    @Test
    public void editAppt_missingInputs_throwsNurseSchedException() {
        String input1 = "appt edit ";
        String input2 = "appt edit id/1 ";

        Exception exception1 = assertThrows(NurseSchedException.class, () -> {
            ApptParser.extractInputs(input1);
        });
        assertEquals("Invalid appointment edit format! Input as: appt edit aid/APPT_INDEX " +
                        "[id/PATIENT_ID] [s/START_TIME] [e/END_TIME] [d/DATE] [im/IMPORTANCE] [n/NOTES]",
                exception1.getMessage());

        Exception exception2 = assertThrows(NurseSchedException.class, () -> {
            ApptParser.extractInputs(input2);
        });
        assertEquals("Invalid appointment edit format! Input as: appt edit aid/APPT_INDEX " +
                        "[id/PATIENT_ID] [s/START_TIME] [e/END_TIME] [d/DATE] [im/IMPORTANCE] [n/NOTES]",
                exception2.getMessage());
    }

    @Test
    public void sortApptByTime_apptListSorted() throws NurseSchedException {
        String input1 = "appt add id/1804 s/15:00 e/16:00 d/2026-02-15 " +
                "im/2 n/Needs a wheelchair";
        addAppointment(input1);
        String input2 = "appt add id/1804 s/10:00 e/11:00 d/2026-02-15 " +
                "im/2 n/Needs a wheelchair";
        addAppointment(input2);

        Appointment.sortByTime();
        Appointment appt1 = Appointment.apptList.get(0);
        Appointment appt2 = Appointment.apptList.get(1);
        assertEquals("10:00", appt1.getStartTime());
        assertEquals("11:00", appt1.getEndTime());
        assertEquals("15:00", appt2.getStartTime());
        assertEquals("16:00", appt2.getEndTime());
    }

    @Test
    public void sortApptByImportance_apptListSorted() throws NurseSchedException {
        String input1 = "appt add id/1804 s/15:00 e/16:00 d/2026-02-15 " +
                "im/1 n/Needs a wheelchair";
        addAppointment(input1);
        String input2 = "appt add id/1804 s/10:00 e/11:00 d/2026-02-15 " +
                "im/3 n/Needs a wheelchair";
        addAppointment(input2);

        Appointment.sortByImportance();
        Appointment appt1 = Appointment.apptList.get(0);
        Appointment appt2 = Appointment.apptList.get(1);
        assertEquals(3, appt1.getImportance());
        assertEquals(1, appt2.getImportance());

    }


    @Test
    public void sortApptEmptyList_throwsNurseSchedException()  {
        assertThrows(NurseSchedException.class,
                Appointment::sortByTime);
        assertThrows(NurseSchedException.class,
                Appointment::sortByImportance);
    }

    @Test
    public void sortApptInvalidBy_throwsNurseSchedException() {
        String input1 = "appt sort by/asdasds";
        String input2 = "appt sort by/";
        assertThrows(NurseSchedException.class,
                () -> ApptParser.extractInputs(input1));
        assertThrows(NurseSchedException.class,
                () -> ApptParser.extractInputs(input2));
    }

}
