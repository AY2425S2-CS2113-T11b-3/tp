package seedu.nursesched.parser;

public class Parser {
    protected String type;

    public Parser() {
        type = "";
    }

    //Extracts type of command - Appointments/Patient Profiles/Shifts
    public static String extractType(String line) {
        if (line.contains(" ")) {
            return line.substring(0, line.indexOf(" "));
        }
        return line;
    }
}
