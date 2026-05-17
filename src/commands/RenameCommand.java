package commands;

import fileManagement.CatalogueManager;
import fileManagement.TableManager;
import main.Application;
import main.Controller;

import java.util.List;
/**
 * Command class to rename a file
 */
public class RenameCommand implements CommandInterface{
    private TableManager tableManager;
    private CatalogueManager catalogueManager;
    private List<String> commands;

    public RenameCommand(TableManager tableManager, CatalogueManager catalogueManager){
        this.tableManager = tableManager;
        this.catalogueManager = catalogueManager;
    }
    @Override
    public void execute() {
        this.commands = Controller.getCommands();
        if(commands.size() != 3) throw new NullPointerException();
        String tableName = commands.get(1);
        String tableNamenew = commands.get(2);
        if(!tableManager.findIfTableExists(tableName)){
            Application.displayMessage("This table doesnt exist!");
            return;
        }
        if(tableManager.findIfTableExists(tableNamenew)){
            Application.displayMessage("This table doesnt exist!");
            return;
        }
        catalogueManager.rename(tableName,tableNamenew);
        tableManager.rename(tableName,tableNamenew);
    }
}
