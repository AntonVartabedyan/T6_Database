package exceptions;

import main.Application;

public class WrongTableNameException extends RuntimeException {
    public WrongTableNameException(String message) {

        Application.displayMessage("Table with name " + message + " does not exist in current database");
    }
}
