package seedu.nursesched.parser;

import seedu.nursesched.exception.ExceptionMessage;
import seedu.nursesched.exception.NurseSchedException;

public class MedicineParser extends Parser {
    private final String command;
    private final String medicineName;
    private final int quantity;
    private final String updatedName;

    public MedicineParser(String command, String medicineName, int quantity, String updatedName) {
        assert command != null && !command.trim().isEmpty() : "Command cannot be null or empty";
        assert medicineName != null : "Medicine name cannot be null";
        assert quantity >= 0 : "Quantity cannot be negative";
        assert updatedName != null : "Updated name cannot be null";

        this.command = command;
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.updatedName = updatedName;
    }

    public static MedicineParser extractInputs(String line) throws NurseSchedException {
        assert line != null : "Input line cannot be null";

        if (line == null || line.trim().isEmpty()) {
            throw new NurseSchedException(ExceptionMessage.INPUT_EMPTY);
        }

        line = line.trim().toLowerCase();
        String[] parts = line.split(" ", 2);

        if (parts.length < 2) {
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINE_FORMAT);
        }

        String remaining = parts[1];
        String command = "";

        String[] commandParts = remaining.split(" ", 2);
        command = commandParts[0];
        remaining = (commandParts.length > 1) ? commandParts[1] : "";

        assert command != null && !command.trim().isEmpty() : "Command cannot be null or empty";

        if (command.equals("add")) {
            return getMedicineAddParser(remaining, command);
        } else if (command.equals("list")) {
            return new MedicineParser("list", "", 0, "");
        } else if (command.equals("remove")) {
            return getMedicineRemoveParser(remaining, command);
        } else if (command.equals("find")) {
            return getMedicineFindParser(remaining, command);
        } else if (command.equals("delete")) {
            return getMedicineDeleteParser(remaining, command);
        } else if (command.equals("edit")) {
            return getMedicineEditParser(remaining, command);
        } else {
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINE_FORMAT);
        }
    }

    private static MedicineParser getMedicineAddParser(String remaining, String command) throws NurseSchedException {
        assert remaining != null : "Remaining string cannot be null";
        assert command != null : "Command cannot be null";

        String medicineName;
        int quantity;

        if (!remaining.contains("mn/") || !remaining.contains("q/")) {
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINEADD_FORMAT);
        }

        try {
            medicineName = extractValue(remaining, "mn/", "q/");
            assert !medicineName.trim().isEmpty() : "Medicine name cannot be empty";
        } catch (RuntimeException e) {
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINEADD_FORMAT);
        }

        try {
            quantity = Integer.parseInt(extractValue(remaining, "q/", null));
            assert quantity > 0 : "Quantity must be greater than zero";
        } catch (RuntimeException e) {
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINEADD_FORMAT);
        }


        return new MedicineParser(command, medicineName, quantity, "");
    }

    private static MedicineParser getMedicineRemoveParser(String remaining, String command) throws NurseSchedException {
        assert remaining != null : "Remaining string cannot be null";
        assert command != null : "Command cannot be null";

        String medicineName;
        int quantity;

        if (!remaining.contains("mn/") || !remaining.contains("q/")) {
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINEREMOVE_FORMAT);
        }


        try {
            medicineName = extractValue(remaining, "mn/", "q/");
            assert !medicineName.trim().isEmpty() : "Medicine name cannot be empty";
        } catch (RuntimeException e) {
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINEREMOVE_FORMAT);
        }


        try {
            String quantityString = String.valueOf(Integer.parseInt(extractValue(remaining, "q/",
                    null)));
            if (quantityString.trim().isEmpty()) {
                throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINEREMOVE_FORMAT);
            }
            quantity = Integer.parseInt(quantityString);
            assert quantity > 0 : "Quantity must be greater than zero";
        } catch (NumberFormatException e) {
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINEREMOVE_FORMAT);
        }


        return new MedicineParser(command, medicineName, quantity, "");
    }

    private static MedicineParser getMedicineFindParser(String remaining, String command) throws NurseSchedException {
        assert remaining != null : "Remaining string cannot be null";
        assert command != null : "Command cannot be null";
        String medicineName;

        try {
            medicineName = extractValue(remaining, "mn/", null);
        } catch (RuntimeException e) {
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINEFIND_FORMAT);
        }

        return new MedicineParser(command, medicineName, 0, "");
    }

    private static MedicineParser getMedicineDeleteParser(String remaining, String command) throws NurseSchedException {
        assert remaining != null : "Remaining string cannot be null";
        assert command != null : "Command cannot be null";
        String medicineName;

        try {
            medicineName = extractValue(remaining, "mn/", null);
        } catch (RuntimeException e) {
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINEDELETE_FORMAT);
        }

        return new MedicineParser(command, medicineName, 0, "");
    }

    private static MedicineParser getMedicineEditParser(String remaining, String command) throws NurseSchedException {
        assert remaining != null : "Remaining string cannot be null";
        assert command != null : "Command cannot be null";
        String medicineName;
        String updatedName;
        int updatedQuantity;

        try {
            medicineName = extractValue(remaining, "mn/", "un/");
        } catch (RuntimeException e) {
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINEEDIT_FORMAT);
        }

        try {
            updatedName = extractValue(remaining, "un/", "uq/");
        } catch (RuntimeException e) {
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINEEDIT_FORMAT);
        }

        try {
            updatedQuantity = Integer.parseInt(extractValue(remaining, "uq/", null));
        } catch (RuntimeException e) {
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINEEDIT_FORMAT);
        }

        return new MedicineParser(command, medicineName, updatedQuantity, updatedName);
    }

    private static String extractValue(String input, String startMarker, String endMarker) {
        assert input != null : "Input string must not be null";
        assert startMarker != null : "Start marker must not be null";

        int start = input.indexOf(startMarker);
        if (start == -1) {
            throw new RuntimeException("Missing required marker: " + startMarker);
        }

        start += startMarker.length();
        int end = (endMarker != null) ? input.indexOf(endMarker, start) : -1;

        return (end == -1) ? input.substring(start).trim() : input.substring(start, end).trim();
    }

    public int getQuantity() {
        assert quantity >= 0 : "Quantity cannot be negative";
        return quantity;
    }

    public String getMedicineName() {
        assert medicineName != null : "Medicine name cannot be null";
        return medicineName;
    }

    public String getCommand() {
        assert command != null : "Command cannot be null";
        return command;
    }

    public String getUpdatedName() {
        assert updatedName != null : "Updated name cannot be null";
        return updatedName;
    }
}
