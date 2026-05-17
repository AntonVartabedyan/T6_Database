package commands;

import fileManagement.CatalogueManager;
import fileManagement.TableManager;
import main.Application;
import main.Controller;

import java.util.ArrayList;
import java.util.List;
/**
 * Command class to insert a row into a table
 */
public class InsertCommand implements CommandInterface{
    private TableManager tableManager;
    private List<String> commands;

    public InsertCommand(TableManager tableManager){
        this.tableManager = tableManager;
    }
    @Override
    public void execute() {
        this.commands = Controller.getCommands();
        String tableName = commands.get(1);
        List<String> collumns = new ArrayList<>();
        for (int i = 2; i < commands.size(); i++)
        {
            collumns.add(commands.get(i));
        }
        if(!tableManager.findIfTableExists(tableName)){
            Application.displayMessage("This table doesnt exist!");
            return;
        }
        tableManager.insert(tableName,collumns);
    }
}
