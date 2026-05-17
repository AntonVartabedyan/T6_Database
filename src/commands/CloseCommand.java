package commands;

import fileManagement.CatalogueManager;
import fileManagement.TableManager;

import java.util.List;
/**
 * Command class to close the table and database sheets and wipe all data
 */
public class CloseCommand implements CommandInterface{
    private CatalogueManager catalogueManager;
    private TableManager tableManager;

    public CloseCommand(CatalogueManager catalogueManager, TableManager tableManager){
        this.catalogueManager = catalogueManager;
        this.tableManager = tableManager;
    }
    @Override
    public void execute() {
        catalogueManager.closeFile();
        tableManager.close();
    }
}
