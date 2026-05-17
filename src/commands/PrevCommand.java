package commands;

import fileManagement.CatalogueManager;
import fileManagement.TableManager;
import main.Application;
import main.Controller;
import pagination.Paginator;

import java.util.ArrayList;
import java.util.List;

public class PrevCommand implements CommandInterface{
    private Paginator paginator;

    public PrevCommand(Paginator paginator){
        this.paginator = paginator;
    }
    @Override
    public void execute() {
        Application.displayMessage(paginator.loadPage((paginator.getCurrentPage() - 1 == 0) ? 1 : paginator.getCurrentPage() - 1));
    }

}
