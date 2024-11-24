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
        int birthdayYear = TextUI.promptNumeric("Please enter your birthdayYear(YYYY): ");
        if (birthdayYear < 1900 || birthdayYear > Year.now().getValue()) {
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

    public void userLogin(){
        TextUI.displayMSG("You have chosen to login");
        String username = TextUI.promptText("Please enter your username");
        if(!users.contains(username)){ //the code is cheking if the username is in the file, if not its giving back this messages.
            TextUI.promptText("There are no profile with that username, please try again");
        }
        String password = TextUI.promptText("Please enter your password");
        //when the username is checked and correct, it has to check if the password is correct.
        if(!users.contains(password)){
            TextUI.promptText("The password is not correct");
        }
        // if username and password is both correct, login the user
    }


    public void userLoginOrRegister() {
        TextUI.displayMSG("Welcome to our WBBTServices \n" +
                "Login = l \n" +
                "Register = r");


        if (TextUI.promptBinary("you have chosen to login") == true) {
            // userLogin();
        } else if (TextUI.promptBinary("you have chosen to register, please press r again if you want to continue with register") == false) {
            userRegister();
        }
        // skal laves så den går til en meny med om man vil se hvilke film der er, hvilke film man har set, eller hvilke fik man har gemt

    }

    public void setup(){
//        loadUsers();
//        loadMovies();
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
