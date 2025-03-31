package seedu.nursesched.medicine;

import seedu.nursesched.exception.ExceptionMessage;
import seedu.nursesched.exception.NurseSchedException;
import seedu.nursesched.storage.MedicineStorage;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import java.util.ArrayList;

/**
 * Represents a medicine in the inventory system.
 * Provides methods for managing the quantity, searching, adding, removing, and editing medicines.
 */
public class Medicine {
    protected static ArrayList<Medicine> medicineList;
    private static final Logger logr = Logger.getLogger("Medicine");

    private int quantity;
    private String medicineName;

    static {
        try {
            LogManager.getLogManager().reset();
            FileHandler fh = new FileHandler("logs/medicine/medicine.log", true);
            fh.setFormatter(new SimpleFormatter());
            logr.addHandler(fh);
            logr.setLevel(Level.ALL);
        } catch (IOException e) {
            logr.log(Level.SEVERE, "File logger not working", e);
        }
        medicineList = MedicineStorage.readFile();
    }

    /**
     * Constructs a new Medicine object with the specified quantity and name.
     *
     * @param quantity     The quantity of the medicine.
     * @param medicineName The name of the medicine.
     * @throws IllegalArgumentException if quantity is less than or equal to 0 or if the medicine name is null or empty.
     */
    public Medicine(int quantity, String medicineName) {
        assert quantity > 0 : "Quantity must be greater than 0";
        assert medicineName != null && !medicineName.trim().isEmpty() : "Medicine name cannot be null or empty";

        this.quantity = quantity;
        this.medicineName = medicineName;
        logr.log(Level.INFO, "Created new medicine: {0}, Quantity: {1}", new Object[]{medicineName, quantity});
    }

    /**
     * Adds a specified quantity of medicine to the inventory.
     * If the medicine already exists, its quantity is updated; otherwise, a new medicine is added.
     *
     * @param quantity     The quantity to add.
     * @param medicineName The name of the medicine.
     * @throws NurseSchedException If the medicine name is invalid or the quantity is not valid.
     */
    public static void addMedicine(int quantity, String medicineName) throws NurseSchedException {
        assert medicineName != null : "Medicine name cannot be null";
        assert quantity > 0 : "Quantity must be greater than 0";

        logr.log(Level.INFO, "Attempting to add medicine: {0}, Quantity: {1}",
                new Object[]{medicineName, quantity});

        if (medicineName.trim().isEmpty()) {
            logr.log(Level.WARNING, "Invalid medicine name: {0}", medicineName);
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINEADD_FORMAT);
        }

