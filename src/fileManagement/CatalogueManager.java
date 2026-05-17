package fileManagement;
import main.Application;
import exceptions.ExistingFileException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * Catalogue manager holds the names of all the tables and manages the main file containing all names
 */
public class CatalogueManager{
     protected File file;
     protected List<String> fileNames = new ArrayList<>();
     public boolean isFileOpened = false;


    public CatalogueManager(){

    }

    /**
     * Function for reading the given file name and creating a list of all the tables listed in it
     * @param fileName
     * @return
     */
    public String readFile(String fileName){
        if (!fileName.endsWith(".cat")){
            return null;
        }
        try{
            file = new File(fileName);
            fileNames = new ArrayList<>();
            Scanner myReader = new Scanner(file);

            while(myReader.hasNextLine()){
                fileNames.add(myReader.nextLine());
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            try {
                if(file.createNewFile()){
                    Application.displayMessage("File created: " + file.getName());
                }
            } catch (Exception ex) {
                Application.displayMessage("An error occurred.");
                ex.printStackTrace();
            }
        } catch (Exception e){
            Application.displayMessage("An error occurred.");
            e.printStackTrace();
        }
        this.isFileOpened = true;
        return fileName;
    }

    /**
     * Function for closing the file and removing the contents of the table list
     */
    public  void closeFile(){
        String endMessage;
        try{
            String fileName = file.getName();
            file = null;
            fileNames.clear();
            endMessage = "Successfully closed ";
            endMessage += fileName;
            this.isFileOpened = false;
        }catch (Exception e){
            endMessage = "Something went wrong";
        }
        Application.displayMessage(endMessage);
    }

    /**
     * Function for saving all the data into the file
     */
    public void save(){
        try {
            if(file.delete()){
                file.createNewFile();

            }else throw new IOException();

        }catch (IOException e){
            Application.displayMessage("An error occured");
            return;
        }
        try(PrintWriter writer = new PrintWriter(file)){
            for (String val : fileNames){
                writer.print(val + "\n");
            }
            writer.close();
            Application.displayMessage( "Successfully saved the data in " + file.getName());
        }catch (IOException e){
            Application.displayMessage("Something went wrong");
        }
    }

    /**
     * Function for getting all the table names
     * @return
     */
    public List<String> getFileNames() {
        return fileNames;
    }

    /**
     * Function for saving all the data into a new file
     * @param fileName
     */
    public void save(String fileName){
        file = new File(fileName);
        try(PrintWriter writer = new PrintWriter(new FileWriter(fileName))){
            for (String tableName : fileNames){
                writer.println(tableName);
            }
            writer.close();
            Application.displayMessage( "Successfully saved the data in " + fileName);
        }catch (IOException e){
            Application.displayMessage( "Something went wrong");
        }
    }

    /**
     * Function for adding the new table to the tabls list
     * @param fileName
     */
    public void importTable(String fileName){
        for (String fName : fileNames){
            if(fName.equals(fileName)) return;
        }
        fileNames.add(fileName + ".tbl");
    }

    /**
     * Function for printing out all the tables
     */
    public void showTables(){
        for (String fileName : fileNames){
            Application.displayMessage(fileName);
        }
    }

    /**
     * Function for renaming a file to a different name
     * @param oldName
     * @param newName
     */

    public void rename(String oldName, String newName){
        for(String val: fileNames){
            if(val.equals(newName)){
                throw new ExistingFileException(newName);
            }
        }
        for (int i = 0; i < fileNames.size(); i++){
            if(fileNames.get(i).equals(oldName + ".tbl")){
                fileNames.set(i, newName + ".tbl");
            }
        }
    }

    /**
     * Function for getting the parent of the database file (for example .\TestingFolder\);
     * @return
     */

    public String getFileLocation(){
        return (file.getParent() == null ? "" : file.getParent() + "\\");
    }
}
