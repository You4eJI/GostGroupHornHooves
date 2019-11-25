package vyastreb.com.hornsAndhooves.exceptions;

import java.util.NoSuchElementException;

public class NoSuchEmployeeException extends NoSuchElementException {
   public NoSuchEmployeeException() {}

   public NoSuchEmployeeException(String message) {
       super(message);
   }
}
