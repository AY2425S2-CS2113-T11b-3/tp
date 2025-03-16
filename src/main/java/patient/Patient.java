package patient;

import java.util.ArrayList;

/**
 * The Patient class represents a patient in the healthcare system.
 * It stores patient information such as name, age, and notes (optional).
 * This class also provides methods to add, remove, and display patient information.
 */
public class Patient {
    private static ArrayList<Patient> patientsList = new ArrayList<>();

    private String name = "";
    private String age = "";
    private String notes = "";

    /**
     * Constructs a new Patient object with the specified name, age, and notes.
     *
     * @param name  The name of the patient.
     * @param age   The age of the patient.
     * @param notes Additional notes about the patient.
     */
    public Patient(String name, String age, String notes) {
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
        System.out.println("Patient information added for " + patient.name + ".");
        patientsList.add(patient);
    }

    /**
     * Removes a patient from the list of patients based on the provided index.
     *
     * @param index The index of the patient to be removed based on the patients list.
     */
    public static void removePatient(int index) {
        System.out.println("Patient information removed for " + patientsList.get(index - 1).name + ".");
        patientsList.remove(index - 1);
    }

    /**
     * Prints the information of all patients in the list.
     * If the list is empty, it prints a message indicating that no patient information is available.
     */
    public static void printPatientInformation() {
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
        return name + ", " + age + " years old" + ", " + notes;
    }
}
