package seedu.nursesched.medicine;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import seedu.nursesched.exception.NurseSchedException;
import seedu.nursesched.parser.MedicineParser;
import seedu.nursesched.storage.MedicineStorage;

class MedicineTest {
    static ArrayList<Medicine> initialMedicineList;

    @BeforeAll
    public static void saveInitialList() {
        initialMedicineList = Medicine.medicineList;
    }

    @AfterAll
    public static void restoreInitialList() {
        MedicineStorage.overwriteSaveFile(initialMedicineList);
    }

    @BeforeEach
    void setUp() {
        Medicine.medicineList = new ArrayList<>();
    }

    // tests for medicine add
    @Test
    void addMedicine_medicineListAdd_expectCorrectOutput() throws NurseSchedException {
        Medicine.getMedicineList().clear();
        String inputString = "medicine add mn/paracetamol q/1";
        MedicineParser medicineParser = MedicineParser.extractInputs(inputString);
        assertNotNull(medicineParser);

        Medicine.addMedicine(medicineParser.getQuantity(), medicineParser.getMedicineName());

        int quantity = medicineParser.getQuantity();
        String medicineName = medicineParser.getMedicineName();

        assertEquals(1, Medicine.getMedicineList().size());
        Medicine addedMedicine = Medicine.getMedicineList().get(0);
        assertEquals(quantity, addedMedicine.getQuantity());
        assertEquals(medicineName, addedMedicine.getMedicineName());
    }

    @Test
    void addMedicine_invalidFormat_throwException() throws NurseSchedException {
        Medicine.getMedicineList().clear();
        String inputString = "medicine add mn/ q/3";
        MedicineParser medicineParser = MedicineParser.extractInputs(inputString);

        NurseSchedException exception = assertThrows(NurseSchedException.class, () -> {
            Medicine.addMedicine(medicineParser.getQuantity(), medicineParser.getMedicineName());
        });

        assertEquals("Invalid medicine add format! Input as: medicine add mn/MEDICINE_NAME q/QUANTITY",
                exception.getMessage());
    }

    // tests for medicine remove
    @Test
    void removeMedicine_medicineListRemove_expectCorrectOutput() throws NurseSchedException {
        Medicine.getMedicineList().clear();
        // add medicine first
        String inputAddString = "medicine add mn/paracetamol q/2";
        MedicineParser medicineAddParser = MedicineParser.extractInputs(inputAddString);
        assertNotNull(medicineAddParser);

        Medicine.addMedicine(medicineAddParser.getQuantity(), medicineAddParser.getMedicineName());
        assertEquals(1, Medicine.getMedicineList().size());
        int quantity = medicineAddParser.getQuantity();
        Medicine addedMedicine = Medicine.getMedicineList().get(0);
        assertEquals(quantity, addedMedicine.getQuantity());

        // remove medicine
        String inputRemoveString = "medicine remove mn/paracetamol q/1";
        MedicineParser medicineRemoveParser = MedicineParser.extractInputs(inputRemoveString);
        assertNotNull(medicineRemoveParser);
        Medicine.removeMedicine(medicineRemoveParser.getQuantity(), medicineRemoveParser.getMedicineName());
        assertEquals(1, Medicine.getMedicineList().size());
        int quantityFinal = quantity - medicineRemoveParser.getQuantity();
        assertEquals(quantityFinal, addedMedicine.getQuantity());
    }

    @Test
    void removeMedicine_invalidFormat_throwException() throws NurseSchedException {
        Medicine.getMedicineList().clear();
        String inputString = "medicine remove mn/abc q/2";
        MedicineParser medicineParser = MedicineParser.extractInputs(inputString);

        NurseSchedException exception = assertThrows(NurseSchedException.class, () -> {
            Medicine.removeMedicine(medicineParser.getQuantity(), medicineParser.getMedicineName());
        });

        assertEquals("Medicine does not exist!",
                exception.getMessage());
    }

    // tests for medicine delete
    @Test
    void deleteMedicine() throws NurseSchedException {
        Medicine.getMedicineList().clear();
        // add medicine first
        String inputAddString = "medicine add mn/paracetamol q/2";
        MedicineParser medicineAddParser = MedicineParser.extractInputs(inputAddString);
        assertNotNull(medicineAddParser);

        Medicine.addMedicine(medicineAddParser.getQuantity(), medicineAddParser.getMedicineName());
        assertEquals(1, Medicine.getMedicineList().size());
        int quantity = medicineAddParser.getQuantity();
        Medicine addedMedicine = Medicine.getMedicineList().get(0);
        assertEquals(quantity, addedMedicine.getQuantity());

        // delete medicine
        String inputDeleteString = "medicine delete mn/paracetamol";
        MedicineParser medicineDeleteParser = MedicineParser.extractInputs(inputDeleteString);
        assertNotNull(medicineDeleteParser);
        Medicine.deleteMedicine(medicineDeleteParser.getMedicineName());
        assertEquals(0, Medicine.getMedicineList().size());
    }

