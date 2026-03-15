package main;

import fileManagement.FileReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        FileReader fileReader = new FileReader();

        Scanner input = new Scanner(System.in);
        Scanner fileNameScanner = new Scanner(System.in);
        do {
            System.out.print("Enter a command: ");
            String[] processedInput = input.nextLine().split(" ");
            List<String> commands = new ArrayList<>(Arrays.asList(processedInput));

            switch(commands.getFirst().toLowerCase()){
                case "open":
                    String fileName = commands.get(1);
                    fileReader.readFile(fileName);
                    break;
                case "exit":
                return;
            }
        }while(true);
    }
}
