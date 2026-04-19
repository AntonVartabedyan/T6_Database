package main;

import fileManagement.FileManager;

import java.io.IOException;
import java.util.*;

public class Application {

    public static Map<String, Runnable> commandMap;
    public static FileManager fileManager;
    public static Scanner input;

    static List<String> commands;

    public static void main(String[] args) throws IOException {
        fileManager = new FileManager();
        input = new Scanner(System.in);
        fillCommandMap();



        do {
            System.out.print("Enter a command: ");
            String cmdLine = input.nextLine();
            commands = List.of(cmdLine.split(" "));
            try{
                commandMap.get(commands.getFirst().toLowerCase()).run();
            }catch (NullPointerException E){
                System.out.println("Invalid command! For a list of commands type 'help'.");
            }

        }while(true);
    }

    public static void fillCommandMap(){
        commandMap = new HashMap<>();
        commandMap.put("open", ()->{
            String fileName = commands.get(1);
            fileManager.readFile(fileName);
        });
        commandMap.put("close", fileManager::closeFile);
        commandMap.put("save",  ()->{
            if(commands.size() >= 2 && commands.get(1).equals("as")){
                commandMap.get("save as").run();
            }else{
                fileManager.save();
            }
        });
        commandMap.put("save as",  ()->{
            String fileName = commands.get(2);
            System.out.println(fileManager.save(fileName));
        });
        commandMap.put("help",  () -> System.out.print(pullUpHelp()));
        commandMap.put("exit", Application::exitProgram);

        commandMap.put("import", ()->{
            String fileName = commands.get(1);
            //do function here
        });
        commandMap.put("showtables", ()->{
            //do function here
        });
        commandMap.put("describe", ()->{
            String tableName = commands.get(1);
            //do function here
        });
        commandMap.put("print", ()->{
            String tableName = commands.get(1);
            //do function here
        });
        commandMap.put("export", ()->{
            String tableName = commands.get(1);
            String fileName = commands.get(2);
            //do function here
        });
        commandMap.put("select", ()->{
            int columnN = Integer.parseInt(commands.get(1));
            String value = commands.get(2);
            String tableName = commands.get(3);
            //do function here
        });
        commandMap.put("addcolumn", ()->{
            String tableName = commands.get(1);
            String columnName = commands.get(2);
            String columnType = commands.get(3);
            //do function here
        });
        commandMap.put("update", ()->{
            String tableName = commands.get(1);
            int searchColumnN = Integer.parseInt(commands.get(2));
            String searchValue = commands.get(3);
            int targetColumnN =Integer.parseInt(commands.get(4));
            String targetValue = commands.get(5);
            //do function here
        });
        commandMap.put("delete", ()->{
            String tableName = commands.get(1);
            int searchColumnN = Integer.parseInt(commands.get(2));
            String searchValue = commands.get(3);
            //do function here
        });
        commandMap.put("insert", ()->{
            String tableName = commands.get(1);
            List<String> collumns = new ArrayList<>();
            for (int i = 2; i < commands.size(); i++)
            {
                collumns.add(commands.get(i));
            }
            //do function here
        });
        commandMap.put("innerjoin", ()->{
            String tableName1 = commands.get(1);
            int column1 = Integer.parseInt(commands.get(2));
            String tableName2 = commands.get(3);
            int column2 = Integer.parseInt(commands.get(4));
            //do function here
        });
        commandMap.put("rename", ()->{
            String tableName = commands.get(1);
            String tableNamenew = commands.get(2);
            //do function here
        });
        commandMap.put("count", ()->{
            String tableName = commands.get(1);
            int searchColumnN = Integer.parseInt(commands.get(2));
            String searchValue = commands.get(3);
            //do function here
        });
        commandMap.put("aggregate", ()->{
            String tableName = commands.get(1);
            int searchColumnN = Integer.parseInt(commands.get(2));
            String searchValue = commands.get(3);
            int targetColumnN = Integer.parseInt(commands.get(4));
            String operation = commands.get(5);
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