    @Test
    void deleteMedicine_invalidFormat_throwException() throws NurseSchedException {
        Medicine.getMedicineList().clear();
        String inputString = "medicine delete mn/abc";
        MedicineParser medicineParser = MedicineParser.extractInputs(inputString);

        NurseSchedException exception = assertThrows(NurseSchedException.class, () -> {
            Medicine.deleteMedicine(medicineParser.getMedicineName());
        });

        assertEquals("Medicine does not exist!",
                exception.getMessage());
    }

    // tests for medicine find
    @Test
    void findMedicine_existingMedicine_returnsCorrectMedicine() throws NurseSchedException {
        Medicine.getMedicineList().clear();
        String inputString = "medicine add mn/paracetamol q/10";
        MedicineParser medicineParser = MedicineParser.extractInputs(inputString);
        assertNotNull(medicineParser);
        Medicine.addMedicine(medicineParser.getQuantity(), medicineParser.getMedicineName());

        String inputFindString = "medicine find mn/paracetamol";
        MedicineParser medicineFindParser = MedicineParser.extractInputs(inputFindString);
        assertNotNull(medicineFindParser);
        ArrayList<Medicine> foundMedicine = Medicine.findMedicine(medicineFindParser.getMedicineName());

        assertNotNull(foundMedicine);
        assertEquals(medicineParser.getMedicineName(), foundMedicine.get(0).getMedicineName());
        assertEquals(medicineParser.getQuantity(), foundMedicine.get(0).getQuantity());
    }

    @Test
    void findMedicine_nonexistentMedicine_throwException() throws NurseSchedException {
        Medicine.getMedicineList().clear();
        String inputString = "medicine find mn/paracetamol";
        MedicineParser medicineParser = MedicineParser.extractInputs(inputString);

        NurseSchedException exception = assertThrows(NurseSchedException.class, () -> {
            Medicine.findMedicine(medicineParser.getMedicineName());
        });

        assertEquals("Medicine does not exist!",
                exception.getMessage());
    }

    // getters
    @Test
    void getQuantity_validMedicine_returnsCorrectQuantity() throws NurseSchedException {
        Medicine.getMedicineList().clear();
        String inputString = "medicine add mn/paracetamol q/10";
        MedicineParser medicineParser = MedicineParser.extractInputs(inputString);
        assertNotNull(medicineParser);
        Medicine.addMedicine(medicineParser.getQuantity(), medicineParser.getMedicineName());
        Medicine medicine = Medicine.getMedicineList().get(0);
        assertEquals(medicineParser.getQuantity(), medicine.getQuantity());
    }

    @Test
    void getMedicineName_validMedicine_returnsCorrectMedicineName() throws NurseSchedException {
        Medicine.getMedicineList().clear();
        String inputString = "medicine add mn/paracetamol q/10";
        MedicineParser medicineParser = MedicineParser.extractInputs(inputString);
        assertNotNull(medicineParser);
        Medicine.addMedicine(medicineParser.getQuantity(), medicineParser.getMedicineName());
        Medicine medicine = Medicine.getMedicineList().get(0);
        assertEquals(medicineParser.getMedicineName(), medicine.getMedicineName());
    }

    @Test
    void addQuantity_increasesQuantity_correctly() throws NurseSchedException {
        Medicine.getMedicineList().clear();
        String inputString = "medicine add mn/paracetamol q/10";
        MedicineParser medicineParser = MedicineParser.extractInputs(inputString);
        assertNotNull(medicineParser);
        Medicine.addMedicine(medicineParser.getQuantity(), medicineParser.getMedicineName());

        String inputString2 = "medicine add mn/paracetamol q/5";
        MedicineParser medicineParser2 = MedicineParser.extractInputs(inputString2);
        assertNotNull(medicineParser2);
        Medicine.addMedicine(medicineParser2.getQuantity(), medicineParser2.getMedicineName());

        Medicine medicine = Medicine.getMedicineList().get(0);

        assertEquals(medicineParser.getQuantity() + medicineParser2.getQuantity(), medicine.getQuantity());
    }

    @Test
    void removeQuantity_decreasesQuantity_correctly() throws NurseSchedException {
        Medicine.getMedicineList().clear();
        String inputString = "medicine add mn/paracetamol q/10";
        MedicineParser medicineParser = MedicineParser.extractInputs(inputString);
        assertNotNull(medicineParser);
        Medicine.addMedicine(medicineParser.getQuantity(), medicineParser.getMedicineName());

        String inputString2 = "medicine remove mn/paracetamol q/5";
        MedicineParser medicineParser2 = MedicineParser.extractInputs(inputString2);
        assertNotNull(medicineParser2);
        Medicine.removeMedicine(medicineParser2.getQuantity(), medicineParser2.getMedicineName());

        Medicine medicine = Medicine.getMedicineList().get(0);

        assertEquals(medicineParser.getQuantity() - medicineParser2.getQuantity(), medicine.getQuantity());
    }

