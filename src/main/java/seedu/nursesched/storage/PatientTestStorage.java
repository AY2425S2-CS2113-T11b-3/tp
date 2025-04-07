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

    public static ArrayList<MedicalTest> readFile() throws NurseSchedException {
        File patientTestFile = new File(FILE_PATH);
        ArrayList<MedicalTest> patientTestList = new ArrayList<>();

        if (!patientTestFile.exists()) {
            patientTestFile.getParentFile().mkdirs();
            return patientTestList;
        }

        try (Scanner fileScanner = new Scanner(patientTestFile)) {
            int lineNumber = 0;
            while (fileScanner.hasNext()) {
                lineNumber++;
                String currentLine = fileScanner.nextLine();

                try {
                    MedicalTest patientTest = parsePatientTest(currentLine);
                    patientTestList.add(patientTest);
                } catch (Exception e) {
                    System.out.println("Error parsing line " + lineNumber + " of save file: " + currentLine);
                    System.out.println("Consider removing that line from the save file. Bypassing line.");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found at: " + FILE_PATH);
        }
        return patientTestList;
    }

    private static MedicalTest parsePatientTest(String currentLine) throws NurseSchedException {
        String[] parts = currentLine.split(" \\| ");

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
