package commands;

import fileManagement.CatalogueManager;
import fileManagement.TableManager;
import main.Application;
import main.Controller;

import java.util.List;

public class DescribeCommand implements CommandInterface{
    private TableManager tableManager;
    private List<String> commands;

    public DescribeCommand(TableManager tableManager){
        this.tableManager = tableManager;
    }
    @Override
    public void execute() {
        this.commands = Controller.getCommands();
        if(commands.size() != 2) throw new NullPointerException();
        String tableName = commands.get(1);
        if(!tableManager.findIfTableExists(tableName)){
            Application.displayMessage("This table doesnt exist!");
            return;
        }
        tableManager.describe(tableName);
    }
}
