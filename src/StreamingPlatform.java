package src;

import java.time.Year;
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
        int birthdayYear = birthyear();
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

        User user = new User(username, password, birthdayYear, gender);
        users.add(user);// addder ikke til userdata informationen
        TextUI.displayMSG("You have now been registered");
    }

    private int birthyear(){
        int birthyear = TextUI.promptNumeric("Please enter your birthdayYear(YYYY): ");
        if (birthyear < Year.now().getValue()-125) {
            TextUI.displayMSG("Birth year must be realistic.");
            birthyear();
        } else if (birthyear > Year.now().getValue()) {
            TextUI.displayMSG("Birth year cannot be in the future.");
            birthyear();
        }
        return birthyear;
    }

    public void userLogin() {
        TextUI.displayMSG("You have chosen to login");
        String username = TextUI.promptText("Please enter your username: ");
        String password = TextUI.promptText("Please enter your password: ");

        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) { //the code is cheking if the username and passeword is in the file, if both is correct.
                System.out.println(user.getUsername() + " is logged in");
                return;
            }
        }
        System.out.println("Login has failed. Username or password is incorrect");
        TextUI.promptBinary("Do you want to register? ");
    }


    public void userLoginOrRegister() {
        TextUI.displayMSG("Welcome to our WBBTServices \n" +
                "Login = l \n" +
                "Register = r");

        boolean choice = TextUI.promptBinary("Do you want to login to an existing account or register a new account? ");
        if (choice) {
            userLogin();
        } else if (!choice) {
            userRegister();
        }
        // skal laves så den går til en meny med om man vil se hvilke film der er, hvilke film man har set, eller hvilke fik man har gemt

    }

    public void setup(){
        loadUsers();
        loadMovies();
        loadSeries();
    }

    public void loadUsers(){
        ArrayList<String> data = FileIO.readData("data/userdata.csv");
        for (String s : data){
            String[] values = s.split(";");
            users.add(new User(values[0].trim(), values[1].trim(), Integer.parseInt(values[2].trim()), values[3].trim()));
        }
    }

    public void loadMovies() {
        ArrayList<String> data = FileIO.readData("data/movie.txt");
        for (String s : data) {
            String[] values = s.replace(" ", "").split(";");
            medias.add(new Movie(values[0], Integer.parseInt(values[1]), getGenres(values[2]), Float.parseFloat(values[3].replace(",", "."))));
        }
    }

    public void loadSeries(){
        ArrayList<String> data = FileIO.readData("data/series.txt");
        for  (String s : data){
            String[] values = s.replace(" ", "").split(";");
            String seriesName = values[0];
            ArrayList<Integer> runYears = getStartAndEndYear(values[1]);
            ArrayList<String> genres = getGenres(values[2]);
            float IMDBScore = Float.parseFloat(values[3].replace(",", "."));
            ArrayList<Season> seasons = getSeasons(values[4]);
            medias.add(new Series(seriesName, runYears.get(0), runYears.get(1), genres, IMDBScore, seasons));
        }
    }

    private ArrayList<Season> getSeasons(String value){
        ArrayList<Season> seasons = new ArrayList<>();
        String[] tmp = value.split(",");
        for (int i = 0; i < tmp.length; i++){
            String[] s = tmp[i].split("-");
            seasons.add(new Season(i+1, getEpisodes(Integer.parseInt(s[1]))));
        }
        return seasons;
    }

    private ArrayList<Episode> getEpisodes(int value){
        ArrayList<Episode> episodes = new ArrayList<>();
        for (int i = 0; i < value; i++){
            episodes.add(new Episode(i+1));
        }
        return episodes;
    }

    private ArrayList<String> getGenres(String value){
        ArrayList<String> res = new ArrayList<>();
        String[] tmp = value.split(",");
        for (String s : tmp){
            res.add(s);
        }
        return res;
    }

    private ArrayList<Integer> getStartAndEndYear(String value){
        ArrayList<Integer> res = new ArrayList<>();
        if (value.contains("-")){
            String[] tmp = value.split("-");
            if (tmp.equals(1)){
                res.add(Integer.parseInt(tmp[0]));
                res.add(Integer.parseInt(tmp[1]));
            } else {
                res.add(Integer.parseInt(tmp[0]));
                res.add(Year.now().getValue());
            }
        } else {            //Start and end year is the same
            res.add(Integer.parseInt(value));
            res.add(Integer.parseInt(value));
        }
        return res;
    }

       // public void end(){
         //   ArrayList<String> playersAsText = new ArrayList<>();
          //  for (User u: users) {
             //   playersAsText.add(u.toString());
          //  }
        //}

    }
