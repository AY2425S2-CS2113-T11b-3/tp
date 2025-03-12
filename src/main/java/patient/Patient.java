package patient;

import java.util.ArrayList;

public class Patient {
    private String name;
    private String age;
    private String notes;

    protected static ArrayList<Patient> patientsList = new ArrayList<>();

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

    @Override
    public String toString() {
        return "| " + name + " | " + age + " years old" + " | " + notes + " |";
    }
}
