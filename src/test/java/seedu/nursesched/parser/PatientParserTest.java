package seedu.nursesched.parser;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.nursesched.exception.NurseSchedException;
import seedu.nursesched.patient.Patient;
import seedu.nursesched.storage.PatientStorage;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;

class PatientParserTest {
    static ArrayList<Patient> initialPatientList;

    @BeforeAll
    public static void saveInitialPatientList() {
        initialPatientList = Patient.getPatientsList();
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
    void extractInputs_emptyInput_throwsException() {
        Exception exception = assertThrows(NurseSchedException.class, () ->
                PatientParser.extractInputs(""));
        assertEquals("Input line cannot be empty!", exception.getMessage());
    }

    @Test
    void extractInputs_addCommandMissingFields_throwsException() {
        Exception exception = assertThrows(NurseSchedException.class, () ->
                PatientParser.extractInputs("pf add"));
        assertEquals("Patient information cannot be empty!", exception.getMessage());
    }

    @Test
    void extractInputs_addCommandInvalidFormat_throwsException() {
        Exception exception = assertThrows(NurseSchedException.class, () ->
                PatientParser.extractInputs("pf add id/123 p/John"));
        assertEquals("Some patient fields are missing!", exception.getMessage());
    }

    @Test
    void extractInputs_addCommandValidInput_success() throws NurseSchedException {
        PatientParser parser = PatientParser.extractInputs(
                "pf add id/1234 p/John Doe a/30 g/M c/12345678 n/Allergic to peanuts");
        assertNotNull(parser);
        assertEquals("add", parser.getCommand());
        assertEquals("1234", parser.getId());
        assertEquals("John Doe", parser.getName());
        assertEquals("30", parser.getAge());
        assertEquals("M", parser.getGender());
        assertEquals("12345678", parser.getContact());
        assertEquals("Allergic to peanuts", parser.getNotes());
    }

    @Test
    void extractInputs_delCommandMissingId_throwsException() {
        Exception exception = assertThrows(NurseSchedException.class, () ->
                PatientParser.extractInputs("pf del"));
        assertEquals("Patient ID field cannot be empty!", exception.getMessage());
    }

    @Test
    void extractInputs_delCommandValidInput_success() throws NurseSchedException {
        PatientParser parser = PatientParser.extractInputs("pf del id/1234");
        assertNotNull(parser);
        assertEquals("del", parser.getCommand());
        assertEquals("1234", parser.getId());
    }

    @Test
    void extractInputs_listCommandValidInput_success() throws NurseSchedException {
        PatientParser parser = PatientParser.extractInputs("pf list");
        assertNotNull(parser);
        assertEquals("list", parser.getCommand());
    }

    @Test
    void extractInputs_listCommandWithExtraInput_throwsException() {
        Exception exception = assertThrows(NurseSchedException.class, () ->
                PatientParser.extractInputs("pf list extra"));
        assertEquals("Invalid Patient command, use either 'add', 'del', `list`, \n" +
                "`result add`, `result del`, or `result list`!", exception.getMessage());
    }

    @Test
    void extractInputs_findCommandMissingId_throwsException() {
        Exception exception = assertThrows(NurseSchedException.class, () ->
                PatientParser.extractInputs("pf find"));
        assertEquals("Missing id/ identifier!", exception.getMessage());
    }

    @Test
    void extractInputs_findCommandValidInput_success() throws NurseSchedException {
        PatientParser parser = PatientParser.extractInputs("pf find id/1234");
        assertNotNull(parser);
        assertEquals("find", parser.getCommand());
        assertEquals("1234", parser.getId());
    }

    @Test
    void extractInputs_editCommandMissingFields_throwsException() {
        Exception exception = assertThrows(NurseSchedException.class, () ->
                PatientParser.extractInputs("pf edit id/1234"));
        assertEquals("Input details cannot be empty!", exception.getMessage());
    }

    @Test
    void extractInputs_editCommandPartialUpdate_success() throws NurseSchedException {
        PatientParser parser = PatientParser.extractInputs(
                "pf edit id/1234 p/New Name a/31 n/New notes");
        assertNotNull(parser);
        assertEquals("edit", parser.getCommand());
        assertEquals("1234", parser.getId());
        assertEquals("New Name", parser.getName());
        assertEquals("31", parser.getAge());
        assertEquals("New notes", parser.getNotes());
        assertNull(parser.getGender());
        assertNull(parser.getContact());
    }

    @Test
    void extractInputs_resultAddCommandValidInput_success() throws NurseSchedException {
        Patient.getPatientsList().add(new Patient("1234", "John Doe", "30", "M", "12345678", ""));

        PatientParser parser = PatientParser.extractInputs(
                "pf result add id/1234 t/Blood Test r/Normal");
        assertNotNull(parser);
        assertEquals("result add", parser.getCommand());
        assertEquals("1234", parser.getId());
    }

    @Test
    void extractInputs_resultDelCommandValidInput_success() throws NurseSchedException {
        Patient.getPatientsList().add(new Patient("1234", "John Doe", "30", "M", "12345678", ""));

        PatientParser parser = PatientParser.extractInputs("pf result del id/1234");
        assertNotNull(parser);
        assertEquals("result del", parser.getCommand());
        assertEquals("1234", parser.getId());
    }

    @Test
    void extractInputs_invalidCommand_returnsNull() throws NurseSchedException {
        Exception exception = assertThrows(NurseSchedException.class, () ->
                PatientParser.extractInputs("pf invalid"));
        assertEquals("Invalid Patient command, use either 'add', 'del', `list`, \n" +
                "`result add`, `result del`, or `result list`!", exception.getMessage());
    }

    @Test
    void extractInputs_addCommandInvalidIdLength_throwsException() {
        Exception exception = assertThrows(NurseSchedException.class, () ->
                PatientParser.extractInputs("pf add id/123 p/John Doe a/30 g/M c/12345678 n/Notes"));
        assertEquals("Patient ID must be 4 digits long.", exception.getMessage());
    }

    @Test
    void extractInputs_addCommandInvalidIdFormat_throwsException() {
        Exception exception = assertThrows(NurseSchedException.class, () ->
                PatientParser.extractInputs("pf add id/12a4 p/John Doe a/30 g/M c/12345678 n/Notes"));
        assertEquals("Patient ID must contain only digits.", exception.getMessage());
    }

    @Test
    void extractInputs_addCommandInvalidGender_throwsException() {
        try {
            PatientParser.extractInputs("pf add id/1234 p/John Doe a/30 g/X c/12345678 n/Notes");
        } catch (Exception exception) {
            assertEquals("Gender must be either 'M' or 'F'!", exception.getMessage());
        }
    }

    @Test
    void extractInputs_addCommandWithLeadingSpaces_success() throws NurseSchedException {
        PatientParser parser = PatientParser.extractInputs(
                "   pf add id/1234 p/John Doe a/30 g/M c/12345678 n/Allergic to peanuts");
        assertNotNull(parser);
        assertEquals("add", parser.getCommand());
        assertEquals("1234", parser.getId());
    }

    @Test
    void extractInputs_addCommandWithTrailingSpaces_success() throws NurseSchedException {
        PatientParser parser = PatientParser.extractInputs(
                "pf add id/1234 p/John Doe a/30 g/M c/12345678 n/Allergic to peanuts   ");
        assertNotNull(parser);
        assertEquals("add", parser.getCommand());
        assertEquals("1234", parser.getId());
    }

    @Test
    void extractInputs_addCommandWithSpacesBetweenFields_success() throws NurseSchedException {
        PatientParser parser = PatientParser.extractInputs(
                "pf add  id/1234   p/John Doe   a/30   g/M   c/12345678   n/Allergic to peanuts");
        assertNotNull(parser);
        assertEquals("add", parser.getCommand());
        assertEquals("1234", parser.getId());
    }

    @Test
    void extractInputs_editCommandWithSpacesInValues_success() throws NurseSchedException {
        PatientParser parser = PatientParser.extractInputs(
                "pf edit id/1234 p/  New Name  a/31 n/  New notes  ");
        assertNotNull(parser);
        assertEquals("edit", parser.getCommand());
        assertEquals("New Name", parser.getName().trim());
        assertEquals("New notes", parser.getNotes().trim());
    }

    @Test
    void extractInputs_resultAddCommandWithSpacesInTestName_success() throws NurseSchedException {
        Patient.getPatientsList().add(new Patient("1234", "John Doe", "30", "M", "12345678", ""));

        PatientParser parser = PatientParser.extractInputs(
                "pf result add id/1234 t/  Blood Test  r/Normal");
        assertNotNull(parser);
        assertEquals("result add", parser.getCommand());
    }

    @Test
    void extractInputs_emptyFieldsWithSpaces_throwsException() {
        Exception exception = assertThrows(NurseSchedException.class, () ->
                PatientParser.extractInputs("pf add id/   p/   a/   g/   c/   n/  "));
        assertEquals("Patient ID cannot be empty!", exception.getMessage());
    }

    @Test
    void extractInputs_tabCharactersInInput_success() throws NurseSchedException {
        PatientParser parser = PatientParser.extractInputs(
                "pf\tadd\tid/1234\tp/John Doe\ta/30\tg/M\tc/12345678\tn/Allergic to peanuts");
        assertNotNull(parser);
        assertEquals("add", parser.getCommand());
        assertEquals("1234", parser.getId());
    }

    @Test
    void extractInputs_mixedWhitespaceCharacters_success() throws NurseSchedException {
        PatientParser parser = PatientParser.extractInputs(
                " pf \t add \t id/1234 \t p/John Doe \t a/30 \t g/M \t c/12345678 \t n/Allergic to peanuts ");
        assertNotNull(parser);
        assertEquals("add", parser.getCommand());
        assertEquals("1234", parser.getId());
    }

    @Test
    void extractInputs_emptyNotesFieldWithSpaces_success() throws NurseSchedException {
        PatientParser parser = PatientParser.extractInputs(
                "pf add id/1234 p/John Doe a/30 g/M c/12345678 n/  ");
        assertNotNull(parser);
        assertEquals("", parser.getNotes().trim());
    }

    @Test
    void extractInputs_multipleConsecutiveSpacesInName_success() throws NurseSchedException {
        PatientParser parser = PatientParser.extractInputs(
                "pf add id/1234 p/John    Doe a/30 g/M c/12345678 n/Notes");
        assertNotNull(parser);
        assertEquals("John Doe", parser.getName().replaceAll("\\s+", " "));
    }
}
