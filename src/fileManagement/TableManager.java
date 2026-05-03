package fileManagement;

import fileManagement.TableElement.Table;
import main.Application;

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
                        Application.displayMessage("File created: " + file.getName());
                        tables.put(file.getName(), new Table(file.getName(), 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (Exception e){
               Application.displayMessage("An error occurred.");
                e.printStackTrace();
                throw new RuntimeException(e);
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
        Application.displayMessage(tables.get(fileName).getTypesRow());
    }

    public List<String> getTable(String fileName){
        return List.of(tables.get(fileName).toString().split("\n"));
    }
}
