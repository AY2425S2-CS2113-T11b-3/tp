package seedu.nursesched.parser;

import seedu.nursesched.exception.ExceptionMessage;
import seedu.nursesched.exception.NurseSchedException;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Parses and extracts information from user commands related to medicine management.
 * Supports operations like add, remove, edit, delete, list, restock, and find.
 */
public class MedicineParser extends Parser {
    private static final Logger logr = Logger.getLogger("MedicineParser");
    private final String command;
    private final String medicineName;
    private final int quantity;
    private final String updatedName;

    static {
        try {
            LogManager.getLogManager().reset();
            FileHandler fh = new FileHandler("logs/parser/medicineParser.log", true);
            fh.setFormatter(new SimpleFormatter());
            logr.addHandler(fh);
            logr.setLevel(Level.ALL);
        } catch (IOException e) {
            logr.log(Level.SEVERE, "File logger not working", e);
        }
    }

    /**
     * Constructs a {@code MedicineParser} instance with the given command details.
     *
     * @param command      The command type (e.g., "add", "delete").
     * @param medicineName The name of the medicine involved.
     * @param quantity     The quantity of the medicine, if applicable.
     * @param updatedName  The updated name of the medicine (used for "edit" command).
     */
    public MedicineParser(String command, String medicineName, int quantity, String updatedName) {
        assert command != null && !command.trim().isEmpty() : "Command cannot be null or empty";
        assert medicineName != null : "Medicine name cannot be null";
        assert quantity >= 0 : "Quantity cannot be negative";
        assert updatedName != null : "Updated name cannot be null";

        this.command = command;
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.updatedName = updatedName;
        logr.log(Level.INFO, "Initialized MedicineParser with command: {0}, medicineName: {1}, quantity: {2}," +
                        " updatedName: {3}",
                new Object[]{command, medicineName, quantity, updatedName});
    }

    /**
     * Extracts the command and parameters from a raw user input string.
     *
     * @param line The full command line input from the user.
     * @return A {@code MedicineParser} instance populated with parsed command details.
     * @throws NurseSchedException If the input is invalid or incorrectly formatted.
     */
    public static MedicineParser extractInputs(String line) throws NurseSchedException {
        assert line != null : "Input line cannot be null";

        logr.log(Level.INFO, "Extracting inputs from line: {0}", line);

        if (line.trim().isEmpty()) {
            logr.log(Level.WARNING, "Input line is empty");
            throw new NurseSchedException(ExceptionMessage.INPUT_EMPTY);
        }

        line = line.trim().toLowerCase();
        String[] parts = line.split(" ", 2);

        if (parts.length < 2) {
            logr.log(Level.WARNING, "Invalid medicine command format: {0}", line);
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINE_FORMAT);
        }

        String remaining = parts[1];
        String command = "";

        String[] commandParts = remaining.split(" ", 2);
        command = commandParts[0];
        remaining = (commandParts.length > 1) ? commandParts[1] : "";

        assert command != null && !command.trim().isEmpty() : "Command cannot be null or empty";

