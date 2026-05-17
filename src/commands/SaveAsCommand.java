package commands;

import fileManagement.CatalogueManager;
import fileManagement.TableManager;
import main.Controller;

import java.util.List;
/**
 * Command class save the database in a different file
 */
public class SaveAsCommand implements CommandInterface{
    private TableManager tableManager;
    private CatalogueManager catalogueManager;
    private List<String> commands;

    public SaveAsCommand(TableManager tableManager, CatalogueManager catalogueManager){
        this.tableManager = tableManager;
        this.catalogueManager = catalogueManager;
    }
    @Override
    public void execute() {
        this.commands = Controller.getCommands();
        if(commands.size() != 3) throw new NullPointerException();
        String fileName = commands.get(2);
        catalogueManager.save(fileName);
        tableManager.save(fileName, catalogueManager.getFileLocation());
    }
}
