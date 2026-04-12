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

        commandMap.put("import", ()->{
            String fileName = input.next();
            //do function here
        });
        commandMap.put("showtables", ()->{
            //do function here
        });
        commandMap.put("describe", ()->{
            String tableName = input.next();
            //do function here
        });
        commandMap.put("print", ()->{
            String tableName = input.next();
            //do function here
        });
        commandMap.put("export", ()->{
            String tableName = input.next();
            String fileName = input.next();
            //do function here
        });
        commandMap.put("select", ()->{
            int columnN = input.nextInt();
            String value = input.next();
            String tableName = input.next();
            //do function here
        });
        commandMap.put("addcolumn", ()->{
            String tableName = input.next();
            String columnName = input.next();
            String columnType = input.next();
            //do function here
        });
        commandMap.put("update", ()->{
            String tableName = input.next();
            int searchColumnN = input.nextInt();
            String searchValue = input.next();
            int targetColumnN = input.nextInt();
            String targetValue = input.next();
            //do function here
        });
        commandMap.put("delete", ()->{
            String tableName = input.next();
            int searchColumnN = input.nextInt();
            String searchValue = input.next();
            //do function here
        });
        commandMap.put("insert", ()->{
            String tableName = input.next();
            //do function here
        });
        commandMap.put("innerjoin", ()->{
            String tableName1 = input.next();
            int column1 = input.nextInt();
            String tableName2 = input.next();
            int column2 = input.nextInt();
            //do function here
        });
        commandMap.put("rename", ()->{
            String tableName = input.next();
            String tableNamenew = input.next();
            //do function here
        });
        commandMap.put("count", ()->{
            String tableName = input.next();
            int searchColumnN = input.nextInt();
            String searchValue = input.next();
            //do function here
        });
        commandMap.put("aggregate", ()->{
            String tableName = input.next();
            int searchColumnN = input.nextInt();
            String searchValue = input.next();
            int targetColumnN = input.nextInt();
            String operation = input.next();
            //do function here
        });
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
