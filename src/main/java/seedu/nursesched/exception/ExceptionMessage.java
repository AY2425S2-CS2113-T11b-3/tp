package seedu.nursesched.exception;

public enum ExceptionMessage {
    INPUT_EMPTY("Input line cannot be empty!"),
    INVALID_FORMAT("Invalid command format!"),
    INVALID_SHIFTADD_FORMAT("Invalid shift add format! Input as:" +
            " shift add [s/START_TIME] [e/END_TIME] [d/DATE] [st/SHIFT_TASK]"),
    INVALID_APPTADD_FORMAT("Invalid appointment add format! Input as:" +
            " appt add [p/PATIENT_NAME] [s/START_TIME] [e/END_TIME] [d/DATE] [n/NOTES]"),
    INVALID_PATIENTADD_FORMAT("Invalid patient add format! Input as:" +
            " pf add [p/PATIENT_NAME] [a/AGE] [n/NOTES]"),
    INVALID_TASKADD_FORMAT("Invalid task add format! Input as: "
            + "task add [td/TASK_DESCRIPTION] [d/DUE_DATE] [t/DUE_TIME]"),
    INVALID_TIME_FORMAT("Invalid time format! Input as HH:mm"),
    INVALID_DATE_FORMAT("Invalid date format! Input as YYYY-MM-DD"),
    INVALID_DATETIME_FORMAT("Invalid date or time format! Input date as YYYY-MM-DD, input time as HH:mm"),
    SHIFT_TASK_EMPTY("Shift task cannot be empty!"),
    INVALID_START_TIME("Start time must be before end time."),
    INVALID_DUE_DATE_TIME("Due date must be after current date and time."),
    INVALID_SHIFTDEL_FORMAT("Invalid shift del format! Input as:" +
            " shift del [sn/SHIFT_NUMBER]"),
    INVALID_SHIFT_NUMBER("Shift index must be a positive integer!"),
    NEGATIVE_INDEX("Index must be a positive integer!"),
    ZERO_INDEX("Index cannot be zero!"),
    INVALID_APPT_NUMBER("Index is greater than number of appointments in the list!"),
    INVALID_PATIENT_NUMBER("Index is greater than number of patients in the list!"),
    INVALID_TASK_NUMBER("Task index is not within 1 and the total number of tasks in the list!"),
    INVALID_COMMAND("Invalid command! Use 'add' or 'del'."),
    PARSING_ERROR("Error parsing command!");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
