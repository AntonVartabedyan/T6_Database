package main;

import fileManagement.FileManager;

import java.io.IOException;
import java.util.*;

public class Application {

    public static Map<String, Runnable> commandMap;
    public static FileManager fileManager;
    public static Scanner input;

    public static void main(String[] args) throws IOException {
        fileManager = new FileManager();
        List<String> commands = new ArrayList<>();
        input = new Scanner(System.in);
        fillCommandMap();


        do {
            System.out.print("Enter a command: ");
            String cmd = input.next().toLowerCase();
            try{
                commandMap.get(cmd).run();
            }catch (NullPointerException E){
                System.out.println("Invalid command! For a list of commands type 'help'.");
            }

        }while(true);
    }

    public static void fillCommandMap(){
        commandMap = new HashMap<>();
        commandMap.put("open", ()->{
            String fileName = input.next();
            fileManager.readFile(fileName);
        });
        commandMap.put("close", fileManager::closeFile);
        commandMap.put("save",  fileManager::save);
        commandMap.put("saveas",  ()->{
            String fileName = input.next();
            System.out.println(fileManager.save(fileName));
        });
        commandMap.put("help",  () -> System.out.print(pullUpHelp()));
        commandMap.put("exit", Application::exitProgram);

    }

    public static String pullUpHelp(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("The following commands are supported:\n");
        stringBuilder.append("open <file> opens <file>\n");
        stringBuilder.append("close             closes currently open file\n");
        stringBuilder.append("save              saves the currently open file\n");
        stringBuilder.append("save as <file>    saves the currently open file\n");
        stringBuilder.append("save as <file>    saves the currently open file in <file>\n");
        stringBuilder.append("help              prints this information\n");
        stringBuilder.append("exit              exits the program\n");

        return stringBuilder.toString();
    }

    public static void exitProgram(){
        System.exit(0);
    }
}
