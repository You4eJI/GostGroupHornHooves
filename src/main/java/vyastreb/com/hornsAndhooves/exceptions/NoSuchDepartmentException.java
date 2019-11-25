package vyastreb.com.hornsAndhooves.exceptions;

public class NoSuchDepartmentException extends RuntimeException {
    public NoSuchDepartmentException() {}

    public NoSuchDepartmentException(String message) {
        super(message);
    }
}
