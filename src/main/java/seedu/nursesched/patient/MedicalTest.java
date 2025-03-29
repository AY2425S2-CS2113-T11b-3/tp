package seedu.nursesched.patient;

import seedu.nursesched.exception.ExceptionMessage;
import seedu.nursesched.exception.NurseSchedException;

import java.util.ArrayList;

public class MedicalTest {
    protected static ArrayList<MedicalTest> medicalTestList = new ArrayList<>();

    private final String patientId; // Patient ID associated with this medical test
    private final String testName;
    private final String result;

    public MedicalTest(String patientId, String testName, String result) throws NurseSchedException {
        if (testName == null || testName.isEmpty()) {
            throw new NurseSchedException(ExceptionMessage.EMPTY_PATIENT_TEST_NAME);
        }

        if (result == null || result.isEmpty()) {
            throw new NurseSchedException(ExceptionMessage.EMPTY_PATIENT_TEST_RESULT);
        }

        this.patientId = patientId;
        this.testName = testName;
        this.result = result;
    }

    // Adds a test to the medicalTestList
    public static void addMedicalTest(MedicalTest test) {
        medicalTestList.add(test);
    }

    // Removes all tests for a specific patient
    public static void removeTestsForPatient(String patientId) {
        medicalTestList.removeIf(test -> test.getPatientId().equals(patientId));
    }

    // Lists all tests for a specific patient ID
    public static void listTestsForPatient(String patientId) {
        boolean found = false;
        for (MedicalTest test : medicalTestList) {
            if (test.getPatientId().equals(patientId)) {
                System.out.println(test);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No medical tests found for patient ID: " + patientId);
        }
    }

    // Getter
    public String getPatientId() {
        return patientId;
    }

    @Override
    public String toString() {
        return "Patient ID: " + patientId + " - Test: " + testName + ", Result: " + result;
    }
}
