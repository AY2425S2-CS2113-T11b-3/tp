package seedu.nursesched.storage;

import seedu.nursesched.exception.NurseSchedException;
import seedu.nursesched.patient.MedicalTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PatientTestStorage {
    private static final String FILE_PATH = "data/PatientTest.txt";

    public static ArrayList<MedicalTest> readFile() {
        File patientTestFile = new File(FILE_PATH);
        ArrayList<MedicalTest> patientTestList = new ArrayList<>();

        if (!patientTestFile.exists()) {
            patientTestFile.getParentFile().mkdirs();
            return patientTestList;
        }

        try (Scanner fileScanner = new Scanner(patientTestFile)) {
            while (fileScanner.hasNext()) {
                String currentLine = fileScanner.nextLine();
                MedicalTest patientTest = parsePatientTest(currentLine);
                patientTestList.add(patientTest);
            }
        } catch (FileNotFoundException | NurseSchedException e) {
            System.out.println("Error reading file.");
        }
        return patientTestList;
    }

    private static MedicalTest parsePatientTest(String currentLine) throws NurseSchedException {
        String[] parts = currentLine.split(" \\| ");

        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid patient format in storage file: " + currentLine);
        }

        String id = parts[0];
        String test = parts[1];
        String result = parts[2];

        return new MedicalTest(id, test, result);
    }

    public static String formatString(MedicalTest medicalTest) {
        return medicalTest.getPatientId() + " | " +  medicalTest.getTestName() + " | " + medicalTest.getResult();
    }

    public static void overwriteSaveFile(ArrayList<MedicalTest> patientTestList) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            for (MedicalTest medicalTest : patientTestList) {
                writer.write(formatString(medicalTest) + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving patient tests: " + e.getMessage());
        }
    }
}
