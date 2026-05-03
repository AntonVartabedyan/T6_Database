package main;

import fileManagement.CatalogueManager;
import fileManagement.TableElement.Table;
import fileManagement.TableManager;

import java.io.IOException;
import java.util.*;

public class Application {



    public static void main(String[] args) throws IOException {
       Controller controller = new Controller();
    }
    public static void displayMessage(String message){
        System.out.println(message);
    }
    public static void displayMessageWONewLine(String message){
        System.out.print(message);
    }
    public static void exitProgram(){
        System.exit(0);
    }
}
