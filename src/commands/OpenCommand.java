package commands;

import fileManagement.CatalogueManager;
import fileManagement.TableManager;
import main.Controller;

import java.util.List;

public class OpenCommand implements CommandInterface{
    private TableManager tableManager;
    private CatalogueManager catalogueManager;
    private List<String> commands;

    public OpenCommand(TableManager tableManager, CatalogueManager catalogueManager){
        this.tableManager = tableManager;
        this.catalogueManager = catalogueManager;
    }
    @Override
    public void execute() {
        this.commands = Controller.getCommands();
        if(this.commands.size() != 2) throw new NullPointerException();
        String fileName = this.commands.get(1);
        this.catalogueManager.readFile(fileName);
        this.tableManager.readFile(this.catalogueManager.getFileNames(), catalogueManager.getFileLocation());
    }
}
