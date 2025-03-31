package seedu.nursesched.storage;

import seedu.nursesched.medicine.Medicine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MedicineStorage {
    private static final String FILE_PATH = "data/Medicine.txt";

    public static ArrayList<Medicine> readFile() {
        File medicineFile = new File(FILE_PATH);
        ArrayList<Medicine> medicineList = new ArrayList<>();

        if (!medicineFile.exists()) {
            medicineFile.getParentFile().mkdirs();
            return medicineList;
        }

        try (Scanner fileScanner = new Scanner(medicineFile)) {
            while (fileScanner.hasNext()) {
                String currentLine = fileScanner.nextLine();
                Medicine medicine = parseMedicine(currentLine);
                medicineList.add(medicine);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found at: " + FILE_PATH);
        }
        return medicineList;
    }

    private static Medicine parseMedicine(String currentLine) {
        String[] parts = currentLine.split(" \\| ");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid medicine format in storage file: " + currentLine);
        }

        String medicineName = parts[0];
        int quantity = Integer.parseInt(parts[1]);
        return new Medicine(quantity, medicineName);
    }

    public static String formatString(Medicine medicine) {
        return medicine.getMedicineName() + " | " + medicine.getQuantity();
    }

    public static void overwriteSaveFile(ArrayList<Medicine> medicineList) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            for (Medicine medicine : medicineList) {
                writer.write(formatString(medicine) + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving medicines: " + e.getMessage());
        }
    }
}
