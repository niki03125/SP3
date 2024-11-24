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

    public void userRegister() {
        String username = TextUI.promptText("Please enter username: ");
        if (users.contains(username)) {
            TextUI.promptText("The username is already taken, please chose another one: ");//Dosent work properly.
        }
        String password = TextUI.promptText("Please enter password: ");
        int birthdayYear = TextUI.promptNumeric("Please enter your birthdayYear(YYYY): ");
        if (birthdayYear < 1900 || birthdayYear > java.time.Year.now().getValue()) {
            throw new IllegalArgumentException("Birthday year must be realistic. ");
        }
        String genderAnswer = TextUI.promptText("Please enter gender: \n " +
                "You have 5 choices " +
                "Woman (W), Male(M), Other(O), Chair (C), Kitten(K)");
        String gender = TextUI.promptText("Woman (W), Male(M), Other(O), Chair (C), Kitten(K)").toLowerCase();

        switch (gender) {
            case "W":
                gender = "Woman";
                break;
            case "M":
                gender = "Male";
                break;
            case "O":
                gender = "Other";
                break;
            case "C":
                gender = "Chair";
                break;
            case "K":
                gender = "Kitten";
                break;
            default:
                gender = "Other ";
        }

        User user = new User(username, password, birthdayYear, gender);
        users.add(user);// addder ikke til userdata informationen
        TextUI.displayMSG("You have now been registeret");
    }

    public void userLogin() {
        TextUI.displayMSG("You have chosen to login");
        String username = TextUI.promptText("Please enter your username");
        String password = TextUI.promptText("Please enter your password");

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) { //the code is cheking if the username and passeword is in the file, if both is correct.
                System.out.println(username + " is logged in");
                return;
            }
        }
        System.out.println("Login has failed. Username og password is incorrect");
        TextUI.promptBinary("Do you want to register?");
    }


    public void userLoginOrRegister() {
        TextUI.displayMSG("Welcome to our WBBTServices \n" +
                "Login = l \n" +
                "Register = r");

        if (TextUI.promptBinary("you have chosen to login") == true) {
            userLogin();
        } else if (TextUI.promptBinary("you have chosen to register, please press r again if you want to continue with register") == false) {
            userRegister();
        }
        // skal laves så den går til en meny med om man vil se hvilke film der er, hvilke film man har set, eller hvilke fik man har gemt

    }

    public void setup(){
        loadUsers();
        loadMovies();
    }

    public void loadUsers(){
        ArrayList<String> data = FileIO.readData("data/userdata.csv");
        for (String s : data){
            String[] values = s.split(";");
            users.add(new User(values[0].trim(), values[1].trim(), Integer.parseInt(values[2].trim()), values[3].trim()));
        }
    }

    public void loadMovies() {
        ArrayList<String> data = FileIO.readData("data/movie.csv");
        for (String s : data) {
            String[] values = s.split(";");
            medias.add(new Movie(values[0].trim(), Integer.parseInt(values[1].trim()), values[2].trim(), Float.parseFloat(values[3].trim())));
        }
    }

       // public void end(){
         //   ArrayList<String> playersAsText = new ArrayList<>();
          //  for (User u: users) {
             //   playersAsText.add(u.toString());
          //  }
        //}

    }
