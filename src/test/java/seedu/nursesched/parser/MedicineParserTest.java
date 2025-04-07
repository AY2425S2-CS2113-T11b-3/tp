package seedu.nursesched.parser;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.nursesched.exception.NurseSchedException;
import seedu.nursesched.medicine.Medicine;
import seedu.nursesched.storage.MedicineStorage;

class MedicineParserTest {
    static ArrayList<Medicine> initialMedicineList;

    @BeforeAll
    public static void saveInitialList() {
        initialMedicineList = Medicine.getMedicineList();
    }

    @AfterAll
    public static void restoreInitialList() {
        MedicineStorage.overwriteSaveFile(initialMedicineList);
    }

    @BeforeEach
    void setUp() {
        Medicine.medicineList = new ArrayList<>();
    }

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

    @Test
    void extractInputs_invalidQuantityFormat_throwsException() {
        String inputString = "medicine add mn/paracetamol q/abc";

        NurseSchedException exception = assertThrows(NurseSchedException.class, () -> {
            MedicineParser.extractInputs(inputString);
        });

        assertEquals("Invalid quantity format! Enter a numeric value.", exception.getMessage());
    }

    @Test
    void extractInputs_missingMedicineName_throwsException() {
        String inputString = "medicine add q/5";

        NurseSchedException exception = assertThrows(NurseSchedException.class, () -> {
            MedicineParser.extractInputs(inputString);
        });

        assertEquals("Invalid medicine add format! Input as: medicine add mn/MEDICINE_NAME q/QUANTITY",
                exception.getMessage());
    }

    @Test
    void extractInputs_missingQuantity_throwsException() {
        String inputString = "medicine add mn/paracetamol";

        NurseSchedException exception = assertThrows(NurseSchedException.class, () -> {
            MedicineParser.extractInputs(inputString);
        });

        assertEquals("Invalid medicine add format! Input as: medicine add mn/MEDICINE_NAME q/QUANTITY",
                exception.getMessage());
    }

    @Test
    void extractInputs_validDeleteCommand_returnsCorrectParser() throws NurseSchedException {
        String inputString = "medicine delete mn/paracetamol";

        MedicineParser medicineParser = MedicineParser.extractInputs(inputString);

        assertEquals("delete", medicineParser.getCommand());
        assertEquals("paracetamol", medicineParser.getMedicineName());
    }

    @Test
    void extractInputs_validEditCommand_returnsCorrectParser() {
        String inputString = "medicine edit mn/paracetamol q/20";

        NurseSchedException exception = assertThrows(NurseSchedException.class, () -> {
            MedicineParser.extractInputs(inputString);
        });

        assertEquals("Invalid medicine edit format! Input as: medicine edit mn/MEDICINE_NAME un/UPDATE_NAME" +
                " uq/UPDATED_QUANTITY", exception.getMessage());
    }

    @Test
    void extractInputs_validRestockCommand_returnsCorrectParser() throws NurseSchedException {
        String inputString = "medicine restock q/50";

        MedicineParser medicineParser = MedicineParser.extractInputs(inputString);

        assertNotNull(medicineParser);
        assertEquals("restock", medicineParser.getCommand());
        assertEquals(50, medicineParser.getQuantity());
    }

    @Test
    void extractInputs_validFindCommand_returnsCorrectParser() throws NurseSchedException {
        String inputString = "medicine find mn/paracetamol";

        MedicineParser medicineParser = MedicineParser.extractInputs(inputString);

        assertNotNull(medicineParser);
        assertEquals("find", medicineParser.getCommand());
        assertEquals("paracetamol", medicineParser.getMedicineName());
    }

    @Test
    void extractInputs_emptyInput_throwsException() {
        String inputString = "   ";

        NurseSchedException exception = assertThrows(NurseSchedException.class, () -> {
            MedicineParser.extractInputs(inputString);
        });

        assertEquals("Input line cannot be empty!", exception.getMessage());
    }

    @Test
    void extractInputs_invalidAddCommandFormat_throwsException() {
        String inputString = "medicine add mn/paracetamol";

        NurseSchedException exception = assertThrows(NurseSchedException.class, () -> {
            MedicineParser.extractInputs(inputString);
        });

        assertEquals("Invalid medicine add format! Input as: medicine add mn/MEDICINE_NAME q/QUANTITY",
                exception.getMessage());
    }

    @Test
    void extractInputs_quantityTooLarge_throwsException() {
        String inputString = "medicine add mn/paracetamol q/12345678901";

        NurseSchedException exception = assertThrows(NurseSchedException.class, () -> {
            MedicineParser.extractInputs(inputString);
        });

        assertEquals("Quantity is too large! Max allowed is 2,147,483,647.", exception.getMessage());
    }

    @Test
    void extractInputs_quantityExceedsMaxValue_throwsException() {
        String inputString = "medicine add mn/paracetamol q/2147483648";

        NurseSchedException exception = assertThrows(NurseSchedException.class, () -> {
            MedicineParser.extractInputs(inputString);
        });

        assertEquals("Quantity is too large! Max allowed is 2,147,483,647.", exception.getMessage());
    }

    @Test
    void extractInputs_negativeQuantity_throwsException() {
        String inputString = "medicine add mn/paracetamol q/-5";

        NurseSchedException exception = assertThrows(NurseSchedException.class, () -> {
            MedicineParser.extractInputs(inputString);
        });

        assertEquals("Medicine quantity must be a positive integer!", exception.getMessage());
    }

    @Test
    void extractInputs_zeroQuantity_throwsException() {
        String inputString = "medicine add mn/paracetamol q/0";

        NurseSchedException exception = assertThrows(NurseSchedException.class, () -> {
            MedicineParser.extractInputs(inputString);
        });

        assertEquals("Medicine quantity must be a positive integer!", exception.getMessage());
    }
}
