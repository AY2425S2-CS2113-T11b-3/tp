package seedu.nursesched.parser;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.nursesched.exception.NurseSchedException;
import seedu.nursesched.medicine.Medicine;

class MedicineParserTest {

    @Test
    void extractInputs_validAddCommand_returnsCorrectParser() throws NurseSchedException {
        Medicine.getMedicineList().clear();
        String inputString = "medicine add mn/paracetamol q/10";

        MedicineParser medicineParser = MedicineParser.extractInputs(inputString);

        assertNotNull(medicineParser);
        assertEquals("add", medicineParser.getCommand());
        assertEquals("paracetamol", medicineParser.getMedicineName());
        assertEquals(10, medicineParser.getQuantity());
    }

    @Test
    void extractInputs_validRemoveCommand_returnsCorrectParser() throws NurseSchedException {
        Medicine.getMedicineList().clear();
        String inputString = "medicine remove mn/paracetamol q/5";

        MedicineParser medicineParser = MedicineParser.extractInputs(inputString);

        assertNotNull(medicineParser);
        assertEquals("remove", medicineParser.getCommand());
        assertEquals("paracetamol", medicineParser.getMedicineName());
        assertEquals(5, medicineParser.getQuantity());
    }

    @Test
    void extractInputs_validListCommand_returnsCorrectParser() throws NurseSchedException {
        Medicine.getMedicineList().clear();
        String inputString = "medicine list";

        MedicineParser medicineParser = MedicineParser.extractInputs(inputString);

        assertNotNull(medicineParser);
        assertEquals("list", medicineParser.getCommand());
    }

    @Test
    void extractInputs_missingCommand_throwsException() {
        Medicine.getMedicineList().clear();
        String inputString = "medicine";

        NurseSchedException exception = assertThrows(NurseSchedException.class, () -> {
            MedicineParser.extractInputs(inputString);
        });

        assertEquals("Try adding add, remove, edit, list, find, restock or delete!", exception.getMessage());
    }

    @Test
    void getQuantity() {
        Medicine.getMedicineList().clear();
        Medicine medicine = new Medicine(10, "paracetamol");

        int quantity = medicine.getQuantity();

        assertEquals(10, quantity);
    }

    @Test
    void getMedicineName() {
        Medicine.getMedicineList().clear();
        Medicine medicine = new Medicine(10, "paracetamol");

        String medicineName = medicine.getMedicineName();

        assertEquals("paracetamol", medicineName);
    }

    @Test
    void getCommand() throws NurseSchedException {
        Medicine.getMedicineList().clear();
        String inputString = "medicine add mn/paracetamol q/10";
        MedicineParser medicineParser = MedicineParser.extractInputs(inputString);

        String command = medicineParser.getCommand();

        assertEquals("add", command);
    }

    @Test
    void getUpdatedQuantity() {
        Medicine.getMedicineList().clear();
        Medicine medicine = new Medicine(10, "paracetamol");

        medicine.addQuantity(5);
        int updatedQuantity = medicine.getQuantity();

        assertEquals(15, updatedQuantity);
    }
}
