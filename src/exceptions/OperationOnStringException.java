package exceptions;

import main.Application;

public class OperationOnStringException extends RuntimeException {
    public OperationOnStringException() {
        Application.displayMessage("Cannot execute aggregate operations on String columns.");
    }
}
