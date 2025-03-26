package seedu.nursesched.parser;


public class MedicineParser extends Parser {
    private final String command;
    private final String medicineName;
    private final int quantity;

    public MedicineParser(String command, String medicineName, int quantity) {
        this.command = command;
        this.medicineName = medicineName;
        this.quantity = quantity;
    }

    public static MedicineParser extractInputs(String line) {
        line = line.trim().toLowerCase();
        String[] parts = line.split(" ", 2);

        if (parts.length < 2) {
            throw new RuntimeException("Invalid command format");
        }

        String remaining = parts[1];
        String command = "";

        try {
            String[] commandParts = remaining.split(" ", 2);
            command = commandParts[0];
            remaining = (commandParts.length > 1) ? commandParts[1] : "";

            if (command.equals("add")) {
                return getMedicineAddParser(remaining, command);
            } else if (command.equals("list")) {
                return new MedicineParser("list", "", 0);
            } else if (command.equals("remove")) {
                return getMedicineRemoveParser(remaining, command);
            } else {
                throw new RuntimeException("Invalid command: " + command);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Error parsing input: " + e.getMessage(), e);
        }
    }

    private static MedicineParser getMedicineAddParser(String remaining, String command) {
        String medicineName;
        int quantity;

        try {
            medicineName = extractValue(remaining, "mn/", "q/");
        } catch (RuntimeException e) {
            throw new RuntimeException("Missing or invalid medicine name format");
        }

        try {
            quantity = Integer.parseInt(extractValue(remaining, "q/", null));
        } catch (RuntimeException e) {
            throw new RuntimeException("Invalid quantity format");
        }

        return new MedicineParser(command, medicineName, quantity);
    }

    private static MedicineParser getMedicineRemoveParser(String remaining, String command) {
        String medicineName;
        int quantity;

        try {
            medicineName = extractValue(remaining, "mn/", "q/");
        } catch (RuntimeException e) {
            throw new RuntimeException("Missing or invalid medicine name format");
        }

        try {
            quantity = Integer.parseInt(extractValue(remaining, "q/", null));
        } catch (RuntimeException e) {
            throw new RuntimeException("Invalid quantity format");
        }

        return new MedicineParser(command, medicineName, quantity);
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
        return quantity;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getCommand() {
        return command;
    }
}
