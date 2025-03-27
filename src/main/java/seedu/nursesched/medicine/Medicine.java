package seedu.nursesched.medicine;

import seedu.nursesched.exception.ExceptionMessage;
import seedu.nursesched.exception.NurseSchedException;

import java.util.ArrayList;

public class Medicine {
    protected static ArrayList<Medicine> medicineList = new ArrayList<>();
    private int quantity;
    private String medicineName;

    public Medicine(int quantity, String medicineName) {
        this.quantity = quantity;
        this.medicineName = medicineName;
    }

    public static void addMedicine(int quantity, String medicineName) throws NurseSchedException {
        if (medicineName == null || medicineName.trim().isEmpty()) {
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINEADD_FORMAT);
        }
        if (quantity <= 0) {
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINE_QUANTITY);
        }

        Medicine existingMedicine = findMedicine(medicineName);
        if (existingMedicine != null) {
            existingMedicine.addQuantity(quantity);
            System.out.println(quantity + " more of " + medicineName + " added. New quantity: " + existingMedicine.getQuantity());
        } else {
            Medicine medicine = new Medicine(quantity, medicineName);
            medicineList.add(medicine);
            System.out.println(quantity + " " + medicineName + " added to the list");
        }
    }

    public static void removeMedicine(int quantity, String medicineName) throws NurseSchedException {
        Medicine existingMedicine = findMedicine(medicineName);

        if (existingMedicine == null) {
            throw new NurseSchedException(ExceptionMessage.MEDICINE_INPUT_NOT_FOUND);
        }
        if (quantity <= 0 || quantity > existingMedicine.getQuantity()) {
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINE_QUANTITY);
        }

        existingMedicine.removeQuantity(quantity);
        System.out.println(quantity + " more of " + medicineName + " removed. New quantity: " + existingMedicine.getQuantity());
    }

    public static void deleteMedicine(String medicineName) throws NurseSchedException {
        boolean removed = medicineList.removeIf(medicine -> medicine.getMedicineName().equalsIgnoreCase(medicineName));

        if (removed) {
            System.out.println("Medicine deleted: " + medicineName);
        } else {
            throw new NurseSchedException(ExceptionMessage.MEDICINE_INPUT_NOT_FOUND);
        }
    }


    public static void listMedicine() {
        if (medicineList.isEmpty()) {
            System.out.println("There is no medicine in the list");
            return;
        }
        System.out.println("List of medicine supply:");
        for (int i = 0; i < medicineList.size(); i++) {
            Medicine medicine = medicineList.get(i);
            System.out.printf("%d. %s%n", i + 1, medicine);
        }
    }

    public static Medicine findMedicine(String medicineName) {
        for (Medicine medicine : medicineList) {
            if (medicine.getMedicineName().equalsIgnoreCase(medicineName)) {
                return medicine;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "[" + quantity + "] " + medicineName;
    }

    public static ArrayList<Medicine> getMedicineList() {
        return medicineList;
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

    public void updateQuantity(int amount) {
        this.quantity = amount;
    }

    public void updateMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }
}
