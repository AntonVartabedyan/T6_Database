package commands;

import fileManagement.CatalogueManager;
import fileManagement.TableManager;
import main.Application;
import main.Controller;

import java.util.List;

public class AddColumnCommand implements CommandInterface{
    private TableManager tableManager;
    private List<String> commands;

    public AddColumnCommand(TableManager tableManager){
        this.tableManager = tableManager;
    }
    @Override
    public void execute() {
        this.commands = Controller.getCommands();
        if(commands.size() != 4) throw new NullPointerException();
        String tableName = commands.get(1);
        String columnName = commands.get(2);
        String columnType = commands.get(3);
        if(!tableManager.findIfTableExists(tableName)){
            Application.displayMessage("This table doesnt exist!");
            return;
        }
        tableManager.addColumn(tableName,columnName,columnType);
    }
}
