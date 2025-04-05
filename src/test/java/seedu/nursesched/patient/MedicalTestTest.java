package seedu.nursesched.patient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.nursesched.exception.NurseSchedException;
import seedu.nursesched.storage.PatientTestStorage;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MedicalTestTest {
    static ArrayList<MedicalTest> initialMedicalTestList;

    @BeforeAll
    public static void saveInitialPatientList() {
        initialMedicalTestList = MedicalTest.getMedicalTestList();
    }

    @AfterAll
    public static void restoreFinalPatientList() {
        PatientTestStorage.overwriteSaveFile(initialMedicalTestList);
    }

    @BeforeEach
    void setUp() {
        MedicalTest.getMedicalTestList().clear();
    }

    @Test
    void constructor_emptyTestName_throwsException() {
        Exception exception = assertThrows(NurseSchedException.class, () ->
                new MedicalTest("1234", "", "Normal"));
        assertEquals("Patient test name cannot be empty!", exception.getMessage());
    }

    @Test
    void constructor_emptyTestResult_throwsException() {
        Exception exception = assertThrows(NurseSchedException.class, () ->
                new MedicalTest("1234", "Blood Test", ""));
        assertEquals("Patient test result cannot be empty!", exception.getMessage());
    }

    @Test
    void constructor_validInput_success() throws NurseSchedException {
        MedicalTest test = new MedicalTest("1234", "Blood Test", "Normal");
        assertNotNull(test);
        assertEquals("1234", test.getPatientId());
        assertEquals("Blood Test", test.getTestName());
        assertEquals("Normal", test.getResult());
    }

    @Test
    void addMedicalTest_validTest_success() throws NurseSchedException {
        MedicalTest test = new MedicalTest("1234", "Blood Test", "Normal");
        MedicalTest.addMedicalTest(test, "1234");
        assertEquals(1, MedicalTest.medicalTestList.size());
        assertEquals(test, MedicalTest.medicalTestList.get(0));
    }

    @Test
    void removeTestsForPatient_existingPatient_success() throws NurseSchedException {
        MedicalTest test1 = new MedicalTest("1234", "Blood Test", "Normal");
        MedicalTest test2 = new MedicalTest("1234", "X-Ray", "Clear");
        MedicalTest.addMedicalTest(test1, "1234");
        MedicalTest.addMedicalTest(test2, "1234");

        MedicalTest.removeTestsForPatient("1234");
        assertEquals(0, MedicalTest.medicalTestList.size());
    }

    @Test
    void removeTestsForPatient_nonExistentPatient_printsMessage() {
        MedicalTest.removeTestsForPatient("9999");
        assertEquals(0, MedicalTest.medicalTestList.size());
    }

    @Test
    void listTestsForPatient_withTests_success() throws NurseSchedException {
        MedicalTest test1 = new MedicalTest("1234", "Blood Test", "Normal");
        MedicalTest test2 = new MedicalTest("1234", "X-Ray", "Clear");
        MedicalTest.addMedicalTest(test1, "1234");
        MedicalTest.addMedicalTest(test2, "1234");

        MedicalTest.listTestsForPatient("1234");
    }

    @Test
    void listTestsForPatient_noTests_printsMessage() {
        MedicalTest.listTestsForPatient("1234");
    }

    @Test
    void toString_validTest_returnsCorrectFormat() throws NurseSchedException {
        MedicalTest test = new MedicalTest("1234", "Blood Test", "Normal");
        String expected = "Patient ID: 1234 - Test: Blood Test, Result: Normal";
        assertEquals(expected, test.toString());
    }

    @Test
    void staticInitializer_loadsFromStorage() {
        assertNotNull(MedicalTest.medicalTestList);
    }
}
