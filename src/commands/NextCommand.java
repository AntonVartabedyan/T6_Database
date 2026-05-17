package commands;

import fileManagement.CatalogueManager;
import fileManagement.TableManager;
import main.Application;
import main.Controller;
import pagination.Paginator;

import java.util.ArrayList;
import java.util.List;
/**
 * Command class go to next page in paginator
 */
public class NextCommand implements CommandInterface{
    private Paginator paginator;

    public NextCommand(Paginator paginator){
        this.paginator = paginator;
    }
    @Override
    public void execute() {
        Application.displayMessage(paginator.loadPage(paginator.getCurrentPage() + 1));
    }

}
