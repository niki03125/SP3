package src;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;

public class StreamingPlatform {
    private String appName;
    private ArrayList<User> users;
    private ArrayList<Media> medias;
    private ArrayList<Movie> movies;
    private ArrayList<Series> series;
    private Media currentMedia;
    private User currentUser;

    boolean on = true;

    public StreamingPlatform(String appName) {
        this.appName = appName;
        this.users = new ArrayList<User>();
        this.medias = new ArrayList<Media>();
        this.movies = new ArrayList<Movie>();
        this.series = new ArrayList<Series>();
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

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public void addMedia(Media media) {
        medias.add(media);
    }

    public void removeMedia(Media media) {
        medias.remove(media);
    }

    public void userRegister() {
        String username = username();
        String password = password();
        int birthdayYear = birthyear();
        String gender = gender();

        User user = new User(username, password, birthdayYear, gender);
        users.add(user);
        TextUI.displayMSG("You have now been registered");
        end();
    }

    private String gender() {
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

    private String password() {
        String password = TextUI.promptText("Please enter password: ");
        //Later logic to make "Strong" password can be added.
        return password;
    }

    private String username() {
        String username = TextUI.promptText("Please enter username: ");
        if (checkForDuplicateUser(username)) {
            TextUI.displayMSG("The username is already taken, please chose another one.");
            username();
        }
        return username;
    }

    private boolean checkForDuplicateUser(String username) {
        boolean isDuplicate = false;
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                isDuplicate = true;
            }
        }
        return isDuplicate;
    }

