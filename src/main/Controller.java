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
            if (!(commands.getFirst().equals("next") || commands.getFirst().equals("prev"))){
                paginator.clearPaginator();
            }
            try{
                commandMap.get(commands.getFirst().toLowerCase()).run();
            }
            catch (NullPointerException E){
                Application.displayMessage("Invalid command! For a list of commands type 'help'.");
            }

        }while(true);
    }

    public void fillCommandMap(){
        commandMap = new HashMap<>();
        commandMap.put("open", ()->{
            if(commands.size() != 2) throw new NullPointerException();
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
            if(commands.size() != 3) throw new NullPointerException();
            String fileName = commands.get(2);
           catalogueManager.save(fileName);
           tableManager.save(fileName);
        });
        commandMap.put("help",  () -> {
            paginator.setRows(pullUpHelp());
            Application.displayMessage(paginator.loadPage(1));
        });
        commandMap.put("exit", Application::exitProgram);

        commandMap.put("import", ()->{
            if(commands.size() != 2) throw new NullPointerException();
            String tableName = commands.get(1);
            if(tableManager.findIfTableExists(tableName)){
                Application.displayMessage("This table already exists");
                return;
            }
            catalogueManager.importTable(tableName);
            tableManager.readFile(catalogueManager.getFileNames());
        });
        commandMap.put("showtables", ()->{
            catalogueManager.showTables();
        });
        commandMap.put("describe", ()->{
            if(commands.size() != 2) throw new NullPointerException();
            String tableName = commands.get(1);
            if(!tableManager.findIfTableExists(tableName)){
                Application.displayMessage("This table doesnt exist!");
                return;
            }
            tableManager.describe(tableName);
        });
        commandMap.put("print", ()->{
            if(commands.size() != 2) throw new NullPointerException();
            String tableName = commands.get(1);
            if(!tableManager.findIfTableExists(tableName)){
                Application.displayMessage("This table doesnt exist!");
                return;
            }
            paginator.setRows(tableManager.getTable(tableName));
            Application.displayMessage(paginator.loadPage(1));
        });
        commandMap.put("export", ()->{
            if(commands.size() != 3) throw new NullPointerException();
            String tableName = commands.get(1);
            String fileName = commands.get(2);
            if(!tableManager.findIfTableExists(tableName)){
                Application.displayMessage("This table doesnt exist!");
                return;
            }
            tableManager.export(tableName,fileName);
        });
        commandMap.put("select", ()->{
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
        });
        commandMap.put("addcolumn", ()->{
            if(commands.size() != 4) throw new NullPointerException();
            String tableName = commands.get(1);
            String columnName = commands.get(2);
            String columnType = commands.get(3);
            if(!tableManager.findIfTableExists(tableName)){
                Application.displayMessage("This table doesnt exist!");
                return;
            }
            tableManager.addColumn(tableName,columnName,columnType);
        });
        commandMap.put("update", ()->{
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
        });
        commandMap.put("delete", ()->{
            if(commands.size() != 4) throw new NullPointerException();
            String tableName = commands.get(1);
            int searchColumnN = Integer.parseInt(commands.get(2));
            String searchValue = commands.get(3);
            if(!tableManager.findIfTableExists(tableName)){
                Application.displayMessage("This table doesnt exist!");
                return;
            }
            tableManager.remove(tableName,searchColumnN,searchValue);
        });
        commandMap.put("insert", ()->{
            String tableName = commands.get(1);
            List<String> collumns = new ArrayList<>();
            for (int i = 2; i < commands.size(); i++)
            {
                collumns.add(commands.get(i));
            }
            if(!tableManager.findIfTableExists(tableName)){
                Application.displayMessage("This table doesnt exist!");
                return;
            }
            tableManager.insert(tableName,collumns);
        });
        commandMap.put("innerjoin", ()->{
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
        });
        commandMap.put("rename", ()->{
            if(commands.size() != 3) throw new NullPointerException();
            String tableName = commands.get(1);
            String tableNamenew = commands.get(2);
            if(!tableManager.findIfTableExists(tableName)){
                Application.displayMessage("This table doesnt exist!");
                return;
            }
            if(tableManager.findIfTableExists(tableNamenew)){
                Application.displayMessage("This table doesnt exist!");
                return;
            }
            catalogueManager.rename(tableName,tableNamenew);
            tableManager.rename(tableName,tableNamenew);
        });
        commandMap.put("count", ()->{
            if(commands.size() != 4) throw new NullPointerException();
            String tableName = commands.get(1);
            int searchColumnN = Integer.parseInt(commands.get(2));
            String searchValue = commands.get(3);
            if(!tableManager.findIfTableExists(tableName)){
                Application.displayMessage("This table doesnt exist!");
                return;
            }
            Application.displayMessage(tableManager.count(tableName,searchColumnN,searchValue) + " columns contain the searched value " + searchValue + ".");
        });
        commandMap.put("aggregate", ()->{
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
        });
        commandMap.put("prev", ()->{
           Application.displayMessage(paginator.loadPage((paginator.getCurrentPage() - 1 == 0) ? 1 : paginator.getCurrentPage() - 1));
        });
        commandMap.put("next", ()->{
            Application.displayMessage(paginator.loadPage(paginator.getCurrentPage() + 1));
        });
    }
    public List<String> pullUpHelp(){
        List<String> stringBuilder = new ArrayList<>();
        stringBuilder.add("The following commands are supported:\n");
        stringBuilder.add("open <file> Opens <file>\n");
        stringBuilder.add("close             Closes currently open file\n");
        stringBuilder.add("save              Saves the currently open file\n");
        stringBuilder.add("save as <file>    Saves the currently open file\n");
        stringBuilder.add("save as <file>    Saves the currently open file in <file>\n");
        stringBuilder.add("help              Prints this information\n");
        stringBuilder.add("exit              Exits the program\n");
        stringBuilder.add("------------------------------------------------------------------\n");
        stringBuilder.add("import <fileName> Add new table\n");
        stringBuilder.add("showtables        Shows a list with the names of all created tables\n");
        stringBuilder.add("describe <name>   Shows all information for the column types of given table\n");
        stringBuilder.add("print <name>      Shows all rows of a given table. Has pagination.\n");
        stringBuilder.add("export <name> <file name>  Records the table into a file\n");
        stringBuilder.add("select <column-n> <value> <table name> Shows all rows in the table with value equal to the desired in column-n\n");
        stringBuilder.add("addcolumn <table name> <column name> <column type>  Adds a new column to the table. All existing rows hold NULL in that column\n");
        stringBuilder.add("update <table name> <search column n> <search value> <target column n> <target value> Updates all rows whose value in <search column n> is equal to <search value> with <target value> in <target column n>\n");
        stringBuilder.add("delete <table name> <search column n> <search value> Deletes all rows in the table whose <search column n> contains <search value>\n");
        stringBuilder.add("insert <table name> <column 1> ... <column n> Inserts a new row in the table\n");
        stringBuilder.add("innerjoin <table1> <column n1> <table 2> <column n2> Creates a new table, joining the mentioned tables by the columns\n");
        stringBuilder.add("rename <old name> <new name> renames the table. The name must be unique.\n");
        stringBuilder.add("count <table name> <search column n> <search value> Counts all rows that meet the condition.\n");
        stringBuilder.add("aggregate <table name> <search column n> <search value> <target column n> <operation> Returns the operation of all values in <target column n> that meet the condition. \n");

        return stringBuilder;
    }
}
