package vyastreb.com.hornsAndhooves.exceptions;

import java.util.NoSuchElementException;

public class NoSuchFurnitureException extends NoSuchElementException {
    public NoSuchFurnitureException() {}

    public NoSuchFurnitureException(String message) {
        super(message);
    }
}
