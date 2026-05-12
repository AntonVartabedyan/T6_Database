package fileManagement;

import exceptions.OperationOnStringException;
import fileManagement.TableElement.Table;
import main.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class TableManager{
    /*
    * Table Template:
    * Table Name
    * Table Columns
    * Headers
    * Col1Val1; Col1Val2; Col1Val3; ...
    * Col2Val1; Col2Val2; Col2Val3; ...
    * ...
    */
    protected File file;
    protected Map<String, Table> tables = new HashMap<>();

    public void readFile(List<String> fileNames){
        tables.clear();
        for (String fileName : fileNames){
            if(!fileName.endsWith(".tbl")){
                continue;
            }
            try{
                file = new File(fileName);
                Scanner myReader = new Scanner(file);

                String tableName = myReader.nextLine();

                String columnsTotal = myReader.nextLine();

                String typesLine = myReader.nextLine();
                List<String> typesLineList = List.of(typesLine.split(" "));

                String headerLine = myReader.nextLine();
                List<String> headerLineVals = List.of(headerLine.split(" "));
                List<List<String>> info = new ArrayList<>();

                int j = 0;
                while(myReader.hasNextLine()){
                    info.add(new ArrayList<>());
                    String data = myReader.nextLine();
                    String[] dataPieces = data.split("(; )|;");
                    for (int i = 0; i < dataPieces.length; i++){
                        if (dataPieces[i].isEmpty() || dataPieces[i].equals(" ")){
                            dataPieces[i] = null;
                            info.get(j).add(null);
                            continue;
                        }
                        info.get(j).add(dataPieces[i]);
                    }
                    j++;
                }
                myReader.close();

                tables.put(tableName, new Table(tableName, Integer.parseInt(columnsTotal), typesLineList, headerLineVals, info));

            } catch (FileNotFoundException e) {
                try {
                    if(file.createNewFile()){
                        Application.displayMessage("File created: " + file.getName().substring(0,file.getName().length()-4));
                        tables.put(file.getName().substring(0,file.getName().length()-4), new Table(file.getName().substring(0,file.getName().length()-4)));
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (Exception e){
                try {
                    if(file.createNewFile()){
                        Application.displayMessage("File created: " + file.getName().substring(0,file.getName().length()-4));
                        tables.put(file.getName().substring(0,file.getName().length()-4), new Table(file.getName().substring(0,file.getName().length()-4)));
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        /*
            for (Table table: tables){
                Application.displayMessage(table.toString());
            }
        */
    }

    public  void closeFile(){
        String endMessage;
        endMessage = "Successfully closed ";
        endMessage += file.getName();
        try{
            file = null;
            tables.clear();
        }catch (Exception e){
            endMessage = "Something went wrong";
        }
        Application.displayMessage(endMessage);
    }

    public void save() {
        for (var table : tables.entrySet()){
            try{
                File currentFile = new File((file.getParent() == null ? "" : file.getParent() + "\\") + table.getValue().getTableName() + ".tbl");
                if(currentFile.delete()){
                    currentFile.createNewFile();
                }
                PrintWriter writer = new PrintWriter(currentFile);
                writer.print(table.getValue());
                writer.close();
                Application.displayMessage("Saved table " + table.getValue().getTableName());
            }catch (IOException e){
                Application.displayMessage("An error occured");
                return;
            }

        }
    }

    public void save(String filename) {
        file = new File(filename);
        save();
    }

    public void describe(String fileName){
        Application.displayMessage(getWholeTable(fileName).getTypesRow());
    }

    public List<String> getTable(String fileName){
        return List.of(getWholeTable(fileName).toString().split("\n"));
    }

    public void export(String tableName, String fileName){
        Table table = getWholeTable(tableName);
        try{
            File currentFile = new File(fileName);
            if(currentFile.delete()){
                currentFile.createNewFile();
            }
            PrintWriter writer = new PrintWriter(currentFile);
            writer.print(table);
            writer.close();
            Application.displayMessage("Exported table " + table.getTableName());
        }catch (IOException e){
            Application.displayMessage("An error occured");
            return;
        }
    }

    public List<List<String>> select(int columnN, String value, String tableName) {
        Table table = getWholeTable(tableName);
        if (table.getColumnCount() < columnN - 1) return new ArrayList<>();
        List<List<String>> answers = new ArrayList<>();
        for (int i = 0; i < table.getValues().size(); i++){
            if (table.getValues().get(i).get(columnN-1).equalsIgnoreCase("NULL") && !value.equalsIgnoreCase("null")) continue;
            if( table.getValues().get(i).get(columnN-1).equals(value)){
                answers.add(table.getValues().get(i));
            }
        }
        return answers;
    }

    public void addColumn(String tableName, String columnName, String columnType) {
        Table table = getWholeTable(tableName);
        table.addColumn(columnName,columnType);
    }

    public void update(String tableName, int searchColumnN, String searchValue, int targetColumnN, String targetValue) {
        Table table = getWholeTable(tableName);
        if (table.getColumnCount() < searchColumnN - 1) return;
        for (int i = 0; i < table.getValues().size(); i++){
            if (table.getValues().get(i).get(searchColumnN-1).equalsIgnoreCase("NULL") && !searchValue.equalsIgnoreCase("null")) continue;
            if( table.getValues().get(i).get(searchColumnN-1).equals(searchValue)){
                table.setValue(i,targetColumnN-1,targetValue);
            }
        }

    }

    public void remove(String tableName, int searchColumnN, String searchValue) {
        Table table = getWholeTable(tableName);
        if (table.getColumnCount() < searchColumnN - 1) return;
        for (int i = 0; i < table.getValues().size(); i++){
            if (table.getValues().get(i).get(searchColumnN-1).equalsIgnoreCase("NULL") && !searchValue.equalsIgnoreCase("null")) continue;
            if( table.getValues().get(i).get(searchColumnN-1).equals(searchValue)){
                table.removeValue(i);
            }
        }

    }

    public void insert(String tableName, List<String> columns){
        Table table = getWholeTable(tableName);
        List<String> vals = new ArrayList<>(); // re-adding them to a list to avoid error when user enters too many values
        for (int i = 0; i < table.getColumnCount(); i++){
            vals.add(columns.get(i));
        }
        table.setRow(vals);
    }

    public void rename(String oldName, String newName) {

        Table table = getWholeTable(oldName);
        table.setTableName(newName);
        tables.remove(oldName);
        tables.put(newName, table);
    }

    public int count(String tableName, int searchColumnN, String searchValue) {
        Table table = getWholeTable(tableName);
        if (table.getColumnCount() < searchColumnN - 1) return 0;
        int counter = 0;
        for (int i = 0; i < table.getValues().size(); i++){
            if (table.getValues().get(i).get(searchColumnN-1).equalsIgnoreCase("NULL") && !searchValue.equalsIgnoreCase("null")) continue;
            if( table.getValues().get(i).get(searchColumnN-1).equals(searchValue)){
                counter++;
            }
        }
        return counter;
    }

    public String innerJoin(String tableName1, int column1, String tableName2, int column2) {
        Table table1 = getWholeTable(tableName1);
        Table table2 = getWholeTable(tableName2);
        if (table1.getColumnCount() < column1 - 1 || table2.getColumnCount() < column2 - 1) return "Column number exceeds total columns!";

        String newTableName = tableName1+"_JOIN_" + tableName2;

        List<String> combinedTypes = new ArrayList<>();
        combinedTypes.addAll(table1.getTypesRowList());
        combinedTypes.addAll(table2.getTypesRowList());

        List<String> combinedHeaderValues = new ArrayList<>();
        combinedHeaderValues.addAll(table1.getHeaderValues());
        combinedHeaderValues.addAll(table2.getHeaderValues());

        Table newTable = new Table(newTableName, table1.getColumnCount() + table2.getColumnCount(), combinedTypes, combinedHeaderValues, new ArrayList<>());
        List<List<String>> answers = new ArrayList<>();
        for (List<String> vals1: table1.getValues()){
            for (List<String> vals2 : table2.getValues()){
                if(vals1.get(column1 - 1).equals(vals2.get(column2- 1))){
                    List<String> combinedRow = new ArrayList<>();
                    combinedRow.addAll(vals1);
                    combinedRow.addAll(vals2);
                    answers.add(combinedRow);
                }
            }
        }
        newTable.setValues(answers);
        tables.put(newTableName, newTable);
        return newTableName;
    }
    public float aggregate(String tableName, int searchColumn, String value, int targetColumn, Operation operation) {
        Table table = getWholeTable(tableName);
        if (table.getColumnCount() < searchColumn - 1) return 0;
        if(table.getHeaderValues().get(targetColumn-1).equals("String")){
            throw new OperationOnStringException();
        }

        float answers = 0;
        boolean firstOp = true;
        for (int i = 0; i < table.getValues().size(); i++){
            if (table.getValues().get(i).get(searchColumn-1).equalsIgnoreCase("NULL") && !value.equalsIgnoreCase("null")) continue;
            if( table.getValues().get(i).get(searchColumn-1).equals(value)){
                if (table.getValues().get(i).get(targetColumn-1).equalsIgnoreCase("null")) continue;
                switch (operation){
                    case Operation.SUM:
                        answers += Float.parseFloat(table.getValues().get(i).get(targetColumn-1));
                        break;
                    case Operation.PRODUCT:
                        if(firstOp){
                            firstOp = false;
                            answers = 1;
                        }
                        answers *= Float.parseFloat(table.getValues().get(i).get(targetColumn-1));
                        break;
                    case Operation.MAXIMUM:
                        if(firstOp){
                            firstOp = false;
                            answers = Float.parseFloat(table.getValues().get(i).get(targetColumn-1));
                            break;
                        }
                        answers = Math.max(answers, Float.parseFloat(table.getValues().get(i).get(targetColumn-1)));
                        break;
                    case Operation.MINIMUM:
                        if(firstOp){
                            firstOp = false;
                            answers = Float.parseFloat(table.getValues().get(i).get(targetColumn-1));
                            break;
                        }
                        answers = Math.min(answers, Float.parseFloat(table.getValues().get(i).get(targetColumn-1)));
                        break;
                }
            }
        }
        return answers;
    }

    public Table getWholeTable(String name) {
        Table table = tables.get(name);
        return table;
    }
    public boolean findIfTableExists(String name){
        return tables.get(name) != null;
    }
}
