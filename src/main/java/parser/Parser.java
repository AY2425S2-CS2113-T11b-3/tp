package parser;

import java.util.Scanner;

public class Parser {
    protected String type;

    public Parser() {
        type = "";
    }

    public Parser(String command) {
        this.type = command;
    }

    //Extracts type of command - Appointments/Patient Profiles/Shifts
    public static String extractType(String line) {
        try {
            line = line.substring(0, line.indexOf(" "));
        } catch (Exception e) {
            System.out.println("Invalid command!");
            System.out.println("Command should start with \"appt\", \"pf\" or \"shift\"");
        }
        return line;
    }

    public String getType() {
        return type;
    }
}
