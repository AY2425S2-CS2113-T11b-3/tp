package seedu.nursesched.patient;

import seedu.nursesched.appointment.Appointment;
import seedu.nursesched.exception.ExceptionMessage;
import seedu.nursesched.exception.NurseSchedException;
import seedu.nursesched.storage.PatientStorage;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * The Patient class represents a patient in the healthcare system.
 * It stores patient information such as ID, name, age, gender, contact, and notes (optional).
 * This class provides methods to add, remove, edit, and display patient information.
 */
public class Patient {
    protected static ArrayList<Patient> patientsList = new ArrayList<>();

    private final String id;
    private String name;
    private String age;
    private String gender;
    private String contact;
    private String notes;

    static {
        try {
            patientsList = PatientStorage.readFile();
        } catch (NurseSchedException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Constructs a new Patient object with the specified details.
     *
     * @param id      The unique identifier for the patient.
     * @param name    The name of the patient.
     * @param age     The age of the patient.
     * @param gender  The gender of the patient.
     * @param contact The contact details of the patient.
     * @param notes   Additional notes about the patient (optional).
     * @throws NurseSchedException If any required field is empty.
     */
    public Patient(String id, String name, String age, String gender, String contact, String notes)
            throws NurseSchedException {
        assert id != null : "id cannot be null";
        assert name != null : "Name cannot be null";
        assert age != null : "Age cannot be null";
        assert gender != null : "Gender cannot be null";
        assert contact != null : "Contact cannot be null";

        if (name.trim().isEmpty() || age.trim().isEmpty() || gender.trim().isEmpty() || contact.trim().isEmpty()) {
            throw new NurseSchedException(ExceptionMessage.EMPTY_PATIENT_FIELDS);
        }

        validateID(id);
        verifyGender(gender);

        for (Patient patient : patientsList) {
            if (patient.getId().equals(id)) {
                throw new NurseSchedException(ExceptionMessage.PATIENT_ID_EXIST);
            }
        }

        age = age.replaceFirst("^0+(?!$)", "");
        contact = contact.replaceFirst("^0+(?!$)", "");
        verifyAge(age);
        verifyContact(contact);

        this.id = id;
        this.name = name;
        this.age = age;
        this.notes = notes;
        this.gender = gender.toUpperCase();
        this.contact = contact;
    }

    private static void verifyGender(String gender) throws NurseSchedException {
        if (!gender.equalsIgnoreCase("M") && !gender.equalsIgnoreCase("F")) {
            throw new NurseSchedException(ExceptionMessage.INVALID_GENDER);
        }
    }

    private static void validateID(String id) throws NurseSchedException {
        // Validate ID format (4 digits)
        if (id.trim().isEmpty()) {
            throw new NurseSchedException(ExceptionMessage.MISSING_ID);
        }

        if (id.trim().length() != 4) {
            if (id.contains(" ")) {
                throw new NurseSchedException(ExceptionMessage.ID_CONTAINS_SPACES);
            } else {
                throw new NurseSchedException(ExceptionMessage.INVALID_ID_LENGTH);
            }
        } else {
            id = id.trim();
        }

        for (char c : id.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new NurseSchedException(ExceptionMessage.INVALID_ID_INPUT);
            }
        }
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
        PatientStorage.overwriteSaveFile(patientsList);
    }

    /**
     * Removes a patient from the list of patients based on the provided ID.
     *
     * @param id The unique identifier of the patient to be removed.
     * @throws NurseSchedException If the patient with the specified ID does not exist.
     */
    public static void removePatient(String id) throws NurseSchedException {
        assert id != null : "Patient ID cannot be null";

        boolean isFound = false;
        validateID(id);
        for (Patient patient : patientsList) {
            if (patient.getId().equals(id)) {
                Appointment.removeAppointmentsForPatient(Integer.parseInt(id));
                patientsList.remove(patient);
                System.out.println("Patient information removed for ID: " + id);
                isFound = true;
                MedicalTest.removeTestsForPatient(patient.getId());
                break;
            }
        }

        if (!isFound) {
            throw new NurseSchedException(ExceptionMessage.PATIENT_NOT_FOUND);
        } else {
            PatientStorage.overwriteSaveFile(patientsList);
        }
    }


    /**
     * Prints the information of all patients in the list.
     * If the list is empty, it prints a message indicating that no patient information is available.
     */
    public static void listPatientInformation() throws NurseSchedException {
        if (patientsList.isEmpty()) {
            throw new NurseSchedException(ExceptionMessage.EMPTY_PATIENT_LIST);
        }
        for (Patient patient : patientsList) {
            System.out.println(patient.toString());
        }
    }

    /**
     * Prints the profile of a patient with the specified ID.
     *
     * @param id The unique identifier of the patient.
     * @throws NurseSchedException If the ID is invalid or no patient is found.
     */
    public static void printProfileWithID(String id) throws NurseSchedException {
        if (id.length() != 4) {
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
     * Edits the details of a patient based on the provided ID.
     *
     * @param id         The ID of the patient to update.
     * @param newName    The new name (if provided).
     * @param newAge     The new age (if provided).
     * @param newGender  The new gender (if provided).
     * @param newContact The new contact details (if provided).
     * @param newNotes   The new notes (if provided).
     */
    public static void editPatientDetails(String id, String newName, String newAge, String newGender,
                                          String newContact, String newNotes) throws NurseSchedException {
        boolean isFound = false;

        for (Patient patient : patientsList) {
            if (patient.getId().equals(id)) {
                isFound = true;

                if ((newName != null && newName.equals(patient.name))) {
                    throw new NurseSchedException(ExceptionMessage.NO_CHANGES_FOUND);
                }
                if ((newAge != null && newAge.equals(patient.age))) {
                    throw new NurseSchedException(ExceptionMessage.NO_CHANGES_FOUND);
                }
                if ((newGender != null && newGender.equalsIgnoreCase(patient.gender))) {
                    throw new NurseSchedException(ExceptionMessage.NO_CHANGES_FOUND);
                }
                if ((newContact != null && newContact.equals(patient.contact))) {
                    throw new NurseSchedException(ExceptionMessage.NO_CHANGES_FOUND);
                }
                if ((newNotes.equals(patient.notes))) {
                    throw new NurseSchedException(ExceptionMessage.NO_CHANGES_FOUND);
                } else {
                    patient.notes = newNotes;
                }

                if (newName != null) {
                    patient.name = newName;
                }
                if (newAge != null) {
                    newAge = newAge.replaceFirst("^0+(?!$)", "");
                    verifyAge(newAge);
                    patient.age = newAge;
                }
                if (newGender != null) {
                    verifyGender(newGender);
                    patient.gender = newGender.toUpperCase();
                }
                if (newContact != null) {
                    newContact = newContact.replaceFirst("^0+(?!$)", "");
                    verifyContact(newContact);
                    patient.contact = newContact;
                }

                System.out.println("Patient information updated for ID: " + id);
                break;
            }
        }
        
        if (!isFound) {
            throw new NurseSchedException(ExceptionMessage.PATIENT_NOT_FOUND);
        } else {
            PatientStorage.overwriteSaveFile(patientsList);
        }
    }

    private static void verifyContact(String contact) throws NurseSchedException {
        try {
            int contactNumber = Integer.parseInt(contact);
            if (contactNumber < 10000000 || contactNumber > 99999999) {
                throw new NurseSchedException(ExceptionMessage.INVALID_CONTACT_LENGTH);
            }
        } catch (NumberFormatException e) {
            throw new NurseSchedException(ExceptionMessage.PATIENT_CONTACT_DIGITS);
        }
    }

    private static void verifyAge(String age) throws NurseSchedException {
        try {
            int ageNumber = Integer.parseInt(age);
            if (ageNumber < 0) {
                throw new NurseSchedException(ExceptionMessage.PATIENT_AGE_NEGATIVE);
            } else if (ageNumber > 125) {
                throw new NurseSchedException(ExceptionMessage.PATIENT_AGE_LIMIT);
            }
        } catch (NumberFormatException e) {
            throw new NurseSchedException(ExceptionMessage.PATIENT_AGE_DIGITS);
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getContact() {
        return contact;
    }

    public String getNotes() {
        return notes;
    }

    public static ArrayList<Patient> getPatientsList() {
        return patientsList;
    }

    /**
     * Returns a string representation of the patient's details.
     *
     * @return A formatted string containing patient details.
     */
    @Override
    public String toString() {
        return "Patient Details:\n" +
                "  ID: " + id + "\n" +
                "  Name: " + name + "\n" +
                "  Age: " + age + " years old\n" +
                "  Gender: " + gender.toUpperCase() + "\n" +
                "  Contact: " + contact + "\n" +
                (notes.isEmpty() ? "  Notes: No notes were given." : "  Notes: " + notes);
    }
}
