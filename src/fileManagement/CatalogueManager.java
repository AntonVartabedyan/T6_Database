package fileManagement;
import fileManagement.TableElement.Table;
import main.Application;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CatalogueManager{
     protected File file;
     protected List<String> fileNames = new ArrayList<>();


    public CatalogueManager(){

    }
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
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } catch (Exception e){
            Application.displayMessage("An error occurred.");
            e.printStackTrace();
        }

        return fileName;
    }

    public  void closeFile(){
        String endMessage;
        try{
            file = null;
            fileNames.clear();
            endMessage = "Successfully closed ";
            endMessage += file.getName();
        }catch (Exception e){
            endMessage = "Something went wrong";
        }
        Application.displayMessage(endMessage);
    }

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

    public List<String> getFileNames() {
        return fileNames;
    }

    public void save(String fileName){
        file = new File(fileName);
        try(PrintWriter writer = new PrintWriter(new FileWriter(fileName))){
            for (String tableName : fileNames){
                writer.println((file.getParent() == null ? "" : file.getParent() + "\\") + tableName);
            }
            writer.close();
            Application.displayMessage( "Successfully saved the data in " + fileName);
        }catch (IOException e){
            Application.displayMessage( "Something went wrong");
        }
    }

    public void importTable(String fileName){
        fileNames.add(fileName);
    }

    public void showTables(){
        for (String fileName : fileNames){
            Application.displayMessage(fileName);
        }
    }
}
