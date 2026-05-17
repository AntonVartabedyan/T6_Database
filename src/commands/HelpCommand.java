package commands;

import fileManagement.CatalogueManager;
import fileManagement.TableManager;
import main.Application;
import main.Controller;
import pagination.Paginator;

import java.util.ArrayList;
import java.util.List;
/**
 * Command class to pull up the help menu
 */
public class HelpCommand implements CommandInterface{
    private Paginator paginator;

    public HelpCommand(Paginator paginator){
        this.paginator = paginator;
    }
    @Override
    public void execute() {
        paginator.setRows(pullUpHelp());
        Application.displayMessage(paginator.loadPage(1));
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
