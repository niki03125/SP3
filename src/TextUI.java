package src;

import java.util.Scanner;

public class TextUI {
    private static Scanner scan = new Scanner(System.in);

    public static void displayMSG(String msg){
        System.out.println(msg);
    }

    public static String promptText(String msg){
        System.out.print(msg); //Stil brugeren et spørgsmål
        String input = scan.nextLine();
        TextUI.cleanScreen();
        return input;
    }

    public static int promptNumeric(String msg){
        System.out.print(msg); //Stil brugeren et spørgsmål
        String input = scan.nextLine();
        int number;
        try {
            number = Integer.parseInt(input);
        } catch (NumberFormatException e){
            System.out.print("Please type a number: ");
            number = promptNumeric(msg);
        }
        TextUI.cleanScreen();
        return number;
    }

    public static boolean promptBinary(String msg){
        String input = promptText(msg);
        if(input.equalsIgnoreCase("Y")){
            TextUI.cleanScreen();
            return true;
        }else if(input.equalsIgnoreCase("N")){
            TextUI.cleanScreen();
            return false;
        } else {
            TextUI.cleanScreen();
            return promptBinary(msg);
        }
    }

    public static void cleanScreen(){
        for (int i = 0; i < 100; i++){
            System.out.println();
        }
    }
}
