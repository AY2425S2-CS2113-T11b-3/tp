package seedu.nursesched.storage;

import seedu.nursesched.exception.NurseSchedException;
import seedu.nursesched.patient.Patient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PatientStorage {
    private static final String FILE_PATH = "data/Patient.txt";

    public static ArrayList<Patient> readFile() throws NurseSchedException {
        File patientFile = new File(FILE_PATH);
        ArrayList<Patient> patientList = new ArrayList<>();

        if (!patientFile.exists()) {
            patientFile.getParentFile().mkdirs();
            return patientList;
        }

        try (Scanner fileScanner = new Scanner(patientFile)) {
            int lineNumber = 0;
            while (fileScanner.hasNext()) {
                lineNumber++;
                String currentLine = fileScanner.nextLine();

                try {
                    Patient patient = parsePatient(currentLine);
                    patientList.add(patient);
                } catch (Exception e) {
                    System.out.println("Error parsing line " + lineNumber + " of save file: " + currentLine);
                    System.out.println("Consider removing that line from the save file. Bypassing line.");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found at: " + FILE_PATH);
        }
        return patientList;
    }

    private static Patient parsePatient(String currentLine) throws NurseSchedException {
        String[] parts = currentLine.split(" \\| ");

        String id = parts[0];
        String name = parts[1];
        String age = parts[2];
        String gender = parts[3];
        String contact = parts[4];
        String notes = "";
        if (parts.length>5) {
            notes = parts[5];
        }

        return new Patient(id, name, age, gender, contact, notes);
    }

    public static String formatString(Patient patient) {
        return patient.getId() + " | " + patient.getName() + " | " + patient.getAge() + " | "
                + patient.getGender() + " | " + patient.getContact() + " | " + patient.getNotes();
    }

    public static void overwriteSaveFile(ArrayList<Patient> patientList) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            for (Patient patient : patientList) {
                writer.write(formatString(patient) + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving patient information: " + e.getMessage());
        }
    }
}
