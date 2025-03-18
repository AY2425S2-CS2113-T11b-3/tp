package seedu.nursesched.exception;

public class NurseSchedException extends Exception {
    public NurseSchedException(ExceptionMessage message) {
        super(message.getMessage());
    }

    public static void showError(String message) {
        System.out.println(message);
    }
}
