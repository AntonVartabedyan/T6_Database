package commands;

import fileManagement.CatalogueManager;
import fileManagement.TableManager;
import main.Application;
import main.Controller;
import pagination.Paginator;

import java.util.ArrayList;
import java.util.List;

public class SelectCommand implements CommandInterface{
    private TableManager tableManager;
    private List<String> commands;
    private Paginator paginator;

    public SelectCommand(TableManager tableManager, Paginator paginator){
        this.tableManager = tableManager;
        this.paginator = paginator;
    }
    @Override
    public void execute() {
        this.commands = Controller.getCommands();
        if(commands.size() != 4) throw new NullPointerException();
        int columnN = Integer.parseInt(commands.get(1));
        String value = commands.get(2);
        String tableName = commands.get(3);
        if(!tableManager.findIfTableExists(tableName)){
            Application.displayMessage("This table doesnt exist!");
            return;
        }
        paginator.setRowsForSelect(tableManager.select(columnN,value,tableName));
        Application.displayMessage(paginator.loadPage(1));
    }
}
