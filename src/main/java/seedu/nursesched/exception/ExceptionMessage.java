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
    TASK_INDEX_OUT_OF_BOUNDS("Task index is not within 1 and the total number of tasks in the list!"),
    INVALID_COMMAND("Invalid command! Use 'add' or 'del'."),
    PARSING_ERROR("Error parsing command!"),
    INVALID_MEDICINEADD_FORMAT("Invalid medicine add format! Input as: medicine add mn/[MEDICINE_NAME] q/[QUANTITY]"),
    INVALID_MEDICINE_QUANTITY("Medicine quantity must be a positive integer!"),
    MEDICINE_INPUT_NOT_FOUND("Medicine not found!"),
    MISSING_SEARCH_TERM("Missing search term after 'find'!"),
    INVALID_ID_LENGTH("Patient ID must be 4 digits long."),
    INVALID_ID_INPUT("Patient ID must contain only digits."),
    EMPTY_PATIENT_INFO("Patient information cannot be empty!"),
    INVALID_PATIENT_INFO("Patient ID is invalid!"),
    MISSING_PATIENT_FIELDS("Some patient fields are missing!"),
    INVALID_APPTEDIT_FORMAT("Invalid appointment edit format! Input as: appt edit [i/INDEX] [p/PATIENT_NAME] " +
            "[s/START_TIME] [e/END_TIME] [d/DATE] [n/NOTES]"),
    INVALID_TASK_INDEX("Invalid task index! Index should be an integer!"),
    PATIENT_NOT_FOUND("Patient not found!"),
    EMPTY_PATIENT_TEST_NAME("Patient test name cannot be empty!"),
    EMPTY_PATIENT_TEST_RESULT("Patient test result cannot be empty!"),
    INVALID_TASK_EDIT_FORMAT("Invalid task edit format! Input as: "
            + "task edit [id/TASK_INDEX] [td/NEW_DESCRIPTION] [d/NEW_DUE_DATE] [t/NEW_DUE_TIME]"),
    EMPTY_INPUT_DETAILS("Input details cannot be empty!"),
    MISSING_ID("Patient ID cannot be empty!"),
    MISSING_EDIT_INPUT("Edit fields cannot be empty!");



    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
