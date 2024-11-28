package src;

import java.util.ArrayList;

public class Save {
    private User currentUser;
    private ArrayList<User> users;

    public Save(ArrayList<User> users) {
        this.users = users;
    }

    public void saveUserLists(User currentUser){
        currentUser.mediaToString(currentUser.getSaved(), "Saved");
        currentUser.mediaToString(currentUser.getSeen(), "Seen");
        currentUser.mediaToString(currentUser.getSpecialPlayLists(), "Special");
    }

    public void usersToText(){
        ArrayList<String> usersAsText = new ArrayList<>();
        for (User u : users) {
            usersAsText.add(u.toString());
        }
        FileIO.saveData(usersAsText, "data/userdata.csv");
    }
}
