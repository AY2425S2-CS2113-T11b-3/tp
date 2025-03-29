package seedu.nursesched.patient;

import seedu.nursesched.exception.ExceptionMessage;
import seedu.nursesched.exception.NurseSchedException;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * The Patient class represents a patient in the healthcare system.
 * It stores patient information such as name, age, and notes (optional).
 * This class also provides methods to add, remove, and display patient information.
 */
public class Patient {
    protected static ArrayList<Patient> patientsList = new ArrayList<>();

    private final String id;
    private final String name;
    private final String age;
    private final String gender;
    private final String contact;
    private final String notes;

    /**
     * Constructs a new Patient object with the specified name, age, and notes.
     *
     * @param name  The name of the patient.
     * @param age   The age of the patient.
     * @param notes Additional notes about the patient.
     */
    public Patient(String id, String name, String age, String gender, String contact, String notes) {
        assert id != null : "id cannot be null";
        assert name != null : "Name cannot be null";
        assert age != null : "Age cannot be null";
        assert gender != null : "Gender cannot be null";
        assert contact != null : "Contact cannot be null";

        this.id = id;
        this.name = name;
        this.age = age;
        this.notes = notes;
        this.gender = gender;
        this.contact = contact;
    }

    public String getId() {
        return id;
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
    public static void removePatient(int index) throws NurseSchedException{
        assert index >= 0 : "Patient index number is invalid";
        if (index >= patientsList.size()) {
            throw new NurseSchedException(ExceptionMessage.INVALID_PATIENT_NUMBER);
        }
        System.out.println("Patient information removed for " + patientsList.get(index).name + ".");
        patientsList.remove(index);
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
        for (Patient patient : patientsList) {
            System.out.println(patient.toString());
        }
    }

    public static void printProfileWithID(String id) throws NurseSchedException {
        if (id.length() > 4 || id.length() < 3) {
            throw new NurseSchedException(ExceptionMessage.INVALID_ID_LENGTH);
        }

        for (char c : id.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new NurseSchedException(ExceptionMessage.INVALID_ID_INPUT);
            }
        }

        if (patientsList.isEmpty()) {
            System.out.println("There are no patients found!");
            return;
        }

        ArrayList<Patient> filteredList = patientsList.stream()
                .filter(patient -> patient.getId().equals(id))
                .collect(Collectors.toCollection(ArrayList::new));

        if (filteredList.isEmpty()) {
            System.out.println("No patient found with ID: " + id);
        } else {
            for (Patient patient : filteredList) {
                System.out.println(patient.toString());
            }
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

    public static ArrayList<Patient> getPatientsList() {
        return patientsList;
    }

    /**
     * Returns a string representation of the patient in the format:
     * "name, age years old, notes".
     *
     * @return A string representation of the patient.
     */
    @Override
    public String toString() {
        return "Patient Details:\n" +
                "  ID: P" + id + "\n" +
                "  Name: " + name + "\n" +
                "  Age: " + age + " years old\n" +
                "  Gender: " + gender + "\n" +
                "  Contact: " + contact + "\n" +
                (notes.isEmpty() ? "" : "  Notes: " + notes + "\n");
    }
}
