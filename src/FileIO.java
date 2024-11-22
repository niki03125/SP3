package src;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileIO {
    public static ArrayList<String> readPlayerData(String path) {
        ArrayList<String> data = new ArrayList<>();
        File file = new File(path);
        try{
            Scanner scan = new Scanner(file);
            scan.nextLine(); //skip header (tjek om vi overhovede har brug for denne linje)

            while (scan.hasNextLine())  {
                String line = scan.nextLine();
                data.add(line);
            }
        } catch (FileNotFoundException e){
            System.out.println("File was not found");
        }
        return data;
    }

    public static void saveData(ArrayList <User> users, String path, String header) {
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(header + "\n");
            for (User u : users)  {
                writer.write(u + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("something went wrong when writing to file");
        }

    }
}
