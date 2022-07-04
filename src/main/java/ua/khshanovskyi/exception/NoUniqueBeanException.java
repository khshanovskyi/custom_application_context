package ua.khshanovskyi.exception;

public class NoUniqueBeanException extends Exception{
    public NoUniqueBeanException() {
    }

    public NoUniqueBeanException(String message) {
        super(message);
    }
}
