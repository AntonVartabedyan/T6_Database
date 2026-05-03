package main;

import fileManagement.CatalogueManager;
import fileManagement.TableManager;
import pagination.Paginator;

import java.util.*;

public class Controller {
    public  Map<String, Runnable> commandMap;
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
            if (!(commands.getFirst().equals("next")  || commands.getFirst().equals("prev"))){
                paginator.clearPaginator();
            }
            try{
                commandMap.get(commands.getFirst().toLowerCase()).run();
            }catch (NullPointerException E){
                Application.displayMessage("Invalid command! For a list of commands type 'help'.");
            }

        }while(true);
    }

    public void fillCommandMap(){
        commandMap = new HashMap<>();
        commandMap.put("open", ()->{
            String fileName = commands.get(1);
            catalogueManager.readFile(fileName);
            tableManager.readFile(catalogueManager.getFileNames());
        });
        commandMap.put("close", catalogueManager::closeFile);
        commandMap.put("save",  ()->{
            if(commands.size() >= 2 && commands.get(1).equals("as")){
                commandMap.get("save as").run();
            }else{
                catalogueManager.save();
                tableManager.save();
            }
        });
        commandMap.put("save as",  ()->{
            String fileName = commands.get(2);
           catalogueManager.save(fileName);
           tableManager.save(fileName);
        });
        commandMap.put("help",  () -> System.out.print(pullUpHelp()));
        commandMap.put("exit", Application::exitProgram);

        commandMap.put("import", ()->{
            String fileName = commands.get(1);
            catalogueManager.importTable(fileName);
        });
        commandMap.put("showtables", ()->{
            catalogueManager.showTables();
        });
        commandMap.put("describe", ()->{
            String tableName = commands.get(1);
            tableManager.describe(tableName);
        });
        commandMap.put("print", ()->{
            String tableName = commands.get(1);
            paginator.setRows(tableManager.getTable(tableName));
            Application.displayMessage(paginator.loadPage(1));
        });
        commandMap.put("export", ()->{
            String tableName = commands.get(1);
            String fileName = commands.get(2);
            //do function here
        });
        commandMap.put("select", ()->{
            int columnN = Integer.parseInt(commands.get(1));
            String value = commands.get(2);
            String tableName = commands.get(3);
            //do function here
        });
        commandMap.put("addcolumn", ()->{
            String tableName = commands.get(1);
            String columnName = commands.get(2);
            String columnType = commands.get(3);
            //do function here
        });
        commandMap.put("update", ()->{
            String tableName = commands.get(1);
            int searchColumnN = Integer.parseInt(commands.get(2));
            String searchValue = commands.get(3);
            int targetColumnN =Integer.parseInt(commands.get(4));
            String targetValue = commands.get(5);
            //do function here
        });
        commandMap.put("delete", ()->{
            String tableName = commands.get(1);
            int searchColumnN = Integer.parseInt(commands.get(2));
            String searchValue = commands.get(3);
            //do function here
        });
        commandMap.put("insert", ()->{
            String tableName = commands.get(1);
            List<String> collumns = new ArrayList<>();
            for (int i = 2; i < commands.size(); i++)
            {
                collumns.add(commands.get(i));
            }
            //do function here
        });
        commandMap.put("innerjoin", ()->{
            String tableName1 = commands.get(1);
            int column1 = Integer.parseInt(commands.get(2));
            String tableName2 = commands.get(3);
            int column2 = Integer.parseInt(commands.get(4));
            //do function here
        });
        commandMap.put("rename", ()->{
            String tableName = commands.get(1);
            String tableNamenew = commands.get(2);
            //do function here
        });
        commandMap.put("count", ()->{
            String tableName = commands.get(1);
            int searchColumnN = Integer.parseInt(commands.get(2));
            String searchValue = commands.get(3);
            //do function here
        });
        commandMap.put("aggregate", ()->{
            String tableName = commands.get(1);
            int searchColumnN = Integer.parseInt(commands.get(2));
            String searchValue = commands.get(3);
            int targetColumnN = Integer.parseInt(commands.get(4));
            String operation = commands.get(5);
            //do function here
        });
        commandMap.put("prev", ()->{
           Application.displayMessage(paginator.loadPage((paginator.getCurrentPage() - 1 == 0) ? 1 : paginator.getCurrentPage() - 1));
        });
        commandMap.put("next", ()->{
            Application.displayMessage(paginator.loadPage(paginator.getCurrentPage() + 1));
        });
    }
    public String pullUpHelp(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("The following commands are supported:\n");
        stringBuilder.append("open <file> Opens <file>\n");
        stringBuilder.append("close             Closes currently open file\n");
        stringBuilder.append("save              Saves the currently open file\n");
        stringBuilder.append("save as <file>    Saves the currently open file\n");
        stringBuilder.append("save as <file>    Saves the currently open file in <file>\n");
        stringBuilder.append("help              Prints this information\n");
        stringBuilder.append("exit              Exits the program\n");
        stringBuilder.append("------------------------------------------------------------------\n");
        stringBuilder.append("import <fileName> Add new table\n");
        stringBuilder.append("showtables        Shows a list with the names of all created tables\n");
        stringBuilder.append("describe <name>   Shows all information for the column types of given table\n");
        stringBuilder.append("print <name>      Shows all rows of a given table. Has pagination.\n");
        stringBuilder.append("export <name> <file name>  Records the table into a file\n");
        stringBuilder.append("select <column-n> <value> <table name> Shows all rows in the table with value equal to the desired in column-n\n");
        stringBuilder.append("addcolumn <table name> <column name> <column type>  Adds a new column to the table. All existing rows hold NULL in that column\n");
        stringBuilder.append("update <table name> <search column n> <search value> <target column n> <target value> Updates all rows whose value in <search column n> is equal to <search value> with <target value> in <target column n>\n");
        stringBuilder.append("delete <table name> <search column n> <search value> Deletes all rows in the table whose <search column n> contains <search value>\n");
        stringBuilder.append("insert <table name> <column 1> ... <column n> Inserts a new row in the table\n");
        stringBuilder.append("innerjoin <table1> <column n1> <table 2> <column n2> Creates a new table, joining the mentioned tables by the columns\n");
        stringBuilder.append("rename <old name> <new name> renames the table. The name must be unique.\n");
        stringBuilder.append("count <table name> <search column n> <search value> Counts all rows that meet the condition.\n");
        stringBuilder.append("aggregate <table name> <search column n> <search value> <target column n> <operation> Returns the operation of all values in <target column n> that meet the condition. \n");

        return stringBuilder.toString();
    }
}
