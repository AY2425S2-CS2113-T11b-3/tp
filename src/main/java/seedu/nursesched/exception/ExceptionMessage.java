package seedu.nursesched.exception;

public enum ExceptionMessage {
    INPUT_EMPTY("Input line cannot be empty!"),
    INVALID_FORMAT("Invalid command format!"),
    INVALID_SHIFTADD_FORMAT("Invalid shift add format! Input as:" +
            " shift add [s/START_TIME] [e/END_TIME] [d/DATE] [st/SHIFT_TASK]"),
    INVALID_PATIENT_ADD_FORMAT("Invalid patient add format! Input as:\n" +
            "pf add id/[ID_NUMBER] p/[PATIENT_NAME] a/[AGE] g/[GENDER] c/[CONTACT] n/[NOTES]"),
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
    INVALID_SHIFTEDIT_FORMAT("Invalid shift edit format! Input as: " +
            "shift edit [sn/SHIFT_NUMBER] [s/START_TIME] [e/END_TIME] [d/DATE] [st/SHIFT_TASK]"),
    INVALID_SHIFTMARK_FORMAT("Invalid shift mark format! Input as: shift mark [sn/SHIFT_NUMBER]"),
    INVALID_SHIFTUNMARK_FORMAT("Invalid shift unmark format! Input as: shift unmark [sn/SHIFT_NUMBER]"),
    INVALID_SHIFT_NUMBER("There is no shift with that specified index!"),
    NEGATIVE_INDEX("Index must be a positive integer greater than 0!"),
    ZERO_INDEX("Index cannot be zero!"),
    INVALID_PATIENT_NUMBER("Index is greater than number of patients in the list!"),
    TASK_INDEX_OUT_OF_BOUNDS("Task index is not within 1 and the total number of tasks in the list!"),
    INVALID_COMMAND("Invalid command! Use 'add' or 'del'."),
    PARSING_ERROR("Error parsing command!"),
    MISSING_SEARCH_TERM("Missing search term after 'find'!"),
    INVALID_ID_LENGTH("Patient ID must be 4 digits long."),
    INVALID_ID_INPUT("Patient ID must contain only digits."),
    EMPTY_PATIENT_INFO("Patient information cannot be empty!"),
    INVALID_PATIENT_INFO("Patient ID is invalid!"),
    MISSING_PATIENT_FIELDS("Some patient fields are missing!"),
    INVALID_TASK_INDEX("Invalid task index! Index should be an integer!"),
    PATIENT_NOT_FOUND("Patient not found!"),
    EMPTY_PATIENT_TEST_NAME("Patient test name cannot be empty!"),
    EMPTY_PATIENT_TEST_RESULT("Patient test result cannot be empty!"),
    INVALID_TASK_EDIT_FORMAT("Invalid task edit format! Input as: "
            + "task edit [id/TASK_INDEX] [td/NEW_DESCRIPTION] [d/NEW_DUE_DATE] [t/NEW_DUE_TIME]"),
    EMPTY_INPUT_DETAILS("Input details cannot be empty!"),
    MISSING_ID("Patient ID cannot be empty!"),
    EMPTY_PATIENT_FIELDS("Some patient fields are empty!"),
    INVALID_GENDER("Gender must be either 'M' or 'F'!"),
    PATIENT_ID_EXIST("Patient ID already exist!"),
    EMPTY_PATIENT_ID_FIELD("Patient ID field cannot be empty!"),
    MISSING_EDIT_INPUT("Edit fields cannot be empty!"),
    INVALID_SORTING_LIST("List is empty. Nothing to sort."),
    MISSING_INDEX_PARAMETER("Missing id/[INDEX] field for index."),
    INVALID_INDEX_PARAMETER("id/[INDEX] field must be an integer only."),
    INVALID_TASK_FIND_FIELDS("Invalid task find fields! Input as: task find td/[KEYWORD]"),

    // Medicine errors
    INVALID_FORMAT_MEDICINE_SAVED("Invalid format in medicine storage file. Input as: MEDICINE_NAME | QUANTITY in" +
            " storage file."),
    INVALID_MEDICINEEDIT_FORMAT("Invalid medicine edit format! Input as: medicine edit mn/MEDICINE_NAME " +
            "un/UPDATE_NAME uq/UPDATED_QUANTITY"),
    INVALID_MEDICINEDELETE_FORMAT("Invalid medicine delete format! Input as: medicine delete mn/MEDICINE_NAME"),
    INVALID_MEDICINEFIND_FORMAT("Invalid medicine find format! Input as: medicine find mn/MEDICINE_NAME"),
    INVALID_MEDICINE_FORMAT("Try adding add, remove, edit, list, find, restock or delete!"),
    INVALID_MEDICINEADD_FORMAT("Invalid medicine add format! Input as: medicine add mn/MEDICINE_NAME q/QUANTITY"),
    INVALID_MEDICINERESTOCK_FORMAT("Invalid medicine restock format! Input as: medicine restock q/QUANTITY"),
    INVALID_MEDICINEREMOVE_FORMAT("Invalid medicine remove format! Input as: medicine remove mn/MEDICINE_NAME" +
            " q/QUANTITY"),
    NO_RESTOCK_REQUIRED("No restock required!"),
    NEGATIVE_MEDICINE_QUANTITY("Medicine quantity must be a positive integer!"),
    INVALID_MEDICINE_QUANTITY("Medicine quantity is too big! Should be lesser than existing quantity."),
    MISSING_MEDICINE_QUANTITY("No medicine quantity input"),
    MISSING_MEDICINE_NAME("No Medicine name input"),
    MEDICINE_NONEXISTENT("Medicine does not exist!"),

    //APPT errors
    INVALID_PATIENT_APPT_ADD("Patient name not found in patient list! Add patient to " +
                                     "patient list before adding appointment!"),
    INVALID_SORT_PARAMETER("Can only be sorted by time or importance!"),
    INVALID_IMPORTANCE_FORMAT("Importance should be an integer from 1 to 3. \n" +
            "1:LOW, 2:MEDIUM, 3:HIGH"),
    INVALID_APPTEDIT_FORMAT("Invalid appointment edit format! Input as: appt edit id/[INDEX] p/[PATIENT_NAME] " +
            "s/[START_TIME] e/[END_TIME] d/[DATE] im/[IMPORTANCE] n/[NOTES]"),
    INVALID_APPT_NUMBER("There is no appointment with that specified index!"),
    INVALID_APPTADD_FORMAT("Invalid appointment add format! Input as:" +
            " appt add [p/PATIENT_NAME] [s/START_TIME] [e/END_TIME] [d/DATE] [n/NOTES]"),
    INVALID_APPT_DATE_TIME("Appointment date and time must be after current date and time."),
    UNMARKING_UNMARKED_APPT("Cant unmark an appointment that was already unmarked!"),
    MARKING_MARKED_APPT("Cant mark an appointment that was already marked!");


    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
