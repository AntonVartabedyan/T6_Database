package exceptions;

import main.Application;

public class ExistingFileException extends RuntimeException {
    public ExistingFileException(String message) {
        Application.displayMessage("This file already exists! " +  message);
    }
}
