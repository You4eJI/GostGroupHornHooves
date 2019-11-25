package vyastreb.com.hornsAndhooves.exceptions;

import java.util.NoSuchElementException;

public class NoSuchOrderException extends NoSuchElementException {
    public NoSuchOrderException() {}

    public NoSuchOrderException(String message) {
        super(message);
    }
}
