package commands;

import fileManagement.CatalogueManager;
import fileManagement.TableManager;
import main.Application;
import main.Controller;

import java.util.List;

public class InnerJoinCommand implements CommandInterface{
    private TableManager tableManager;
    private CatalogueManager catalogueManager;
    private List<String> commands;

    public InnerJoinCommand(TableManager tableManager, CatalogueManager catalogueManager){
        this.tableManager = tableManager;
        this.catalogueManager = catalogueManager;
    }
    @Override
    public void execute() {
        this.commands = Controller.getCommands();
        if(commands.size() != 5) throw new NullPointerException();
        String tableName1 = commands.get(1);
        int column1 = Integer.parseInt(commands.get(2));
        String tableName2 = commands.get(3);
        int column2 = Integer.parseInt(commands.get(4));
        if(!tableManager.findIfTableExists(tableName1)){
            Application.displayMessage("Table " + tableName1 +" doesnt exist!");
            return;
        }
        if(!tableManager.findIfTableExists(tableName2)){
            Application.displayMessage("Table " + tableName2 +" doesnt exist!");
            return;
        }
        String newTableName = tableManager.innerJoin(tableName1, column1, tableName2, column2);
        Application.displayMessage("Inner join table " + newTableName + " created");
        catalogueManager.importTable(newTableName);
    }
}
