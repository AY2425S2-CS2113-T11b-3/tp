package seedu.nursesched.parser;

import seedu.nursesched.exception.ExceptionMessage;
import seedu.nursesched.exception.NurseSchedException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import java.io.File;

/**
 * The ApptParser class parses the input of the user to make sense of the command.
 * It stores a command, name, start time, end time, date and notes if successfully parsed.
 * The methods within this class will return null if it does not understand the input.
 */
public class ApptParser extends Parser {

    private static final Logger logr = Logger.getLogger("ApptParser");


    private static int apptIndex;
    private static String searchKeyword;
    private String command;
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private String notes;

    static {
        try {
            File logDir = new File("logs/parser");
            if (!logDir.exists()) {
                logDir.mkdirs();  // Creates the directory and any missing parent directories
            }

            LogManager.getLogManager().reset();
            FileHandler fh = new FileHandler("logs/parser/apptParser.log", true);
            fh.setFormatter(new SimpleFormatter());
            logr.addHandler(fh);
            logr.setLevel(Level.ALL);
        } catch (IOException e) {
            logr.log(Level.SEVERE, "File logger not working", e);
        }
    }


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
                      LocalDate date, String notes, int apptIndex, String searchKeyword) {
        this.command = command;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.notes = notes;
        this.apptIndex = apptIndex;
        this.searchKeyword = searchKeyword;

        logr.info("ApptParser created: " + this);
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
    public static ApptParser extractInputs (String line) throws NurseSchedException {
        assert line != null : "Input line should not be null";
        logr.info("Extracting inputs from: " + line);
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
            if (line.contains(" ")) {
                command = line.substring(0, line.indexOf(" "));
                line = line.substring(line.indexOf(" ") + 1);
            } else {
                command = line;
                line = null;
            }
        } catch (IndexOutOfBoundsException e) {
            logr.warning("Invalid command: " + command);
            System.out.println("Invalid inputs! Please try again.");
            return null;
        }

        if (command.equals("add")) {
            if (!line.contains("p/") || !line.contains("s/") ||
                    !line.contains("d/") || !line.contains("e/")) {
                logr.warning("Missing fields");
                throw new NurseSchedException(ExceptionMessage.INVALID_APPTADD_FORMAT);
            }

            try {
                //extracts patient name
                name = line.substring(line.indexOf("p/") + 2, line.indexOf("s/") - 1);
                line = line.substring(line.indexOf("s/"));

                //extracts appointment's start time
                startTime = LocalTime.parse(line.substring(2, line.indexOf(" ")));
                endTime = LocalTime.parse(line.substring(line.indexOf("e/") + 2, line.indexOf("d/") - 1));

                //extracts appointment's date
                date = LocalDate.parse(line.substring(line.indexOf("d/") + 2, line.indexOf("d/") + 12));
            } catch (DateTimeParseException e) {
                throw new NurseSchedException(ExceptionMessage.INVALID_DATETIME_FORMAT);
            }

            notes = line.substring(line.indexOf("n/") + 2);
            return new ApptParser(command, name, startTime, endTime, date, notes, apptIndex, searchKeyword);
        }

        if (command.equals("del")) {
            apptIndex = parseIndex(line);
            return new ApptParser(command, name, startTime, endTime, date, notes, apptIndex,searchKeyword);
        }

        if (command.equals("mark") || command.equals("unmark")) {
            apptIndex = parseIndex(line);
            return new ApptParser(command, name, startTime, endTime, date, notes, apptIndex,searchKeyword);
        }


        if (command.equals("list")) {
            return new ApptParser(command, name, startTime, endTime, date, notes, apptIndex,searchKeyword);
        }

        if (command.equals("sort")) {
            return new ApptParser(command, name, startTime, endTime, date, notes, apptIndex,searchKeyword);
        }

        if (command.equals("find")) {
            if (line == null){
                throw new NurseSchedException(ExceptionMessage.MISSING_SEARCH_TERM);
            }
            searchKeyword = line;
            return new ApptParser(command, name, startTime, endTime, date, notes, apptIndex, searchKeyword);
        }

        if (command.equals("edit")) {
            // Nothing written after command. i.e "edit "
            if (line.isEmpty()) {
                logr.warning("Missing index field in edit command");
                throw new NurseSchedException(ExceptionMessage.INVALID_APPTEDIT_FORMAT);
            }

            try {
                // Extract index
                if (line.trim().contains(" ")) {
                    String indexString = line.substring(0, line.indexOf(" "));
                    apptIndex = parseIndex(indexString);
                    line = line.substring(line.indexOf(" ") + 1);
                } else{
                    // Nothing written after index i.e "edit 1 "
                    logr.warning("No fields given to edit");
                    throw new NurseSchedException(ExceptionMessage.INVALID_APPTEDIT_FORMAT);
                }

                // Extract optional fields
                if (line.contains("p/")) {
                    int nameStart = line.indexOf("p/") + 2;
                    int nameEnd = findNextFieldIndex(line, nameStart);
                    name = line.substring(nameStart, nameEnd).trim();
                } else {
                    name = null;
                }

                if (line.contains("s/")) {
                    int sIndex = line.indexOf("s/") + 2;
                    int sEnd = findNextFieldIndex(line, sIndex);
                    startTime = LocalTime.parse(line.substring(sIndex, sEnd).trim());
                }else {
                    startTime = null;
                }

                if (line.contains("e/")) {
                    int eIndex = line.indexOf("e/") + 2;
                    int eEnd = findNextFieldIndex(line, eIndex);
                    endTime = LocalTime.parse(line.substring(eIndex, eEnd).trim());
                } else {
                    endTime = null;
                }

                if (line.contains("d/")) {
                    int dIndex = line.indexOf("d/") + 2;
                    int dEnd = findNextFieldIndex(line, dIndex);
                    date = LocalDate.parse(line.substring(dIndex, dEnd).trim());
                } else {
                    date = null;
                }

                if (line.contains("n/")) {
                    int nIndex = line.indexOf("n/") + 2;
                    notes = line.substring(nIndex).trim();
                } else {
                    notes = null;
                }

            } catch (NumberFormatException e) {
                logr.warning("Invalid appointment index format in edit command");
                throw new NurseSchedException(ExceptionMessage.INVALID_APPT_NUMBER);
            } catch (DateTimeParseException e) {
                throw new NurseSchedException(ExceptionMessage.INVALID_DATETIME_FORMAT);
            }

            return new ApptParser(command, name, startTime, endTime, date, notes, apptIndex, searchKeyword);
        }

        logr.warning("Invalid command: " + command);
        return null;
    }

    public static int parseIndex (String line) throws NurseSchedException {
        int index = 0;
        try {
            index = Integer.parseInt(line) - 1;
            if (index < 0) {
                logr.warning("Negative index: " + line);
                throw new NurseSchedException(ExceptionMessage.NEGATIVE_INDEX);
            }
        } catch (NumberFormatException e) {
            logr.warning("Invalid index: " + line);
            throw new NurseSchedException(ExceptionMessage.INVALID_APPT_NUMBER);
        }
        return index;
    }

    // Helper method to find where the next field starts
    private static int findNextFieldIndex(String line, int startPos) {
        // All possible next field markers
        int[] markers = {
                line.indexOf("p/", startPos),
                line.indexOf("s/", startPos),
                line.indexOf("e/", startPos),
                line.indexOf("d/", startPos),
                line.indexOf("n/", startPos)
        };

        // Find the closest marker that's not -1 (not found)
        int nextIndex = line.length();
        for (int marker : markers) {
            if (marker != -1 && marker < nextIndex) {
                nextIndex = marker;
            }
        }
        return nextIndex;
    }


    //Getters
    public String getCommand () {
        return command;
    }

    public String getName () {
        return name;
    }

    public LocalTime getStartTime () {
        return startTime;
    }

    public LocalTime getEndTime () {
        return endTime;
    }

    public LocalDate getDate () {
        return date;
    }

    public String getNotes () {
        return notes;
    }

    public int getIndex () {
        return apptIndex;
    }

    public String getSearchKeyword () {
        return searchKeyword;
    }

}
