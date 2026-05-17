package commands;

import fileManagement.CatalogueManager;
import fileManagement.TableManager;
import main.Application;
import main.Controller;

import java.util.List;
/**
 * Command class to do counting under circumstances
 */
public class CountCommand implements CommandInterface{
    private TableManager tableManager;
    private List<String> commands;

    public CountCommand(TableManager tableManager){
        this.tableManager = tableManager;
    }
    @Override
    public void execute() {
        this.commands = Controller.getCommands();
        if(commands.size() != 4) throw new NullPointerException();
        String tableName = commands.get(1);
        int searchColumnN = Integer.parseInt(commands.get(2));
        String searchValue = commands.get(3);
        if(!tableManager.findIfTableExists(tableName)){
            Application.displayMessage("This table doesnt exist!");
            return;
        }
        Application.displayMessage(tableManager.count(tableName,searchColumnN,searchValue) + " columns contain the searched value " + searchValue + ".");

    }
}
