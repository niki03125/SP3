package src;

import java.util.ArrayList;

public class Load {
    private ArrayList<User> users;

    public Load(ArrayList<User> users) {
        this.users = users;
    }

    public void loadUsers() {
        ArrayList<String> data = FileIO.readData("data/userdata.csv");
        for (String s : data) {
            String[] values = s.split(";");
            users.add(new User(values[0].trim(), values[1].trim(), Integer.parseInt(values[2].trim()), values[3].trim()));
        }
    }
}
