package main;

import fileManagement.FileReader;

import java.io.IOException;
import java.util.*;

public class Application {

    public static Map<String, Runnable> commandMap;
    public static FileReader fileReader;
    public static Scanner input;

    public static void main(String[] args) throws IOException {
        fileReader = new FileReader();
        List<String> commands = new ArrayList<>();
        input = new Scanner(System.in);
        fillCommandMap();


        do {
            System.out.print("Enter a command: ");
            String cmd = input.next().toLowerCase();
            commandMap.get(cmd).run();
        }while(true);
    }

    public static void fillCommandMap(){
        commandMap = new HashMap<>();
        commandMap.put("open", ()->{
            String fileName = input.next();
            fileReader.readFile(fileName);
        });
        commandMap.put("close", fileReader::closeFile);
        commandMap.put("exit", Application::exitProgram);
    }

    public static void exitProgram(){
        System.exit(0);
    }
}
