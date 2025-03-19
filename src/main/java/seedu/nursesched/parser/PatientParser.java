package seedu.nursesched.parser;

import seedu.nursesched.exception.ExceptionMessage;
import seedu.nursesched.exception.NurseSchedException;
import seedu.nursesched.patient.Patient;

/**
 * The PatientParser class parses the input of the user to make sense of the command.
 * It stores a command, name, age, notes and index if successfully parsed.
 * The methods within this class will return null if it does not understand the input.
 */
public class PatientParser extends Parser {
    private final String command;
    private final String name;
    private final String age;
    private final String notes;
    private final int index;

    /**
     * Constructs a new PatientParser object with the specified parameters.
     *
     * @param command The command associated with the input given.
     * @param name The name of the patient.
     * @param age The age of the patient.
     * @param notes Additional notes about the patient.
     * @param index Chosen index within the patient list.
     */
    public PatientParser(String command, String name, String age, String notes, int index) {
        assert command != null : "Command cannot be null";
        assert index >= 0 : "Patient index cannot be negative";

        this.command = command;
        this.name = name;
        this.age = age;
        this.notes = notes;
        this.index = index;
    }

    /**
     * Extracts and parses the inputs from the given command line for patient-related operations.
     * The method supports three commands: "add", "del", and "list".
     *
     * For the "add" command, the input line should follow the format:
     * {@code pf add p/<name> a/<age> n/<notes>}
     *
     * For the "del" command, the input line should follow the format:
     * {@code pf del <index>}
     *
     * For the "list" command, the input line should follow the format:
     * {@code pf list}
     *
     * @param line The input command line to be parsed. It should start with "pf" followed by the command.
     * @return A {@link PatientParser} object containing the parsed command and its associated parameters.
     *         Returns {@code null} if the input line is invalid or if required parameters are missing.
     *
     * @throws IndexOutOfBoundsException If the input line does not contain the expected parameters.
     * @throws NumberFormatException If the index provided for the "del" command is invalid or out of bounds.
     */
    public static PatientParser extractInputs(String line) throws NurseSchedException {
        assert line != null : "Input line cannot be null";

        if (line.trim().isEmpty()) {
            throw new NurseSchedException(ExceptionMessage.INPUT_EMPTY);
        }

        line = line.trim();
        line = line.substring(line.indexOf(" ") + 1);
        String command = "";
        String name = "";
        String age = "";
        String notes = "";
        int index = 0;

        // Handle cases where the line is just the command itself
        // If there are additional parameters, the command will be correctly parsed
        // If there are no parameters like "pf list", then throw an exception that treats
        // the line as the command
        try {
            if (line.contains(" ")) {
                command = line.substring(0, line.indexOf(" "));
                line = line.substring(line.indexOf(" ") + 1);
            } else {
                command = line;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid inputs! Please try again.");
            return null;
        }

        if (command.equals("add")) {
            try {
                name = line.substring(line.indexOf("p/") + 2, line.indexOf("a/") - 1);
                line = line.substring(line.indexOf("a/"));

                age = line.substring(line.indexOf("a/") + 2, line.indexOf("n/") - 1);
                line = line.substring(line.indexOf("n/"));

                notes = line.substring(line.indexOf("n/") + 2);
                return new PatientParser(command, name, age, notes, index);
            } catch (Exception e) {
                throw new NurseSchedException(ExceptionMessage.INVALID_PATIENTADD_FORMAT);
            }
        } else if (command.equals("del")) {
            index = parseIndex(line);
            return new PatientParser(command, name, age, notes, index);
        } else if (command.equals("list")) {
            return new PatientParser(command, name, age, notes, index);
        }
        return null;
    }

    public static int parseIndex (String line) throws NurseSchedException {
        int index = 0;
        try {
            index = Integer.parseInt(line) - 1;
            if (index == -1) {
                throw new NurseSchedException(ExceptionMessage.ZERO_INDEX);
            } else if (index < 0) {
                throw new NurseSchedException((ExceptionMessage.NEGATIVE_INDEX));
            }
        } catch (NumberFormatException e) {
            throw new NurseSchedException(ExceptionMessage.INVALID_PATIENT_NUMBER);
        }
        return index;
    }

    public String getCommand() {
        return command;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getNotes() {
        return notes;
    }

    public int getIndex() {
        return index;
    }
}
