package seedu.nursesched.storage;

import seedu.nursesched.medicine.Medicine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles the reading, parsing, and saving of medicine data to a file.
 */
public class MedicineStorage {
    private static final String FILE_PATH = "data/Medicine.txt";

    /**
     * Reads the medicine data from the storage file and returns a list of medicines.
     * If the file does not exist, it will create necessary directories and return an empty list.
     *
     * @return A list of medicines read from the file.
     */
    public static ArrayList<Medicine> readFile() {
        File medicineFile = new File(FILE_PATH);
        ArrayList<Medicine> medicineList = new ArrayList<>();
        ArrayList<Medicine> validLines = new ArrayList<>();

        if (!medicineFile.exists()) {
            medicineFile.getParentFile().mkdirs();
            return medicineList;
        }

        try (Scanner fileScanner = new Scanner(medicineFile)) {
            while (fileScanner.hasNext()) {
                String currentLine = fileScanner.nextLine();
                Medicine medicine = parseMedicine(currentLine);
                if (medicine != null) {
                    medicineList.add(medicine);
                    validLines.add(medicine);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found at: " + FILE_PATH);
        }

        overwriteSaveFile(validLines);
        return medicineList;
    }

    /**
     * Parses a string representing a medicine and returns a Medicine object.
     * The expected format is: "medicineName | quantity"
     *
     * @param currentLine The line representing a medicine.
     * @return A Medicine object with the parsed values or null if invalid.
     */
    private static Medicine parseMedicine(String currentLine) {
        try {
            String[] parts = currentLine.split(" \\| ");

            if (parts.length != 2) {
                System.out.println("Warning: Invalid medicine format:" + currentLine);
                return null;
            }

            String medicineName = parts[0].trim();
            if (medicineName.isEmpty()) {
                System.out.println("Warning: Medicine name cannot be empty: " + currentLine);
                return null;
            }

            int quantity;
            try {
                quantity = Integer.parseInt(parts[1].trim());
            } catch (NumberFormatException e) {
                System.out.println("Warning: Invalid quantity format : " + currentLine);
                return null;
            }

            return new Medicine(quantity, medicineName);

        } catch (Exception e) {
            System.out.println("Unexpected error while parsing medicine: " + currentLine);
            return null;
        }
    }

    /**
     * Formats a Medicine object into a string representation suitable for saving.
     * The format is: "medicineName | quantity"
     *
     * @param medicine The Medicine object to format.
     * @return A formatted string representation of the medicine.
     */
    public static String formatString(Medicine medicine) {
        return medicine.getMedicineName() + " | " + medicine.getQuantity();
    }

    /**
     * Overwrites the storage file with the list of valid medicines.
     *
     * @param validLines The list of valid lines to save to the file.
     */
    public static void overwriteSaveFile(ArrayList<Medicine> validLines) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            for (Medicine line : validLines) {
                writer.write(formatString(line) + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving medicines: " + e.getMessage());
        }
    }
}
