package patient;

import java.util.ArrayList;

public class Patient {
    protected static ArrayList<Patient> patientsList = new ArrayList<>();

    private String name;
    private String age;
    private String notes;

    public Patient(String name, String age, String notes) {
        this.name = name;
        this.age = age;
        this.notes = notes;
    }

    public static void addPatient(Patient patient) {
        System.out.println("Patient information added for " + patient.name + ".");
        patientsList.add(patient);
    }

    public static void removePatient(int index) {
        System.out.println("Patient information removed for " + patientsList.get(index - 1).name + ".");
        patientsList.remove(index - 1);
    }

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

    @Override
    public String toString() {
        return name + ", " + age + " years old" + ", " + notes + ".";
    }
}
