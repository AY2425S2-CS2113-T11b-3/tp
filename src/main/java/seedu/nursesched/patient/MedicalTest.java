package seedu.nursesched.patient;

import seedu.nursesched.exception.ExceptionMessage;
import seedu.nursesched.exception.NurseSchedException;

import java.util.ArrayList;

/**
 * The MedicalTest class represents a medical test associated with a specific patient.
 * It stores details about the test such as the patient ID, test name, and result.
 * The class provides methods to add, remove, and list medical tests for patients.
 */
public class MedicalTest {
    protected static ArrayList<MedicalTest> medicalTestList = new ArrayList<>();

    private final String patientId; // Patient ID associated with this medical test
    private final String testName;
    private final String result;

    /**
     * Constructs a new MedicalTest object with the specified patient ID, test name, and result.
     *
     * @param patientId The ID of the patient associated with the test.
     * @param testName  The name of the medical test.
     * @param result    The result of the medical test.
     * @throws NurseSchedException If the test name or result is empty.
     */
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

    /**
     * Adds a medical test to the list of medical tests.
     *
     * @param test The MedicalTest object to be added.
     */
    public static void addMedicalTest(MedicalTest test) {
        medicalTestList.add(test);
    }

    /**
     * Removes all medical tests for a specific patient based on their patient ID.
     *
     * @param patientId The ID of the patient whose tests will be removed.
     */
    public static void removeTestsForPatient(String patientId) {
        medicalTestList.removeIf(test -> test.getPatientId().equals(patientId));
    }

    /**
     * Lists all medical tests for a specific patient ID.
     * If no tests are found for the given patient ID, a message is printed indicating that no tests exist.
     *
     * @param patientId The ID of the patient for whom the tests will be listed.
     */
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

    /**
     * Returns a string representation of the medical test in the format:
     * Patient ID: {@code patientId} - Test: {@code testName}, Result: {@code result}.
     *
     * @return A string representation of the medical test.
     */
    @Override
    public String toString() {
        return "Patient ID: " + patientId + " - Test: " + testName + ", Result: " + result;
    }
}
