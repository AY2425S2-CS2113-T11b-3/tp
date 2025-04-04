package seedu.nursesched.parser;

public class Parser {
    /**
     * Extracts the type of command from user input. There are 5 types, appt, pf, task, medicine and shift.
     *
     * @param line The users input.
     * @return The command type.
     */
    public static String extractType(String line) {
        String type = "";
        if (line.contains(" ")) {
            return line.substring(0, line.indexOf(" "));
        }
        return type;
    }
}
