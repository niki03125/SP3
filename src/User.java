package src;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private int birthdayYear;
    private String gender;
    private ArrayList<Media> seen;
    private ArrayList<Media> saved;
    private int id;
    private static int idCount = 1;

   public User (String username, String password, int birthdayYear, String gender){
        this.username = username;
        this.password = password;
        this.birthdayYear = birthdayYear;
        this.gender = gender;
        this.seen = new ArrayList<Media>();
        this.saved = new ArrayList<Media>();
        this.id = idCount++;

   }

   //Getter
   public String getUsername(){
       return username;
   }

   public String getPassword(){
       return password;
   }

   public int getBirthdayYear(){
       return birthdayYear;
   }

   public String getGender(){
       return gender;
   }

   public   ArrayList<Media> getSeen() {
        return this.seen;
   }

   public ArrayList<Media> getSaved() {
        return saved;
   }

   public int getId() {
        return id;
   }

   //Setter
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBirthdayYear(int birthdayYear) {
        this.birthdayYear = birthdayYear;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString(){
       return username
               + "; " + password
               + "; " + birthdayYear
               + "; " + gender;
    }

    public void addToSaved(Media media){
        saved.add(media);
    }

    public void removeFromSaved(Media media){
       saved.remove(media);
    }

    public void addToSeen(Media media){
       seen.add(media);
    }


}
