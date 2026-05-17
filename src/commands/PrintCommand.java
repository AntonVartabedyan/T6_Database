package commands;

import fileManagement.CatalogueManager;
import fileManagement.TableManager;
import main.Application;
import main.Controller;
import pagination.Paginator;

import java.util.List;
/**
 * Command class to print out a table's contents
 */
public class PrintCommand implements CommandInterface{
    private TableManager tableManager;
    private CatalogueManager catalogueManager;
    private List<String> commands;
    private Paginator paginator;

    public PrintCommand(TableManager tableManager, CatalogueManager catalogueManager, Paginator paginator){
        this.tableManager = tableManager;
        this.catalogueManager = catalogueManager;
        this.paginator = paginator;
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
        paginator.setRows(tableManager.getTable(tableName));
        Application.displayMessage(paginator.loadPage(1));
    }
}
