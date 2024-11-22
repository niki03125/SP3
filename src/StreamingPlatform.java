package src;

import java.util.ArrayList;

public class StreamingPlatform {
    private String appName;
    private ArrayList<User> users;
    private ArrayList<Media> medias;

    public StreamingPlatform(String appName){
        this.appName = appName;
        this.users = new ArrayList<User>();
        this.medias = new ArrayList<Media>();
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Media> getMedias() {
        return medias;
    }

    public void addUser(User user){
        users.add(user);
    }

    public void removeUser(User user){
        users.remove(user);
    }

    public void addMedia(Media media){
        medias.add(media);
    }

    public void removeMedia(Media media){
        medias.remove(media);
    }







    public static void main(String[] args){
        StreamingPlatform sp1 = new StreamingPlatform("WBMM");
        System.out.println("Hello");
    }

}
