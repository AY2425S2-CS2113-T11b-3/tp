package parser;

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

        if (line.equals("list")) {
            command = "list";
            return new PatientParser(command, name, age, notes, index);
        }

        command = line.substring(0, line.indexOf(" "));
        line = line.substring(line.indexOf(" ") + 1);

        if (command.equals("add")) {
            name = line.substring(line.indexOf("p/") + 3, line.indexOf("a/") - 1);
            line = line.substring(line.indexOf("a/"));

            age = line.substring(line.indexOf("a/") + 3, line.indexOf("n/") - 1);
            line = line.substring(line.indexOf("n/"));

            notes = line.substring(line.indexOf("n/") + 2);
            return new PatientParser(command, name, age, notes, index);
        } else if (command.equals("del")) {
            try {
                index = Integer.parseInt(line);
                return new PatientParser(command, name, age, notes, index);
            } catch (NumberFormatException e) {
                System.out.println("Invalid index: " + line);
                return null;
            }
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
