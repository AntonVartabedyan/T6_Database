package main;

import commands.*;
import fileManagement.CatalogueManager;
import fileManagement.TableManager;
import pagination.Paginator;

import java.util.*;

public class Controller {
    public  Map<CommandEnum, CommandInterface> commandMap;
    public  CatalogueManager catalogueManager;
    public  TableManager tableManager;
    public Paginator paginator;
    public  Scanner input;

    static List<String> commands;

    public Controller() {
        this.init();
    }

    private void init(){
        tableManager = new TableManager();
        catalogueManager = new CatalogueManager();
        paginator = new Paginator(9);
        input = new Scanner(System.in);
        fillCommandMap();

        do {
           Application.displayMessageWONewLine("Enter a command: ");
            String cmdLine = input.nextLine();
            commands = List.of(cmdLine.split(" "));


            try{
                CommandEnum cmd;
                if(CommandEnum.contains(commands.getFirst().toUpperCase())){
                    cmd = CommandEnum.valueOf(commands.getFirst().toUpperCase());
                }else throw new NullPointerException(commands.getFirst().toUpperCase());
                if(!catalogueManager.isFileOpened && !(commands.getFirst().equalsIgnoreCase("OPEN") || commands.getFirst().equalsIgnoreCase("HELP") || commands.getFirst().equalsIgnoreCase("EXIT"))){
                    Application.displayMessage("No database selected!");
                    continue;
                }
                if (!(commands.getFirst().equalsIgnoreCase(String.valueOf(CommandEnum.valueOf("NEXT"))) || commands.getFirst().equalsIgnoreCase(String.valueOf(CommandEnum.valueOf("PREV"))))){
                    paginator.clearPaginator();
                }

                if(commands.size() == 3 && commands.get(0).equalsIgnoreCase("save") && commands.get(1).equalsIgnoreCase("as")){
                    commandMap.get(CommandEnum.SAVE_AS).execute();
                }else{
                    commandMap.get(cmd).execute();
                }
            }
            catch (NullPointerException E){
                Application.displayMessage("Invalid command! For a list of commands type 'help'.");
            }

        }while(true);
    }

    public void fillCommandMap(){
        commandMap = new HashMap<>();
        commandMap.put(CommandEnum.OPEN, new OpenCommand(tableManager, catalogueManager));
        commandMap.put(CommandEnum.CLOSE, new CloseCommand(catalogueManager, tableManager));
        commandMap.put(CommandEnum.SAVE, new SaveCommand(tableManager, catalogueManager));
        commandMap.put(CommandEnum.SAVE_AS, new SaveAsCommand(tableManager, catalogueManager));
        commandMap.put(CommandEnum.HELP, new HelpCommand(paginator));
        commandMap.put(CommandEnum.EXIT, new ExitCommand());
        commandMap.put(CommandEnum.IMPORT, new ImportCommand(tableManager,catalogueManager));
        commandMap.put(CommandEnum.SHOWTABLES, new ShowTablesCommand(catalogueManager));
        commandMap.put(CommandEnum.DESCRIBE, new DescribeCommand(tableManager));
        commandMap.put(CommandEnum.PRINT, new PrintCommand(tableManager, catalogueManager, paginator));
        commandMap.put(CommandEnum.EXPORT, new ExportCommand(tableManager));
        commandMap.put(CommandEnum.SELECT, new SelectCommand(tableManager, paginator));
        commandMap.put(CommandEnum.ADDCOLUMN, new AddColumnCommand(tableManager));
        commandMap.put(CommandEnum.UPDATE, new UpdateCommand(tableManager));
        commandMap.put(CommandEnum.DELETE, new DeleteCommand(tableManager));
        commandMap.put(CommandEnum.INSERT, new InsertCommand(tableManager));
        commandMap.put(CommandEnum.INNERJOIN, new InnerJoinCommand(tableManager,catalogueManager));
        commandMap.put(CommandEnum.RENAME, new RenameCommand(tableManager, catalogueManager));
        commandMap.put(CommandEnum.COUNT, new CountCommand(tableManager));
        commandMap.put(CommandEnum.AGGREGATE, new AggregateCommand(tableManager));
        commandMap.put(CommandEnum.PREV, new PrevCommand(paginator));
        commandMap.put(CommandEnum.NEXT, new NextCommand(paginator));
    }


    public static List<String> getCommands() {
        return commands;
    }
}
