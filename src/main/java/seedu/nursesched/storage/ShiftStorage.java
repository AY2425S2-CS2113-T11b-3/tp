package seedu.nursesched.storage;

import seedu.nursesched.shift.Shift;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Provides persistent storage operations for shifts.
 * This class handles reading shifts from a local save file,
 * writing shifts to a file, and formatting shift data for storage.
 */
public class ShiftStorage {
    private static final String FILE_PATH = "data/Shift.txt";

    /**
     * Reads all shifts from the shift save file.
     * If the save file doesn't exist, it creates the necessary directories and returns an empty ArrayList.
     *
     * @return An ArrayList containing all shifts read from the storage file.
     */
    public static ArrayList<Shift> readFile() {
        File file = new File(FILE_PATH);
        ArrayList<Shift> shiftList = new ArrayList<>();

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            return shiftList;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String currentLine = fileScanner.nextLine();
                Shift shift = getDetails(currentLine);
                shiftList.add(shift);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found at: " + FILE_PATH);
        }

        return shiftList;
    }

    /**
     * Extracts shift details from a string in the save file.
     *
     * @param currentLine The formatted string containing shift information.
     * @return A new Shift object created with parsed information.
     */
    private static Shift getDetails(String currentLine) {
        String[] parts = currentLine.split(" \\| ");

        boolean isDone = Boolean.parseBoolean(parts[0]);
        LocalTime startTime = LocalTime.parse(parts[1]);
        LocalTime endTime = LocalTime.parse(parts[2]);
        LocalDate date = LocalDate.parse(parts[3]);
        String task = parts.length > 4 ? parts[4] : "";

        Shift shift = new Shift(startTime, endTime, date, task);
        shift.setDone(isDone);
        return shift;
    }

    /**
     * Formats a Shift object into a string for storage.
     * Format: [Status] | [Start Time] | [End Time] | [Date] | [Task]
     *
     * @param shift The Shift object to format.
     * @return Formatted string for save file.
     */
    public static String formatString(Shift shift) {
        return shift.getStatus() + " | " + shift.getStartTime()
                + " | " + shift.getEndTime()
                + " | " + shift.getDate()
                + " | " + shift.getShiftTask();
    }

    /**
     * Overwrites the storage file with the current ArrayList of shifts.
     *
     * @param shiftList The ArrayList of Shift objects to replace the save file with.
     */
    public static void overwriteSaveFile(ArrayList<Shift> shiftList) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            for (Shift shift : shiftList) {
                writer.write(formatString(shift) + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving shifts: " + e.getMessage());
        }
    }

    /**
     * Appends a single shift to the storage file.
     * Does not modify existing content.
     *
     * @param shift Shift object to append to the storage file.
     */
    public static void appendToFile(Shift shift) {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            writer.write(formatString(shift) + "\n");
        } catch (IOException e) {
            System.out.println("Error saving shift: " + e.getMessage());
        }
    }
}