        logr.log(Level.INFO, "Command extracted: {0}, Remaining: {1}", new Object[]{command, remaining});

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
        } else if (command.equals("restock")) {
            return getMedicineRestockParser(remaining, command);
        } else {
            logr.log(Level.WARNING, "Unknown command received: {0}", command);
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINE_FORMAT);
        }
    }

    /**
     * Parses an "add" command to extract the medicine name and quantity.
     *
     * @param remaining Remaining string after the "add" keyword.
     * @param command   The command type ("add").
     * @return A {@code MedicineParser} instance with extracted values.
     * @throws NurseSchedException If the input format is invalid or missing required values.
     */
    private static MedicineParser getMedicineAddParser(String remaining, String command) throws NurseSchedException {
        assert remaining != null : "Remaining string cannot be null";
        assert command != null : "Command cannot be null";

        logr.log(Level.INFO, "Parsing add command with remaining: {0}", remaining);

        String medicineName;
        int quantity;

        if (!remaining.contains("mn/") || !remaining.contains("q/")) {
            logr.log(Level.WARNING, "Invalid add format: {0}", remaining);
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINEADD_FORMAT);
        }

        try {
            medicineName = extractValue(remaining, "mn/", "q/");
            quantity = Integer.parseInt(extractValue(remaining, "q/", null));
            if (quantity <= 0) {
                throw new NurseSchedException(ExceptionMessage.NEGATIVE_MEDICINE_QUANTITY);
            }
            quantity = Integer.parseInt(extractValue(remaining, "q/", null));
            logr.log(Level.INFO, "Extracted medicineName: {0}, quantity: {1}",
                    new Object[]{medicineName, quantity});
            return new MedicineParser(command, medicineName, quantity, "");
        } catch (RuntimeException e) {
            logr.log(Level.SEVERE, "Failed to parse add command: {0}", remaining);
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINEADD_FORMAT);
        }
    }

    /**
     * Parses a "remove" command to extract the medicine name and quantity.
     *
     * @param remaining Remaining string after the "remove" keyword.
     * @param command   The command type ("remove").
     * @return A {@code MedicineParser} instance with extracted values.
     * @throws NurseSchedException If the input format is invalid or missing required values.
     */
    private static MedicineParser getMedicineRemoveParser(String remaining, String command) throws NurseSchedException {
        assert remaining != null : "Remaining string cannot be null";
        assert command != null : "Command cannot be null";

        logr.log(Level.INFO, "Parsing remove command with remaining: {0}", remaining);

        String medicineName;
        int quantity;

        if (!remaining.contains("mn/") || !remaining.contains("q/")) {
            logr.log(Level.WARNING, "Invalid add format: {0}", remaining);
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINEREMOVE_FORMAT);
        }

        try {
            medicineName = extractValue(remaining, "mn/", "q/");
            assert !medicineName.trim().isEmpty() : "Medicine name cannot be empty";
            String quantityString = String.valueOf(Integer.parseInt(extractValue(remaining, "q/",
                    null)));
            if (quantityString.trim().isEmpty()) {
                throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINEREMOVE_FORMAT);
            }
            quantity = Integer.parseInt(quantityString);
            if (quantity <= 0) {
                throw new NurseSchedException(ExceptionMessage.NEGATIVE_MEDICINE_QUANTITY);
            }
            logr.log(Level.INFO, "Extracted medicineName: {0}, quantity: {1}",
                    new Object[]{medicineName, quantity});
            return new MedicineParser(command, medicineName, quantity, "");
        } catch (RuntimeException e) {
            logr.log(Level.SEVERE, "Failed to parse remove command: {0}", remaining);
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINEREMOVE_FORMAT);
        }
    }

    /**
     * Parses a "find" command to extract the medicine name.
     *
     * @param remaining Remaining string after the "find" keyword.
     * @param command   The command type ("find").
     * @return A {@code MedicineParser} instance with extracted values.
     * @throws NurseSchedException If the input format is invalid or missing required values.
     */
    private static MedicineParser getMedicineFindParser(String remaining, String command) throws NurseSchedException {
        assert remaining != null : "Remaining string cannot be null";
        assert command != null : "Command cannot be null";

        logr.log(Level.INFO, "Parsing find command with remaining: {0}", remaining);

        String medicineName;

        try {
            medicineName = extractValue(remaining, "mn/", null);
            logr.log(Level.INFO, "Extracted medicineName: {0}", new Object[]{medicineName});
            return new MedicineParser(command, medicineName, 0, "");
        } catch (RuntimeException e) {
            logr.log(Level.SEVERE, "Failed to parse find command: {0}", remaining);
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINEFIND_FORMAT);
        }
    }

    /**
     * Parses a "delete" command to extract the medicine name.
     *
     * @param remaining Remaining string after the "delete" keyword.
     * @param command   The command type ("delete").
     * @return A {@code MedicineParser} instance with extracted values.
     * @throws NurseSchedException If the input format is invalid or missing required values.
     */
    private static MedicineParser getMedicineDeleteParser(String remaining, String command) throws NurseSchedException {
        assert remaining != null : "Remaining string cannot be null";
        assert command != null : "Command cannot be null";

        logr.log(Level.INFO, "Parsing delete command with remaining: {0}", remaining);

        String medicineName;

        try {
            medicineName = extractValue(remaining, "mn/", null);
            logr.log(Level.INFO, "Extracted medicineName: {0}", new Object[]{medicineName});
            return new MedicineParser(command, medicineName, 0, "");
        } catch (RuntimeException e) {
            logr.log(Level.SEVERE, "Failed to parse delete command: {0}", remaining);
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINEDELETE_FORMAT);
        }
    }

    /**
     * Parses an "edit" command to extract original and updated medicine details.
     *
     * @param remaining Remaining string after the "edit" keyword.
     * @param command   The command type ("edit").
     * @return A {@code MedicineParser} instance with extracted values.
     * @throws NurseSchedException If the input format is invalid or missing required values.
     */
    private static MedicineParser getMedicineEditParser(String remaining, String command) throws NurseSchedException {
        assert remaining != null : "Remaining string cannot be null";
        assert command != null : "Command cannot be null";

        logr.log(Level.INFO, "Parsing edit command with remaining: {0}", remaining);

        String medicineName;
        String updatedName;
        int updatedQuantity;

        try {
            medicineName = extractValue(remaining, "mn/", "un/");
            updatedName = extractValue(remaining, "un/", "uq/");
            updatedQuantity = Integer.parseInt(extractValue(remaining, "uq/", null));
            if (updatedQuantity <= 0) {
                throw new NurseSchedException(ExceptionMessage.NEGATIVE_MEDICINE_QUANTITY);
            }
            logr.log(Level.INFO, "Extracted medicineName: {0}, updatedQuantity: {1}, updatedName: {2}",
                    new Object[]{medicineName, updatedQuantity, updatedQuantity});
            return new MedicineParser(command, medicineName, updatedQuantity, updatedName);
        } catch (RuntimeException e) {
            logr.log(Level.SEVERE, "Failed to parse edit command: {0}", remaining);
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINEEDIT_FORMAT);
        }
    }

    /**
     * Parses a "restock" command to extract the quantity to restock.
     *
     * @param remaining Remaining string after the "restock" keyword.
     * @param command   The command type ("restock").
     * @return A {@code MedicineParser} instance with extracted values.
     * @throws NurseSchedException If the input format is invalid or quantity is invalid.
     */
    private static MedicineParser getMedicineRestockParser(String remaining, String command)
            throws NurseSchedException {
        assert remaining != null : "Remaining string cannot be null";
        assert command != null : "Command cannot be null";

        logr.log(Level.INFO, "Parsing restock command with remaining: {0}", remaining);

        int restockQuantity;

        try {
            restockQuantity = Integer.parseInt(extractValue(remaining, "q/", null));
            if (restockQuantity <= 0) {
                throw new NurseSchedException(ExceptionMessage.NEGATIVE_MEDICINE_QUANTITY);
            }
            logr.log(Level.INFO, "Extracted restockQuantity: {0}",
                    new Object[]{restockQuantity});
            return new MedicineParser(command, "", restockQuantity, "");
        } catch (RuntimeException e) {
            logr.log(Level.SEVERE, "Failed to parse restock command: {0}", remaining);
            throw new NurseSchedException(ExceptionMessage.INVALID_MEDICINERESTOCK_FORMAT);
        }
    }

    /**
     * Extracts a value from the given input string between a start marker and an optional end marker.
     *
     * @param input       The full input string.
     * @param startMarker The starting marker to locate the beginning of the value.
     * @param endMarker   The ending marker (optional) to delimit the value.
     * @return The extracted value between markers.
     * @throws RuntimeException If the start marker is missing.
     */
    private static String extractValue(String input, String startMarker, String endMarker) {
        logr.log(Level.INFO, "Extracting value from input: {0}, startMarker: {1}, endMarker: {2}",
                new Object[]{input, startMarker, endMarker});

        assert input != null : "Input string must not be null";
        assert startMarker != null : "Start marker must not be null";

        int start = input.indexOf(startMarker);
        if (start == -1) {
            logr.log(Level.WARNING, "Missing required marker: {0} in input: {1}",
                    new Object[]{startMarker, input});
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
