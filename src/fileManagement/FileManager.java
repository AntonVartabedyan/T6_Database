package fileManagement;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManager {
     File file;
     List<String> tables = new ArrayList<>();

     List<String> headerLineVals;
     List<List<String>> info;
    public FileManager(){
    }
    public void readFile(String fileName){
        try{
            file = new File(fileName);
            tables = new ArrayList<>();
            Scanner myReader = new Scanner(file);

            while(myReader.hasNextLine()){
                tables.add(myReader.nextLine());
            }

            for (int i = 0; i < tables.size(); i++){
                System.out.println(tables.get(i));
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            try {
                if(file.createNewFile()){
                    System.out.println("File created: " + file.getName());
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } catch (Exception e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public  String closeFile(){
        String endMessage;
        endMessage = "Successfully closed ";
        endMessage += file.getName();
        try{
            file = null;
        }catch (Exception e){
            endMessage = "Something went wrong";
        }
        return endMessage;
    }

    public String save(){
        try {
            if(file.delete()){
                file.createNewFile();
            }else throw new IOException();

        }catch (IOException e){
            return "An error occured";
        }
        try(PrintWriter writer = new PrintWriter(file)){
            for (String headerEl : headerLineVals){
                writer.print(headerEl + " ");
            }
            writer.println();
            for (List<String> line : info){
                for (String el : line){
                    writer.print(el + "; ");
                }
                writer.println();
            }
            writer.close();
            return "Successfully saved the data in " + file.getName();
        }catch (IOException e){
            return "Something went wrong";
        }
    }

    public String save(String filename){
        try(PrintWriter writer = new PrintWriter(new FileWriter(filename))){
            for (String headerEl : headerLineVals){
                writer.print(headerEl + " ");
            }
            writer.println();
            for (List<String> line : info){
                for (String el : line){
                    writer.print(el + "; ");
                }
                writer.println();
            }
            writer.close();
            return "Successfully saved the data in " + filename;
        }catch (IOException e){
            return "Something went wrong";
        }
    }
}
