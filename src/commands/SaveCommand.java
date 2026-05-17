package commands;

import fileManagement.CatalogueManager;
import fileManagement.TableManager;
import main.Controller;

import java.util.List;
/**
 * Command class to save the database
 */
public class SaveCommand implements CommandInterface{
    private TableManager tableManager;
    private CatalogueManager catalogueManager;

    public SaveCommand(TableManager tableManager, CatalogueManager catalogueManager){
        this.tableManager = tableManager;
        this.catalogueManager = catalogueManager;
    }
    @Override
    public void execute() {
            catalogueManager.save();
            tableManager.save(catalogueManager.getFileLocation());
    }
}