    private int birthyear() {
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

    public void userLogin() {
        TextUI.displayMSG("You have chosen to login");
        String username = TextUI.promptText("Please enter your username: ");
        String password = TextUI.promptText("Please enter your password: ");

        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) { //the code is cheking if the username and passeword is in the file, if both is correct.
                TextUI.displayMSG(user.getUsername() + " has logged in");
                currentUser = user;
                return;
            }
        }
        TextUI.displayMSG("Login has failed. Username or password is incorrect");
        String flag = TextUI.promptText("Do you want to login(L), register(R) or cancel(C)? ");
        if (flag.equalsIgnoreCase("L")) {
            userLogin();
        } else if (flag.equalsIgnoreCase("R")) {
            userRegister();
        } else if (flag.equalsIgnoreCase("C")) {
           end();
        }
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

    }

    public void setup() {
        loadUsers();
        loadMovies();
        loadSeries();
    }

    public void loadUsers() {
        ArrayList<String> data = FileIO.readData("data/userdata.csv");
        for (String s : data) {
            String[] values = s.split(";");
            users.add(new User(values[0].trim(), values[1].trim(), Integer.parseInt(values[2].trim()), values[3].trim()));
        }
    }

    public void loadMovies() {
        ArrayList<String> data = FileIO.readData("data/movie.txt");
        for (String s : data) {
            String[] values = s.replace(" ", "").split(";");
            Movie tmpMovie = new Movie(values[0], Integer.parseInt(values[1]), getGenres(values[2]), Float.parseFloat(values[3].replace(",", ".")));
            movies.add(tmpMovie);
            medias.add(tmpMovie);
        }
    }

    public void loadSeries() {
        ArrayList<String> data = FileIO.readData("data/series.txt");
        for (String s : data) {
            String[] values = s.replace(" ", "").split(";");
            String seriesName = values[0];
            ArrayList<Integer> runYears = getStartAndEndYear(values[1]);
            ArrayList<String> genres = getGenres(values[2]);
            float IMDBScore = Float.parseFloat(values[3].replace(",", "."));
            ArrayList<Season> seasons = getSeasons(values[4]);
            series.add(new Series(seriesName, runYears.get(0), runYears.get(1), genres, IMDBScore, seasons));
        }
    }


    private ArrayList<Season> getSeasons(String value) {
        ArrayList<Season> seasons = new ArrayList<>();
        String[] tmp = value.split(",");
        for (int i = 0; i < tmp.length; i++) {
            String[] s = tmp[i].split("-");
            seasons.add(new Season(i + 1, getEpisodes(Integer.parseInt(s[1]))));
        }
        return seasons;
    }

    private ArrayList<Episode> getEpisodes(int value) {
        ArrayList<Episode> episodes = new ArrayList<>();
        for (int i = 0; i < value; i++) {
            episodes.add(new Episode(i + 1));
        }
        return episodes;
    }

    private ArrayList<String> getGenres(String value) {
        ArrayList<String> res = new ArrayList<>();
        String[] tmp = value.split(",");
        for (String s : tmp) {
            res.add(s);
        }
        return res;
    }

    private ArrayList<Integer> getStartAndEndYear(String value) {
        ArrayList<Integer> res = new ArrayList<>();
        if (value.contains("-")) {
            String[] tmp = value.split("-");
            if (tmp.equals(1)) {
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

    public String menu(){
        ArrayList<String> menu = new ArrayList<>(Arrays.asList("Movies(M)", "Series(S)", "Lists(LI)", "Search(F)", "Logout(LO)"));
        TextUI.displayMSG("=====MENU=====");
        TextUI.displayMSG(String.valueOf(menu));
        return TextUI.promptText("Please enter what you want to do: ");
    }

    public ArrayList<Media> searchByTitle(){
        String input = TextUI.promptText("Search: ");
        //had problems with this for each loop
        /*for (Media mediaArray : medias) {
            if (mediaArray.getMediaName().equalsIgnoreCase(input)) {
                mediaAction(mediaArray);
            }
        }*/

        ArrayList<Media> searchResults = new ArrayList<>();


        for(int i = 0; i < medias.size(); i++){
            if (medias.get(i).getMediaName().equalsIgnoreCase(input)){
                mediaAction(medias.get(i));
                searchResults.add(medias.get(i));
            }
        }
        return searchResults;
    }

    public void mediaAction(Media media)   {
        TextUI.displayMSG("Title: " + media.getMediaName() +
                "\nIMDBScore:" + media.getIMDBScore());
        playMedia();


    }

    public void playMedia()   {
        TextUI.displayMSG("Now watching: " + currentMedia.getMediaName());
        currentUser.addToSeen(currentMedia);
    }


    private void movies(){
        for (int i = 0; i < movies.size(); i++){
            TextUI.displayMSG(i+1 + " " + movies.get(i).getMediaName());
        }
    }

    public void runLoop(){
        while (on){
            mainMenu();
        }
    }

    public void mainMenu(){
        String menuChoice = menu();
        if (menuChoice.equalsIgnoreCase("M")){
            TextUI.displayMSG("Movies - to be done");
            movies();
        } else if (menuChoice.equalsIgnoreCase("S")) {
            TextUI.displayMSG("Series - to be done");
        }else if(menuChoice.equalsIgnoreCase("LI")){
            TextUI.displayMSG("Lists");
            listMenu();
        } else if (menuChoice.equalsIgnoreCase("F")) {
            TextUI.displayMSG("Search - to be done. Looking for method called search();");
            searchByTitle();
        } else if (menuChoice.equalsIgnoreCase("LO")) {
            TextUI.displayMSG("Thank you for watching today.");
            end();
            on = false;
        }
    }

    public void listMenu(){
        ArrayList<String> listMenu = new ArrayList<>(Arrays.asList("SavedList(SA)", "SeenList(SE)"));
        TextUI.displayMSG("=====LISTMENU=====");
        TextUI.displayMSG(String.valueOf(listMenu));
        String choice = TextUI.promptText("Please enter what list, you want to see: ");
        if(choice.equalsIgnoreCase("SE")){
            TextUI.displayMSG("Here is your seenList: ");
            ArrayList<Media> userSeenList = currentUser.getSeen();
            for(Media media : userSeenList){
            TextUI.displayMSG(media.toString());
            }
        }else if(choice.equalsIgnoreCase("SA")){
                TextUI.displayMSG("Here is your savedList: ");
                ArrayList<Media> userSavedList = currentUser.getSaved();
                for (Media media : userSavedList){
                    TextUI.displayMSG((media.toString()));
                }
        }
    }




    public void end() {
        ArrayList<String> playersAsText = new ArrayList<>();
        for (User u : users) {
            playersAsText.add(u.toString());
        }
        FileIO.saveData(playersAsText, "data/userdata.csv");
    }
}

