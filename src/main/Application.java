package main;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        do {
            System.out.print("Enter a command: ");
            String command = input.nextLine();
            if(command.equalsIgnoreCase("exit")){
                return;
            }
        }while(true);
    }
}
