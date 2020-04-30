package ua.com.foxminded.fastestlaps.exception;

public class ValidationDataException extends RuntimeException {

    private static final long serialVersionUID = 1152180325674347639L;

    public ValidationDataException(String message) {
        super(message);
    }

}
