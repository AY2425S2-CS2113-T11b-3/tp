package seedu.nursesched.patient;

import java.util.ArrayList;

/**
 * The Patient class represents a patient in the healthcare system.
 * It stores patient information such as name, age, and notes (optional).
 * This class also provides methods to add, remove, and display patient information.
 */
public class Patient {
    protected static ArrayList<Patient> patientsList = new ArrayList<>();

    private final String name;
    private final String age;
    private final String notes;

    /**
     * Constructs a new Patient object with the specified name, age, and notes.
     *
     * @param name  The name of the patient.
     * @param age   The age of the patient.
     * @param notes Additional notes about the patient.
     */
    public Patient(String name, String age, String notes) {
        assert name != null : "Name cannot be null";
        assert age != null : "Age cannot be null";

        this.name = name;
        this.age = age;
        this.notes = notes;
    }

    /**
     * Adds a patient to the list of patients.
     *
     * @param patient The Patient object to be added.
     */
    public static void addPatient(Patient patient) {
        assert patient != null : "Patient details are invalid";

        patientsList.add(patient);
        System.out.println("Patient information added for " + patient.name + ".");
    }

    /**
     * Removes a patient from the list of patients based on the provided index.
     *
     * @param index The index of the patient to be removed based on the patients list.
     */
    public static void removePatient(int index) {
        assert index > 0 : "Patient index number is invalid";
        if (index > patientsList.size()) {
            System.out.println("Invalid patient index.");
            return;
        }
        patientsList.remove(index);
        System.out.println("Patient information removed for " + patientsList.get(index).name + ".");
    }

    /**
     * Prints the information of all patients in the list.
     * If the list is empty, it prints a message indicating that no patient information is available.
     */
    public static void listPatientInformation() {
        if (patientsList.isEmpty()) {
            System.out.println("Patient information is empty.");
            return;
        }
        System.out.println("Patient information:");
        System.out.println("");
        for (int index = 0; index < patientsList.size(); index++) {
            System.out.println((index + 1) + ". " + patientsList.get(index).toString());
        }
    }

    /**
     * Returns the number of patients in the list.
     *
     * @return The size of the patients list.
     */
    public static int getSizeOfList() {
        return patientsList.size();
    }

    /**
     * Returns a string representation of the patient in the format:
     * "name, age years old, notes".
     *
     * @return A string representation of the patient.
     */
    @Override
    public String toString() {
        return name + ", " + age + " years old" + (notes.isEmpty() ? "." : ", " + notes + ".");
    }
}
