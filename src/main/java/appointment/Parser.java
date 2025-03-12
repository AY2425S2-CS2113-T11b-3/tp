package appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Parser {
    private String command = "";
    private String type = "";
    private String name = "";
    private LocalTime startTime = null;
    private LocalTime endTime = null;
    private LocalDate date = null;
    private String notes = "";

    public Parser(String command, String type, String name, LocalTime startTime, LocalTime endTime,
                  LocalDate date, String notes) {
        this.command = command;
        this.type = type;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.notes = notes;
    }

    //Extracts command and the type (Appointments, Patient Profile or Shifts)
    public static Parser extractCommand(Scanner in) {
        String line = in.nextLine();
        line = line.trim();
        line = line.toLowerCase();
        String command = "";
        String type = "Empty";
        String name = null;
        LocalTime startTime = null;
        LocalTime endTime = null;
        LocalDate date = null;
        String notes = null;

        try {
            command = line.substring(0, line.indexOf(" "));
            line = line.substring(line.indexOf(" ") + 1);
            type = line.substring(0, line.indexOf(" "));

            //line only stores input parameters from here on
            line = line.substring(line.indexOf(" ") + 1);
        } catch (Exception e) {
            System.out.println("Command or type not valid!");
            return null;
        }

        switch (type) {
        case "appt":
            try {
                name = line.substring(line.indexOf("p/") + 2, line.indexOf("s/") - 1);
                line  = line.substring(line.indexOf("s/"));
                startTime = LocalTime.parse(line.substring(2, line.indexOf(" ")));
                date = LocalDate.parse(line.substring(line.indexOf("d/") + 2, line.indexOf("d/") + 12));
            } catch (Exception e) {
                System.out.println(line);
                System.out.println("NAME" + name);
                System.out.println("START" + startTime);
                System.out.println("DATE" + date);
                System.out.println("Patient's name, appointment time or date is missing!");
                return null;
            }

            if (command.equals("add")) {
                try {
                    endTime = LocalTime.parse(line.substring(line.indexOf("e/") + 2, line.indexOf("d/") - 1));
                    notes = line.substring(line.indexOf("n/") + 2);
                } catch (Exception e) {
                    System.out.println("Invalid appointment time/notes format!");
                    return null;
                }
            }
            return new Parser(command, type, name, startTime, endTime, date, notes);
        case "pf":
            break;
        case "shift":
            break;
        default:
            System.out.println("Invalid type!");
            System.out.println("Please include appt, pf or shift type!");
            return null;
        }
        return new Parser(command, type, name, startTime, endTime, date, notes);
    }

    //Getters
    public String getCommand() {
        return command;
    }
    public String getType() {
        return type;
    }
    public String getName() {
        return name;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
    public LocalDate getDate() {
        return date;
    }
    public String getNotes() {
        return notes;
    }
}
