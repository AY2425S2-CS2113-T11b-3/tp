package seedu.nursesched.patient;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.nursesched.exception.NurseSchedException;
import seedu.nursesched.storage.PatientStorage;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PatientTest {
    static ArrayList<Patient> initialPatientList;

    @BeforeAll
    public static void saveInitialPatientList() {
        initialPatientList = Patient.patientsList;
    }

    @AfterAll
    public static void restoreFinalPatientList() {
        PatientStorage.overwriteSaveFile(initialPatientList);
    }

    @BeforeEach
    void setUp() {
        Patient.getPatientsList().clear();
    }

    @Test
    void constructor_validPatient_success() throws NurseSchedException {
        Patient patient = new Patient("1234", "John Doe", "30", "M", "12345678", "Allergic to penicillin");
        assertEquals("1234", patient.getId());
        assertEquals("John Doe", patient.getName());
        assertEquals("30", patient.getAge());
        assertEquals("M", patient.getGender());
        assertEquals("12345678", patient.getContact());
        assertEquals("Allergic to penicillin", patient.getNotes());
    }

    @Test
    void constructor_emptyFields_throwsException() {
        Exception exception = assertThrows(NurseSchedException.class, () ->
                new Patient("1234", "", "30", "M", "12345678", ""));
        assertEquals("Some patient fields are empty!", exception.getMessage());
    }

    @Test
    void constructor_validGenderLowerCase_success() throws NurseSchedException {
        Patient patient = new Patient("1234", "John Doe", "30", "m", "12345678", "Allergic to penicillin");
        assertEquals("1234", patient.getId());
        assertEquals("John Doe", patient.getName());
        assertEquals("30", patient.getAge());
        assertEquals("M", patient.getGender());
        assertEquals("12345678", patient.getContact());
        assertEquals("Allergic to penicillin", patient.getNotes());
    }

    @Test
    void constructor_invalidGender_throwsException() {
        Exception exception = assertThrows(NurseSchedException.class, () ->
                new Patient("1234", "John Doe", "30", "X", "12345678", ""));
        assertEquals("Gender must be either 'M' or 'F'!", exception.getMessage());
    }

    @Test
    void constructor_duplicateId_throwsException() throws NurseSchedException {
        Patient.addPatient(new Patient("1234", "John Doe", "30", "M", "12345678", ""));
        Exception exception = assertThrows(NurseSchedException.class, () ->
                Patient.addPatient(new Patient("1234", "Jane Doe", "25", "F", "87654321", "")));
        assertEquals("Patient ID already exist!", exception.getMessage());
    }

    @Test
    void constructor_invalidAge_throwsException() {
        Exception exception = assertThrows(NurseSchedException.class, () ->
                new Patient("1234", "John Doe", "-5", "M", "12345678", ""));
        assertEquals("Patient age cannot be negative!", exception.getMessage());

        exception = assertThrows(NurseSchedException.class, () ->
                new Patient("1234", "John Doe", "126", "M", "12345678", ""));
        assertEquals("Patient age cannot be greater than the maximum age!", exception.getMessage());

        exception = assertThrows(NurseSchedException.class, () ->
                new Patient("1234", "John Doe", "thirty", "M", "12345678", ""));
        assertEquals("Patient age must not contain any non-digits or spaces!", exception.getMessage());
    }

    @Test
    void constructor_invalidContact_throwsException() {
        Exception exception = assertThrows(NurseSchedException.class, () ->
                new Patient("1234", "John Doe", "30", "M", "1234567", ""));
        assertEquals("Contact length must be 8 digits!", exception.getMessage());

        exception = assertThrows(NurseSchedException.class, () ->
                new Patient("1234", "John Doe", "30", "M", "123456789", ""));
        assertEquals("Contact length must be 8 digits!", exception.getMessage());

        exception = assertThrows(NurseSchedException.class, () ->
                new Patient("1234", "John Doe", "30", "M", "1234abcd", ""));
        assertEquals("Patient contact must not contain non-digits or spaces!", exception.getMessage());
    }

    @Test
    void addPatient_validPatient_success() throws NurseSchedException {
        Patient patient = new Patient("1234", "John Doe", "30", "M", "12345678", "");
        Patient.addPatient(patient);
        assertEquals(1, Patient.getPatientsList().size());
        assertEquals(patient, Patient.getPatientsList().get(0));
    }

    @Test
    void removePatient_existingPatient_success() throws NurseSchedException {
        Patient patient = new Patient("1234", "John Doe", "30", "M", "12345678", "");
        Patient.addPatient(patient);

        Patient.removePatient("1234");
        assertEquals(0, Patient.getPatientsList().size());
    }

    @Test
    void removePatient_nonExistentPatient_throwsException() {
        Exception exception = assertThrows(NurseSchedException.class, () ->
                Patient.removePatient("9999"));
        assertEquals("Patient not found!", exception.getMessage());
    }

    @Test
    void listPatientInformation_emptyList_printsEmptyMessage() throws NurseSchedException {
        Exception exception = assertThrows(NurseSchedException.class, () ->
                Patient.listPatientInformation());
        assertEquals("Patient list is empty!", exception.getMessage());
    }

    @Test
    void listPatientInformation_nonEmptyList_success() throws NurseSchedException {
        Patient patient1 = new Patient("1234", "John Doe", "30", "M", "12345678", "");
        Patient patient2 = new Patient("5678", "Jane Smith", "25", "F", "87654321", "");
        Patient.addPatient(patient1);
        Patient.addPatient(patient2);

        Patient.listPatientInformation();
        assertEquals(2, Patient.getPatientsList().size());
    }

    @Test
    void printProfileWithID_validId_success() throws NurseSchedException {
        Patient patient = new Patient("1234", "John Doe", "30", "M", "12345678", "");
        Patient.addPatient(patient);

        Patient.printProfileWithID("1234");
    }

    @Test
    void printProfileWithID_invalidIdLength_throwsException() {
        Exception exception = assertThrows(NurseSchedException.class, () ->
                Patient.printProfileWithID("123"));
        assertEquals("Patient ID must be 4 digits long.", exception.getMessage());
    }

    @Test
    void printProfileWithID_invalidIdFormat_throwsException() {
        Exception exception = assertThrows(NurseSchedException.class, () ->
                Patient.printProfileWithID("12a4"));
        assertEquals("Patient ID must contain only digits.", exception.getMessage());
    }

    @Test
    void printProfileWithID_nonExistentId_printsNotFound() throws NurseSchedException {
        Patient.addPatient(new Patient("1234", "John Doe", "30", "M", "12345678", ""));
        Patient.printProfileWithID("9999");
    }

    @Test
    void editPatientDetails_validUpdates_success() throws NurseSchedException {
        Patient patient = new Patient("1234", "John Doe", "30", "M", "12345678", "");
        Patient.addPatient(patient);

        Patient.editPatientDetails("1234", "Johnathan Doe", "31", null, "98765432", "New notes");

        assertEquals("Johnathan Doe", patient.getName());
        assertEquals("31", patient.getAge());
        assertEquals("M", patient.getGender());
        assertEquals("98765432", patient.getContact());
        assertEquals("New notes", patient.getNotes());

        Patient.editPatientDetails("1234", "John Doe", null, "f", null, null);
        assertEquals("John Doe", patient.getName());
        assertEquals("31", patient.getAge());
        assertEquals("F", patient.getGender());
        assertEquals("98765432", patient.getContact());
        assertEquals("New notes", patient.getNotes());
    }

    @Test
    void editPatientDetails_nonExistentPatient_throwsException() {
        Exception exception = assertThrows(NurseSchedException.class, () ->
                Patient.editPatientDetails("9999", "New Name", null, null, null, null));
        assertEquals("Patient not found!", exception.getMessage());
    }

    @Test
    void editPatientDetails_invalidAge_throwsException() throws NurseSchedException {
        Patient patient = new Patient("1234", "John Doe", "30", "M", "12345678", "");
        Patient.addPatient(patient);

        Exception exception = assertThrows(NurseSchedException.class, () ->
                Patient.editPatientDetails("1234", null, "abc", null, null, null));
        assertEquals("Patient age must not contain any non-digits or spaces!", exception.getMessage());
    }

    @Test
    void editPatientDetails_invalidContact_throwsException() throws NurseSchedException {
        Patient patient = new Patient("1234", "John Doe", "30", "M", "12345678", "");
        Patient.addPatient(patient);

        Exception exception = assertThrows(NurseSchedException.class, () ->
                Patient.editPatientDetails("1234", null, null, null, "1234abcd", null));
        assertEquals("Patient contact must not contain non-digits or spaces!", exception.getMessage());
    }

    @Test
    void toString_withNotes_containsNotes() throws NurseSchedException {
        Patient patient = new Patient("1234", "John Doe", "30", "M", "12345678", "Allergic to penicillin");
        String result = patient.toString();
        assertTrue(result.contains("Notes: Allergic to penicillin"));
    }

    @Test
    void toString_withoutNotes_containsNoNotesMessage() throws NurseSchedException {
        Patient patient = new Patient("1234", "John Doe", "30", "M", "12345678", "");
        String result = patient.toString();
        assertTrue(result.contains("Notes: No notes were given."));
    }
}
