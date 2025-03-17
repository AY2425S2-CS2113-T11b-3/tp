package parser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * The ApptParser class parses the input of the user to make sense of the command.
 * It stores a command, name, start time, end time, date and notes if successfully parsed.
 * The methods within this class will return null if it does not understand the input.
 */
public class ApptParser extends Parser {
    private String command;
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private String notes;

    /**
     * Constructs a new ApptParser object with the specified parameters.
     *
     * @param command The command associated with the input given.
     * @param name The name of the patient.
     * @param startTime The start time of the appointment.
     * @param endTime The end time of the appointment.
     * @param date The date of the appointment.
     * @param notes The additional things to note about the patient.
     */
    public ApptParser(String command, String name, LocalTime startTime, LocalTime endTime,
                      LocalDate date, String notes) {
        this.command = command;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.notes = notes;
    }

    /**
     * Extracts and parses the inputs from the given command for appointment-related operations.
     * This method supports three commands "add", "del" and "mark".
     *
     * @param line The user's input command to be parsed.
     * @return An {@link ApptParser} object which contains the parsed commands and associated parameters.
     *         Returns {@code null} if the input parameters are missing or invalid.
     *
     * @throws IndexOutOfBoundsException If the input line does not contain the expected parameters.
     * @throws DateTimeParseException If the input time or date is not of the expected format.
     */
    public static ApptParser extractInputs(String line) {
        line = line.trim();
        line = line.toLowerCase();
        line = line.substring(line.indexOf(" ") + 1);
        String command = "";
        String name = "";
        LocalTime startTime = null;
        LocalTime endTime = null;
        LocalDate date = null;
        String notes = "";

        try {
            //extracts command
            command = line.substring(0, line.indexOf(" "));
            line = line.substring(line.indexOf(" ") + 1);

            //extracts patient name
            name = line.substring(line.indexOf("p/") + 2, line.indexOf("s/") - 1);
            line = line.substring(line.indexOf("s/"));

            //extracts appointment's start time
            startTime = LocalTime.parse(line.substring(2, line.indexOf(" ")));

            //extracts appointment's date
            date = LocalDate.parse(line.substring(line.indexOf("d/") + 2, line.indexOf("d/") + 12));

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid inputs! Please try again.");
            return null;
        } catch (DateTimeParseException e) {
            System.out.println("Time or Date format! Please try again.");
            return null;
        }

        if (command.equals("add")) {
            try {
                endTime = LocalTime.parse(line.substring(line.indexOf("e/") + 2, line.indexOf("d/") - 1));
                notes = line.substring(line.indexOf("n/") + 2);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid inputs! Please try again.");
                return null;
            }
            return new ApptParser(command, name, startTime, endTime, date, notes);
        }
        return new ApptParser(command, name, startTime, endTime, date, notes);
    }

    //Getters
    public String getCommand() {
        return command;
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
