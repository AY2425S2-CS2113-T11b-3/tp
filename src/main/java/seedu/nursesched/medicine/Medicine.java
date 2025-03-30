package seedu.nursesched.medicine;

import seedu.nursesched.exception.ExceptionMessage;
import seedu.nursesched.exception.NurseSchedException;


import java.util.ArrayList;

public class Medicine {
    protected static ArrayList<Medicine> medicineList = new ArrayList<>();
    private int quantity;
    private String medicineName;

    public Medicine(int quantity, String medicineName) {
        assert quantity > 0 : "Quantity must be greater than 0";
        assert medicineName != null && !medicineName.trim().isEmpty() : "Medicine name cannot be null or empty";

        this.quantity = quantity;
        this.medicineName = medicineName;
    }

    public static void addMedicine(int quantity, String medicineName) throws NurseSchedException {
        assert medicineName != null : "Medicine name cannot be null";
        assert quantity > 0 : "Quantity must be greater than 0";

        if (medicineName == null || medicineName.trim().isEmpty()) {
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINEADD_FORMAT);
        }
        if (quantity <= 0) {
            throw new NurseSchedException(ExceptionMessage.NEGATIVE_MEDICINE_QUANTITY);
        }


        Medicine existingMedicine = findSpecificMedicine(medicineName);
        if (existingMedicine != null) {
            existingMedicine.addQuantity(quantity);
            System.out.println(quantity + " more of " + medicineName + " added. New quantity: " +
                    existingMedicine.getQuantity());
        } else {
            Medicine medicine = new Medicine(quantity, medicineName);
            medicineList.add(medicine);
            System.out.println(quantity + " " + medicineName + " added to the list");
        }
    }

    public static void removeMedicine(int quantity, String medicineName) throws NurseSchedException {
        assert medicineName != null : "Medicine name cannot be null";
        assert quantity > 0 : "Quantity must be greater than 0";

        Medicine existingMedicine = findSpecificMedicine(medicineName);

        if (existingMedicine == null) {
            throw new NurseSchedException(ExceptionMessage.MEDICINE_NONEXISTENT);
        }
        if (quantity <= 0) {
            throw new NurseSchedException(ExceptionMessage.NEGATIVE_MEDICINE_QUANTITY);
        } else if (quantity > existingMedicine.getQuantity()) {
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINE_QUANTITY);
        }

        existingMedicine.removeQuantity(quantity);
        System.out.println(quantity + " more of " + medicineName + " removed. New quantity: " +
                existingMedicine.getQuantity());
    }

    public static void deleteMedicine(String medicineName) throws NurseSchedException {
        assert medicineName != null : "Medicine name cannot be null";

        boolean removed = medicineList.removeIf(medicine ->
                medicine.getMedicineName().equalsIgnoreCase(medicineName));

        if (removed) {
            System.out.println("Medicine deleted: " + medicineName);
        } else {
            throw new NurseSchedException(ExceptionMessage.MEDICINE_NONEXISTENT);
        }
    }

    public static void listMedicine() {
        if (medicineList.isEmpty()) {
            System.out.println("There is no medicine in the list");
        }
        System.out.println("List of medicine supply:");
        for (int i = 0; i < medicineList.size(); i++) {
            Medicine medicine = medicineList.get(i);
            System.out.printf("%d. %s%n", i + 1, medicine);
        }
    }

    public static ArrayList<Medicine> findMedicine(String medicineName) throws NurseSchedException {
        assert medicineName != null : "Medicine name cannot be null";

        ArrayList<Medicine> matchingMedicine = new ArrayList<>();
        for (Medicine medicine : medicineList) {
            if (medicine.getMedicineName().toLowerCase().contains(medicineName)) {
                matchingMedicine.add(medicine);
            }
        }
        if (matchingMedicine.isEmpty()) {
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
        assert medicineName != null : "Medicine name cannot be null";

        for (Medicine medicine : medicineList) {
            if (medicine.getMedicineName().equalsIgnoreCase(medicineName)) {
                return medicine;
            }
        }
        return null;
    }

    public static void editMedicine(String medicineName, String updatedName, int updatedQuantity) throws
            NurseSchedException {
        assert medicineName != null : "Medicine name cannot be null";
        assert updatedName != null : "Updated name cannot be null";
        assert updatedQuantity > 0 : "Updated quantity must be greater than 0";

        for (Medicine medicine : medicineList) {
            if (medicine.getMedicineName().equalsIgnoreCase(medicineName)) {
                medicine.setMedicineName(updatedName);
                medicine.setQuantity(updatedQuantity);
                System.out.println("Medicine " + medicine.getMedicineName() + " updated.");
                return;
            }
        }
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
        assert amount > 0 : "Amount to add must be greater than 0";
        this.quantity += amount;
    }

    public void removeQuantity(int amount) {
        assert amount > 0 : "Amount to remove must be greater than 0";
        assert quantity >= amount : "Cannot remove more than available quantity";
        this.quantity -= amount;
    }

    public static ArrayList<Medicine> getMedicineList() {
        return medicineList;
    }

    public void setQuantity(int quantity) {
        assert quantity > 0 : "Quantity must be greater than 0";
        this.quantity = quantity;
    }

    public void setMedicineName(String medicineName) {
        assert medicineName != null && !medicineName.trim().isEmpty() : "Medicine name cannot be null or empty";
        this.medicineName = medicineName;
    }
}
