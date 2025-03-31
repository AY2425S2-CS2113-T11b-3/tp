package seedu.nursesched.medicine;

import seedu.nursesched.exception.ExceptionMessage;
import seedu.nursesched.exception.NurseSchedException;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import java.util.ArrayList;

public class Medicine {
    protected static ArrayList<Medicine> medicineList = new ArrayList<>();
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
    }

    public Medicine(int quantity, String medicineName) {
        this.quantity = quantity;
        this.medicineName = medicineName;
        logr.log(Level.INFO, "Created new medicine: {0}, Quantity: {1}", new Object[]{medicineName, quantity});
    }

    public static void addMedicine(int quantity, String medicineName) throws NurseSchedException {
        logr.log(Level.INFO, "Attempting to add medicine: {0}, Quantity: {1}", new Object[]{medicineName, quantity});

        if (medicineName == null || medicineName.trim().isEmpty()) {
            logr.log(Level.WARNING, "Invalid medicine name: {0}", medicineName);
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINEADD_FORMAT);
        }
        if (quantity <= 0) {
            logr.log(Level.WARNING, "Invalid quantity: {0} for medicine: {1}", new Object[]{quantity, medicineName});
            throw new NurseSchedException(ExceptionMessage.NEGATIVE_MEDICINE_QUANTITY);
        }


        Medicine existingMedicine = findSpecificMedicine(medicineName);
        if (existingMedicine != null) {
            existingMedicine.addQuantity(quantity);
            logr.log(Level.INFO, "Added {0} more of {1}. New quantity: {2}", new Object[]{quantity, medicineName, existingMedicine.getQuantity()});
            System.out.println(quantity + " more of " + medicineName + " added. New quantity: " +
                    existingMedicine.getQuantity());
        } else {
            Medicine medicine = new Medicine(quantity, medicineName);
            medicineList.add(medicine);
            logr.log(Level.INFO, "Added new medicine: {0}, Quantity: {1}", new Object[]{medicineName, quantity});
            System.out.println(quantity + " " + medicineName + " added to the list");
        }
    }

    public static void removeMedicine(int quantity, String medicineName) throws NurseSchedException {
        logr.log(Level.INFO, "Attempting to remove medicine: {0}, Quantity: {1}", new Object[]{medicineName, quantity});
        Medicine existingMedicine = findSpecificMedicine(medicineName);

        if (existingMedicine == null) {
            logr.log(Level.WARNING, "Medicine not found: {0}", medicineName);
            throw new NurseSchedException(ExceptionMessage.MEDICINE_NONEXISTENT);
        }
        if (quantity <= 0) {
            logr.log(Level.WARNING, "Invalid quantity: {0} for medicine: {1}", new Object[]{quantity, medicineName});
            throw new NurseSchedException(ExceptionMessage.NEGATIVE_MEDICINE_QUANTITY);
        } else if (quantity > existingMedicine.getQuantity()) {
            logr.log(Level.WARNING, "Not enough stock to remove: {0} of {1}, Available: {2}", new Object[]{quantity, medicineName, existingMedicine.getQuantity()});
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINE_QUANTITY);
        }

        existingMedicine.removeQuantity(quantity);
        logr.log(Level.INFO, "Removed {0} of {1}. New quantity: {2}", new Object[]{quantity, medicineName, existingMedicine.getQuantity()});
        System.out.println(quantity + " more of " + medicineName + " removed. New quantity: " +
                existingMedicine.getQuantity());
    }

    public static void deleteMedicine(String medicineName) throws NurseSchedException {
        logr.log(Level.INFO, "Attempting to delete medicine: {0}", medicineName);

        boolean removed = medicineList.removeIf(medicine ->
                medicine.getMedicineName().equalsIgnoreCase(medicineName));

        if (removed) {
            logr.log(Level.INFO, "Medicine deleted: {0}", medicineName);
            System.out.println("Medicine deleted: " + medicineName);
        } else {
            logr.log(Level.WARNING, "Medicine not found: {0}", medicineName);
            throw new NurseSchedException(ExceptionMessage.MEDICINE_NONEXISTENT);
        }
    }

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

    public static ArrayList<Medicine> findMedicine(String medicineName) throws NurseSchedException {
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

    public static Medicine findSpecificMedicine(String medicineName) {
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

    public static void editMedicine(String medicineName, String updatedName, int updatedQuantity) throws
            NurseSchedException {
        logr.log(Level.INFO, "Attempting to edit medicine: {0}", medicineName);

        for (Medicine medicine : medicineList) {
            if (medicine.getMedicineName().equalsIgnoreCase(medicineName)) {
                medicine.setMedicineName(updatedName);
                medicine.setQuantity(updatedQuantity);
                logr.log(Level.INFO, "Updated medicine: {0} to new name: {1}, new quantity: {2}", new Object[]{medicineName, updatedName, updatedQuantity});
                System.out.println("Medicine " + medicine.getMedicineName() + " updated.");
                return;
            }
        }
        logr.log(Level.WARNING, "Medicine not found: {0}", medicineName);
        System.out.println("Medicine " + medicineName + " not found.");
    }

    @Override
    public String toString() {
        return "[" + quantity + "] " + medicineName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void addQuantity(int amount) {
        this.quantity += amount;
    }

    public void removeQuantity(int amount) {
        this.quantity -= amount;
    }

    public static ArrayList<Medicine> getMedicineList() {
        return medicineList;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }
}
