package src;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class FileIO {
    public static HashMap<String, User> readUserData(String path) {
        HashMap<String, User> data = new HashMap<>();
        File file = new File(path);
        try{
            Scanner scan = new Scanner(file);

            while (scan.hasNextLine())  {
                String line = scan.nextLine();
                Object[] tmp = line.split(";");
                System.out.println(tmp[0]);
                data.put((String) tmp[0], new User((String) tmp[0], (String) tmp[1], (Integer) tmp[2], (String) tmp[3]));
            }
        } catch (FileNotFoundException e){
            System.out.println("File was not found");
        }
        for (String i : data.keySet()){
            System.out.println(i);
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
