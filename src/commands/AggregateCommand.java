package commands;

import fileManagement.CatalogueManager;
import fileManagement.TableManager;
import main.Application;
import main.Controller;
import main.Operation;

import java.util.List;
/**
 * Command class to do operations under circumstances
 */
public class AggregateCommand implements CommandInterface{
    private TableManager tableManager;
    private List<String> commands;

    public AggregateCommand(TableManager tableManager){
        this.tableManager = tableManager;
    }
    @Override
    public void execute() {
        this.commands = Controller.getCommands();
        if(commands.size() != 6) throw new NullPointerException();
        String tableName = commands.get(1);
        int searchColumnN = Integer.parseInt(commands.get(2));
        String searchValue = commands.get(3);
        int targetColumnN = Integer.parseInt(commands.get(4));
        String operation = commands.get(5);
        Operation operationEnum = Operation.valueOf(operation.toUpperCase());
        if(!tableManager.findIfTableExists(tableName)){
            Application.displayMessage("This table doesnt exist!");
            return;
        }
        float val = tableManager.aggregate(tableName,searchColumnN,searchValue,targetColumnN,operationEnum);
        Application.displayMessage( "The operation returns " + val + ".");
    }
}
