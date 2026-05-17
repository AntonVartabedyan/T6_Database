package commands;

import fileManagement.CatalogueManager;
import fileManagement.TableManager;
import main.Application;
import main.Controller;

import java.util.List;
/**
 * Command class to export a file
 */
public class ExportCommand implements CommandInterface{
    private TableManager tableManager;
    private CatalogueManager catalogueManager;
    private List<String> commands;

    public ExportCommand(TableManager tableManager){
        this.tableManager = tableManager;
    }
    @Override
    public void execute() {
        this.commands = Controller.getCommands();
        if(commands.size() != 3) throw new NullPointerException();
        String tableName = commands.get(1);
        String fileName = commands.get(2);
        if(!tableManager.findIfTableExists(tableName)){
            Application.displayMessage("This table doesnt exist!");
            return;
        }
        tableManager.export(tableName,fileName);
    }
}
