package commands;

import fileManagement.CatalogueManager;
import fileManagement.TableManager;
import main.Application;
import main.Controller;

import java.util.List;
/**
 * Command class to import a table into database
 */
public class ImportCommand implements CommandInterface{
    private TableManager tableManager;
    private CatalogueManager catalogueManager;
    private List<String> commands;

    public ImportCommand(TableManager tableManager, CatalogueManager catalogueManager){
        this.tableManager = tableManager;
        this.catalogueManager = catalogueManager;
    }
    @Override
    public void execute() {
        this.commands = Controller.getCommands();
        if(commands.size() != 2) throw new NullPointerException();
        String tableName = commands.get(1);
        if(tableManager.findIfTableExists(tableName)){
            Application.displayMessage("This table already exists");
            return;
        }
        catalogueManager.importTable(tableName);
        tableManager.readFile(catalogueManager.getFileNames(), catalogueManager.getFileLocation());
    }
}
