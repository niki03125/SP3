package src;

import java.time.Year;
import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private int birthdayYear;
    private String gender;
    private ArrayList<Media> seen;
    private ArrayList<Media> saved;
    private ArrayList<Media> specialPlayLists;
    private int id;
    private static int idCount = 1;

   public User (String username, String password, int birthdayYear, String gender){
        this.username = username;
        this.password = password;
        this.birthdayYear = birthdayYear;
        this.gender = gender;
        this.seen = new ArrayList<Media>();
        this.saved = new ArrayList<Media>();
        this.specialPlayLists = new ArrayList<Media>();
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

   public ArrayList<Media> getSpecialPlayLists(){
       return specialPlayLists;
   }

   public int getId() {
        return id;
   }

   //Setter Hvis man vil lave en setting feature, hvor du kan ændre alle dine oplysninger
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
    public void mediaToString(ArrayList<Media> medArr, String listName){
       ArrayList<String> addMediaArray = new ArrayList<>();
       String userFile = "data/UserMovieLists/" + this.username  + listName + ".csv";
        for (Media med : medArr){
            addMediaArray.add(med.getMediaName() + " ; " + med.getIMDBScore());
        }
        FileIO.writeToCVSFileMovie(addMediaArray, userFile);
    }
    public void removeFromSaved(Media media){
       saved.remove(media);
    }

    public void addToSeen(Media media){
       seen.add(media);
    }

    public void addToSpecialPlayLists(Media media){
       specialPlayLists.add(media);
    }

    public void removeFromSpecialPlayLists(Media media){
        specialPlayLists.remove(media);
    }

    public static String gender() {
        String gender = TextUI.promptText("Please enter gender, You have 5 choices:" +
                "\nFemale (F), Male(M), Non-binary(N), Transgender(T), Other(O), Prefer not to say(D)" +
                "\nGender: ").toUpperCase();
        switch (gender) {
            case "F":
                gender = "Female";
                break;
            case "M":
                gender = "Male";
                break;
            case "N":
                gender = "Non-binary";
                break;
            case "T":
                gender = "Transgender";
                break;
            case "O":
                gender = "Other";
                break;
            case "D":
                gender = null;
                break;
            default:
                gender = null;
        }
        return gender;
    }

    public static String password() {
        String password = TextUI.promptText("Please enter password: ");
        if (password.length() < 6 || !password.matches(".*[0-9].*") || !checkUpperCase(password)){
            TextUI.displayMSG("Password must be at least 6 character, contain a number and one capital letter. Please try again");
            password = password();
        }
        return password;
    }

    public static boolean checkUpperCase(String password){
        char character;
        for (int i = 0; i < password.length(); i++){
            character = password.charAt(i);
            if (Character.isUpperCase(character)) {
                return true;
            }
        }
        return false;
    }

    public static String username(ArrayList<User> users) {
        String username = TextUI.promptText("Please enter username: ");
        if (checkForDuplicateUser(username, users)) {
            TextUI.displayMSG("The username is already taken, please chose another one.");
            username = username(users);
        }
        return username;
    }

    public static boolean checkForDuplicateUser(String username, ArrayList<User> users) {
        boolean isDuplicate = false;
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                isDuplicate = true;
            }
        }
        return isDuplicate;
    }

    public static int birthyear() {
        int birthyear = TextUI.promptNumeric("Please enter birth year(YYYY): ");
        if (birthyear < Year.now().getValue() - 125) {
            TextUI.displayMSG("Birth year must be realistic.");
            birthyear();
        } else if (birthyear > Year.now().getValue()) {
            TextUI.displayMSG("Birth year cannot be in the future.");
            birthyear();
        }
        return birthyear;
    }
}