        Medicine existingMedicine = findSpecificMedicine(medicineName);
        if (existingMedicine != null) {
            existingMedicine.addQuantity(quantity);
            MedicineStorage.overwriteSaveFile(medicineList);
            logr.log(Level.INFO, "Added {0} more of {1}. New quantity: {2}",
                    new Object[]{quantity, medicineName, existingMedicine.getQuantity()});
            System.out.println(quantity + " more of " + medicineName + " added. New quantity: " +
                    existingMedicine.getQuantity());
        } else {
            Medicine medicine = new Medicine(quantity, medicineName);
            medicineList.add(medicine);
            MedicineStorage.overwriteSaveFile(medicineList);
            logr.log(Level.INFO, "Added new medicine: {0}, Quantity: {1}", new Object[]{medicineName, quantity});
            System.out.println(quantity + " " + medicineName + " added to the list");
        }
    }

    /**
     * Removes a specified quantity of medicine from the inventory.
     *
     * @param quantity     The quantity to remove.
     * @param medicineName The name of the medicine.
     * @throws NurseSchedException If the medicine does not exist or if there is not enough quantity to remove.
     */
    public static void removeMedicine(int quantity, String medicineName) throws NurseSchedException {
        assert medicineName != null : "Medicine name cannot be null";
        assert quantity > 0 : "Quantity must be greater than 0";

        logr.log(Level.INFO, "Attempting to remove medicine: {0}, Quantity: {1}",
                new Object[]{medicineName, quantity});

        Medicine existingMedicine = findSpecificMedicine(medicineName);

        if (existingMedicine == null) {
            logr.log(Level.WARNING, "Medicine not found: {0}", medicineName);
            throw new NurseSchedException(ExceptionMessage.MEDICINE_NONEXISTENT);
        }
        if (quantity > existingMedicine.getQuantity()) {
            logr.log(Level.WARNING, "Not enough stock to remove: {0} of {1}, Available: {2}",
                    new Object[]{quantity, medicineName, existingMedicine.getQuantity()});
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINE_QUANTITY);
        }

        existingMedicine.removeQuantity(quantity);
        MedicineStorage.overwriteSaveFile(medicineList);
        logr.log(Level.INFO, "Removed {0} of {1}. New quantity: {2}",
                new Object[]{quantity, medicineName, existingMedicine.getQuantity()});
        System.out.println(quantity + " more of " + medicineName + " removed. New quantity: " +
                existingMedicine.getQuantity());
    }

    /**
     * Deletes a medicine from the inventory based on its name.
     *
     * @param medicineName The name of the medicine to delete.
     * @throws NurseSchedException If the medicine does not exist.
     */
    public static void deleteMedicine(String medicineName) throws NurseSchedException {
        assert medicineName != null : "Medicine name cannot be null";

        logr.log(Level.INFO, "Attempting to delete medicine: {0}", medicineName);

        boolean removed = medicineList.removeIf(medicine ->
                medicine.getMedicineName().equalsIgnoreCase(medicineName));

        if (removed) {
            MedicineStorage.overwriteSaveFile(medicineList);
            logr.log(Level.INFO, "Medicine deleted: {0}", medicineName);
            System.out.println("Medicine deleted: " + medicineName);
        } else {
            logr.log(Level.WARNING, "Medicine not found: {0}", medicineName);
            throw new NurseSchedException(ExceptionMessage.MEDICINE_NONEXISTENT);
        }
    }

    /**
     * Lists all medicines in the inventory.
     * If the list is empty, prints a message indicating no medicines are available.
     */
    public static void listMedicine() {
        logr.log(Level.INFO, "Listing all medicines");

        if (medicineList.isEmpty()) {
            System.out.println("There is no medicine in the list");
            logr.log(Level.INFO, "No medicines found in the list");
        }
        System.out.println("List of medicine supply:");
        for (int i = 0; i < medicineList.size(); i++) {
            Medicine medicine = medicineList.get(i);
            System.out.printf("%d. %s%n", i + 1, medicine);
        }
    }

    /**
     * Finds all medicines that match the given name (case-insensitive).
     *
     * @param medicineName The name or partial name of the medicine to search for.
     * @return A list of matching medicines.
     * @throws NurseSchedException If no matching medicines are found.
     */
    public static ArrayList<Medicine> findMedicine(String medicineName) throws NurseSchedException {
        assert medicineName != null : "Medicine name cannot be null";

        logr.log(Level.INFO, "Searching for medicine containing: {0}", medicineName);

        ArrayList<Medicine> matchingMedicine = new ArrayList<>();
        for (Medicine medicine : medicineList) {
            if (medicine.getMedicineName().toLowerCase().contains(medicineName)) {
                matchingMedicine.add(medicine);
            }
        }
        if (matchingMedicine.isEmpty()) {
            logr.log(Level.WARNING, "No medicines found matching: {0}", medicineName);
            throw new NurseSchedException(ExceptionMessage.MEDICINE_NONEXISTENT);
        } else {
            for (int i = 0; i < matchingMedicine.size(); i++) {
                Medicine medicine = matchingMedicine.get(i);
                System.out.printf("%d. %s%n", i + 1, medicine);
            }
        }
        return matchingMedicine;
    }

    /**
     * Finds a specific medicine based on its name.
     *
     * @param medicineName The name of the medicine.
     * @return The Medicine object if found, otherwise null.
     */
    public static Medicine findSpecificMedicine(String medicineName) {
        assert medicineName != null : "Medicine name cannot be null";

        logr.log(Level.INFO, "Finding specific medicine: {0}", medicineName);

        for (Medicine medicine : medicineList) {
            if (medicine.getMedicineName().equalsIgnoreCase(medicineName)) {
                logr.log(Level.INFO, "Found medicine: {0}", medicineName);
                return medicine;
            }
        }
        logr.log(Level.WARNING, "Medicine not found: {0}", medicineName);
        return null;
    }

    /**
     * Edits the name and/or quantity of a specific medicine.
     *
     * @param medicineName    The name of the medicine to edit.
     * @param updatedName     The new name for the medicine.
     * @param updatedQuantity The new quantity for the medicine.
     * @throws NurseSchedException If the medicine cannot be found or the new quantity is invalid.
     */
    public static void editMedicine(String medicineName, String updatedName, int updatedQuantity) throws
            NurseSchedException {
        assert medicineName != null : "Medicine name cannot be null";
        assert updatedName != null : "Updated name cannot be null";
        assert updatedQuantity > 0 : "Updated quantity must be greater than 0";

        logr.log(Level.INFO, "Attempting to edit medicine: {0}", medicineName);

        for (Medicine medicine : medicineList) {
            if (medicine.getMedicineName().equalsIgnoreCase(medicineName)) {
                medicine.setMedicineName(updatedName);
                medicine.setQuantity(updatedQuantity);
                MedicineStorage.overwriteSaveFile(medicineList);
                logr.log(Level.INFO, "Updated medicine: {0} to new name: {1}, new quantity: {2}",
                        new Object[]{medicineName, updatedName, updatedQuantity});
                System.out.println("Medicine " + medicine.getMedicineName() + " updated.");
                return;
            }
        }
        logr.log(Level.WARNING, "Medicine not found: {0}", medicineName);
        System.out.println("Medicine " + medicineName + " not found.");
    }

    /**
     * Returns a string representation of the medicine object, including its quantity and name.
     *
     * @return A string representation of the medicine.
     */
    @Override
    public String toString() {
        return "[" + quantity + "] " + medicineName;
    }

    /**
     * Returns the current quantity of the medicine.
     *
     * @return The quantity of the medicine.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Returns the name of the medicine.
     *
     * @return The name of the medicine.
     */
    public String getMedicineName() {
        return medicineName;
    }

    /**
     * Adds a specified amount to the current quantity of the medicine.
     *
     * @param amount The amount to add.
     * @throws IllegalArgumentException If the amount is less than or equal to 0.
     */
    public void addQuantity(int amount) {
        assert amount > 0 : "Amount to add must be greater than 0";
        this.quantity += amount;
    }

    /**
     * Removes a specified amount from the current quantity of the medicine.
     *
     * @param amount The amount to remove.
     * @throws IllegalArgumentException If the amount is less than or equal to 0 or if trying to remove more than
     * the available quantity.
     */
    public void removeQuantity(int amount) {
        assert amount > 0 : "Amount to remove must be greater than 0";
        assert quantity >= amount : "Cannot remove more than available quantity";
        this.quantity -= amount;
    }

    /**
     * Returns the list of all medicines.
     *
     * @return The list of all the medicines.
     */
    public static ArrayList<Medicine> getMedicineList() {
        return medicineList;
    }

    /**
     * Sets a specified amount as the current quantity of the medicine.
     *
     * @param quantity The new quantity to be set as the quantity.
     */
    public void setQuantity(int quantity) {
        assert quantity > 0 : "Quantity must be greater than 0";
        this.quantity = quantity;
    }

    /**
     * Sets a specified medicine name as the current medicine name.
     *
     * @param medicineName The new medicine name to be set as the current medicine name.
     */
    public void setMedicineName(String medicineName) {
        assert medicineName != null && !medicineName.trim().isEmpty() : "Medicine name cannot be null or empty";
        this.medicineName = medicineName;
    }
}
