package ua.com.foxminded.fastestlaps;

public class ValidationDataException extends Exception {

    private static final long serialVersionUID = 1152180325674347639L;

    public ValidationDataException(String data) {
        System.out.println(data);
    }

}
