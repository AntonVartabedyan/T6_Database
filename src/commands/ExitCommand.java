package commands;

import fileManagement.CatalogueManager;
import fileManagement.TableManager;
import main.Application;
import main.Controller;

import java.util.List;

public class ExitCommand implements CommandInterface{

    public ExitCommand(){

    }
    @Override
    public void execute() {
        Runnable exitProgram = Application::exitProgram;
        exitProgram.run();
    }
}
