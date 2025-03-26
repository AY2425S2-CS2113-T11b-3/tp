package seedu.nursesched.medicine;

import java.util.ArrayList;

public class Medicine {
    protected static ArrayList<Medicine> medicineList = new ArrayList<>();
    private int quantity;
    private final String medicineName;

    public Medicine(int quantity, String medicineName) {
        this.quantity = quantity;
        this.medicineName = medicineName;
    }

    public static void addMedicine(int quantity, String medicineName) {
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

    public static void removeMedicine(int quantity, String medicineName) {
        Medicine existingMedicine = findMedicine(medicineName);
        if (existingMedicine != null) {
            existingMedicine.removeQuantity(quantity);
            System.out.println(quantity + " more of " + medicineName + " removed. New quantity: " + existingMedicine.getQuantity());
        } else {
            System.out.println("Medicine does not exist");
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

    public static Medicine findMedicine(String name) {
        for (Medicine medicine : medicineList) {
            if (medicine.getMedicineName().equalsIgnoreCase(name)) {
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
}
