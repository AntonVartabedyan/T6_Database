package commands;

import fileManagement.CatalogueManager;
import fileManagement.TableManager;
import main.Application;
import main.Controller;

import java.util.List;

public class UpdateCommand implements CommandInterface{
    private TableManager tableManager;
    private List<String> commands;

    public UpdateCommand(TableManager tableManager){
        this.tableManager = tableManager;
    }
    @Override
    public void execute() {
        this.commands = Controller.getCommands();
        if(commands.size() != 6) throw new NullPointerException();
        String tableName = commands.get(1);
        int searchColumnN = Integer.parseInt(commands.get(2));
        String searchValue = commands.get(3);
        int targetColumnN =Integer.parseInt(commands.get(4));
        String targetValue = commands.get(5);
        if(!tableManager.findIfTableExists(tableName)){
            Application.displayMessage("This table doesnt exist!");
            return;
        }
        tableManager.update(tableName,searchColumnN,searchValue,targetColumnN,targetValue);
    }
}