    @Test
    void getMedicineList_returnsCorrectList() throws NurseSchedException {
        Medicine.getMedicineList().clear();
        String inputString1 = "medicine add mn/paracetamol q/10";
        MedicineParser medicineParser1 = MedicineParser.extractInputs(inputString1);
        assertNotNull(medicineParser1);
        Medicine.addMedicine(medicineParser1.getQuantity(), medicineParser1.getMedicineName());

        String inputString2 = "medicine add mn/albuterol q/5";
        MedicineParser medicineParser2 = MedicineParser.extractInputs(inputString2);
        assertNotNull(medicineParser2);
        Medicine.addMedicine(medicineParser2.getQuantity(), medicineParser2.getMedicineName());

        assertEquals(2, Medicine.getMedicineList().size());
        assertEquals(medicineParser1.getMedicineName(), Medicine.getMedicineList().get(0).getMedicineName());
        assertEquals(medicineParser2.getMedicineName(), Medicine.getMedicineList().get(1).getMedicineName());
    }

    @Test
    void editMedicine_existingMedicine_updatesSuccessfully() throws NurseSchedException {
        Medicine.getMedicineList().clear();
        Medicine.addMedicine(10, "paracetamo");

        String inputString = "medicine edit mn/paracetamo un/paracetamol uq/4";
        MedicineParser medicineParser = MedicineParser.extractInputs(inputString);
        assertNotNull(medicineParser);
        assertEquals("paracetamo", medicineParser.getMedicineName());

        Medicine.editMedicine(medicineParser.getMedicineName(), medicineParser.getUpdatedName(),
                medicineParser.getQuantity());

        Medicine updatedMedicine = Medicine.getMedicineList().get(0);
        assertEquals("paracetamol", updatedMedicine.getMedicineName());
        assertEquals(4, updatedMedicine.getQuantity());
    }

    @Test
    void editMedicine_nonExistentMedicine_doesNotUpdate() throws NurseSchedException {
        Medicine.getMedicineList().clear();

        Medicine.addMedicine(10, "paracetamo");
        Medicine.editMedicine("aspirin", "ibuprofen", 5);

        assertEquals(1, Medicine.getMedicineList().size());
        assertEquals("paracetamo", Medicine.getMedicineList().get(0).getMedicineName());
    }


    @Test
    void restockMedicine_shouldListMedicinesBelowThreshold() {
        Medicine.medicineList.clear();
        Medicine.medicineList.add(new Medicine(3, "Panadol"));
        Medicine.medicineList.add(new Medicine(10, "Amoxicillin"));
        Medicine.medicineList.add(new Medicine(2, "Ibuprofen"));

        assertDoesNotThrow(() -> Medicine.restockMedicine(5));
    }

    @Test
    void restockMedicine_shouldThrowExceptionWhenNoRestockNeeded() {
        Medicine.medicineList.clear();
        Medicine.medicineList.add(new Medicine(10, "Paracetamol"));
        Medicine.medicineList.add(new Medicine(15, "Aspirin"));

        NurseSchedException thrown = assertThrows(NurseSchedException.class, () -> {
            Medicine.restockMedicine(5);
        });

        assertEquals("No restock required!", thrown.getMessage());
    }

    @Test
    void listMedicine_shouldPrintAllMedicines() {
        Medicine.medicineList.clear();
        Medicine.medicineList.add(new Medicine(10, "Panadol"));
        Medicine.medicineList.add(new Medicine(5, "Ibuprofen"));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Medicine.listMedicine();

        String output = outContent.toString();
        assertTrue(output.contains("1. [10] Panadol"));
        assertTrue(output.contains("2. [5] Ibuprofen"));
    }

    @Test
    void listMedicine_shouldPrintNoMedicinesMessageWhenEmpty() {
        Medicine.medicineList.clear();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Medicine.listMedicine();

        String output = outContent.toString();
        assertTrue(output.contains("There is no medicine in the list"));
    }

    @Test
    void removeMedicine_shouldThrowExceptionWhenQuantityExceedsAvailable() {
        Medicine.medicineList.clear();
        Medicine paracetamol = new Medicine(5, "Paracetamol");
        Medicine.medicineList.add(paracetamol);

        NurseSchedException exception = assertThrows(NurseSchedException.class, () ->
                Medicine.removeMedicine(10, "Paracetamol")
        );

        assertEquals("Medicine quantity is too big! Should be lesser than existing quantity.",
                exception.getMessage());
    }

    @Test
    void editMedicine_shouldThrowExceptionWhenDuplicateNameExists() {
        Medicine.medicineList.clear();
        Medicine original = new Medicine(50, "Ibuprofen");
        Medicine duplicate = new Medicine(30, "Panadol");
        Medicine.medicineList.add(original);
        Medicine.medicineList.add(duplicate);

        NurseSchedException exception = assertThrows(NurseSchedException.class, () ->
                Medicine.editMedicine("Ibuprofen", "Panadol", 60)
        );

        assertEquals("Updated medicine name already exists!", exception.getMessage());
    }
}
