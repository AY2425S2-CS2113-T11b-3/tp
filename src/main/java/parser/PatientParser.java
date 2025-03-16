package parser;

import patient.Patient;

public class PatientParser {
    private String command;
    private String name;
    private String age;
    private String notes;
    private int index;

    public PatientParser(String command, String name, String age, String notes, int index) {
        this.command = command;
        this.name = name;
        this.age = age;
        this.notes = notes;
        this.index = index;
    }

    public static PatientParser extractInputs(String line) {
        line = line.trim();
        line = line.substring(line.indexOf(" ") + 1);
        String command = "";
        String name = "";
        String age = "";
        String notes = "";
        int index = -1;

        // Handle cases where the line is just the command itself
        // If there are additional parameters, the command will be correctly parsed
        // If there are no parameters like "pf list", then throw an exception that treats
        // the line as the command
        try {
            command = line.substring(0, line.indexOf(" "));
        } catch (IndexOutOfBoundsException e) {
            command = line;
        }

        if (command.equals("add")) {
            try {
                line = line.substring(line.indexOf(" ") + 1);

                name = line.substring(line.indexOf("p/") + 2, line.indexOf("a/") - 1);
                line = line.substring(line.indexOf("a/"));

                age = line.substring(line.indexOf("a/") + 2, line.indexOf("n/") - 1);
                line = line.substring(line.indexOf("n/"));

                notes = line.substring(line.indexOf("n/") + 2);
                return new PatientParser(command, name, age, notes, index);
            } catch (Exception e) {
                System.out.println("Missing Parameters!");
                return null;
            }
        } else if (command.equals("del")) {
            try {
                line = line.substring(line.indexOf(" ") + 1);

                index = Integer.parseInt(line);
                if (index < 0 || index > Patient.getSizeOfList()) {
                    throw new NumberFormatException("This index doesn't exist within the list: " + index);
                }
                return new PatientParser(command, name, age, notes, index);
            } catch (NumberFormatException e) {
                System.out.println("Invalid index: " + line);
                return null;
            }
        } else if (command.equals("list")) {
            return new PatientParser(command, name, age, notes, index);
        }
        return null;
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
