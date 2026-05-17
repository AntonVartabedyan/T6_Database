package commands;

import fileManagement.CatalogueManager;
import fileManagement.TableManager;
import main.Controller;

import java.util.List;

public class ShowTablesCommand implements CommandInterface{
    private CatalogueManager catalogueManager;

    public ShowTablesCommand(CatalogueManager catalogueManager){
        this.catalogueManager = catalogueManager;
    }
    @Override
    public void execute() {
        catalogueManager.showTables();
    }
}
