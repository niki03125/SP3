package src;

import java.time.Year;
import java.util.ArrayList;

public class StreamingPlatform {
    private String appName;
    private ArrayList<User> users;
    private ArrayList<Media> medias;
    private ArrayList<Movie> movies;
    private ArrayList<Series> series;
    private Media currentMedia;
    private User currentUser;
    private Menu menu;
    private Search search;
    private CreateUser createUser;

    boolean on = true;

    public StreamingPlatform(String appName) {
        this.appName = appName;
        this.users = new ArrayList<User>();
        this.medias = new ArrayList<Media>();
        this.movies = new ArrayList<Movie>();
        this.series = new ArrayList<Series>();
        this.menu = new Menu();
        this.search = new Search();
        this.createUser = new CreateUser(users, currentUser);
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
            createUser.userRegister();
        } else if (flag.equalsIgnoreCase("C")) {
           end();
        }
    }

    public void userLoginOrRegister() {
        TextUI.displayMSG("Welcome to our WBBTServices \n" +
                "Login = (L) \n" +
                "Register = (R)");

        String choice = TextUI.promptText("Do you want to login to an existing account or register a new account? ");
        if (choice.equalsIgnoreCase("L")) {
            userLogin();
        } else if (choice.equalsIgnoreCase("R")) {
            currentUser = createUser.userRegister();

        }
    }

    public void setup() {
        loadUsers();
        loadMovies();
        loadSeries();
        userLoginOrRegister();
        runLoop();
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
            String[] values = s.split(";");
            Movie tmpMovie = new Movie(values[0], Integer.parseInt(values[1].trim()), getGenres(values[2].trim()), Float.parseFloat(values[3].replace(",", ".").trim()));
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
            series.add(new Series(seriesName, runYears.get(0), runYears.get(1), genres, IMDBScore, seasons)); Series tmpSeries = new Series(seriesName, runYears.get(0), runYears.get(1), genres, IMDBScore, seasons);
            series.add(tmpSeries);
            medias.add(tmpSeries);
        }
    }

    public ArrayList<Season> getSeasons(String value) {
        ArrayList<Season> seasons = new ArrayList<>();
        String[] tmp = value.split(",");
        for (int i = 0; i < tmp.length; i++) {
            String[] s = tmp[i].split("-");
            seasons.add(new Season(i + 1, getEpisodes(Integer.parseInt(s[1]))));
        }
        return seasons;
    }

    public ArrayList<Episode> getEpisodes(int value) {
        ArrayList<Episode> episodes = new ArrayList<>();
        for (int i = 0; i < value; i++) {
            episodes.add(new Episode(i + 1));
        }
        return episodes;
    }

    public ArrayList<String> getGenres(String value) {
        ArrayList<String> res = new ArrayList<>();
        String[] tmp = value.split(",");
        for (String s : tmp) {
            res.add(s);
        }
        return res;
    }

    public ArrayList<Integer> getStartAndEndYear(String value) {
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

    public void chooseMovie(){
        int choice = TextUI.promptNumeric("Please write the number of the movie you want to choose: ");
        // Check if the input is valid:
        if (choice < 1 || choice > movies.size()) {
            TextUI.displayMSG("Invalid choice. Please select a number from the list.");
            chooseMovie();
            return;
        }
        currentMedia = movies.get(choice - 1); //Get the chosen movie and convert user input to 0-based index:
        TextUI.displayMSG("You selected: " + currentMedia.getMediaName() + "\nIMDB Score: " + currentMedia.getIMDBScore());
        mediaActionMenu();
    }

    public void mediaAction(Media media)   {
        TextUI.displayMSG("Title: " + media.getMediaName() +
                "\nIMDBScore:" + media.getIMDBScore());
        mediaActionMenu();
    }

    public void mediaActionMenu(){
        String tmpChoice;
        if (currentUser.getSaved().contains(currentMedia)){
            tmpChoice = TextUI.promptText("You have the following options: Play(P), Remove from list(R), Main menu(M) ");
            if (tmpChoice.equalsIgnoreCase("P")){
                playMedia();
            } else if (tmpChoice.equalsIgnoreCase("R")) {
                currentUser.removeFromSaved(currentMedia);
            } else if (tmpChoice.equalsIgnoreCase("M")) {
                mainMenuOptions();
            } else {
                TextUI.displayMSG("Invalid choice. Please try again");
                mediaActionMenu();
            }
        } else {
            tmpChoice = TextUI.promptText("You have the following options: Play(P), Add to list(A), Main menu(M) ");
            if (tmpChoice.equalsIgnoreCase("P")){
                playMedia();
            } else if (tmpChoice.equalsIgnoreCase("A")) {
                currentUser.addToSaved(currentMedia);
                //currentUser.addToSavedTMP(currentMedia);
            } else if (tmpChoice.equalsIgnoreCase("M")) {
                mainMenuOptions();
            } else {
                TextUI.displayMSG("Invalid choice. Please try again");
                mediaActionMenu();
            }
        }
    }

    public void playMedia()   {
        TextUI.displayMSG("Now watching: " + currentMedia.getMediaName());
        currentUser.addToSeen(currentMedia);
    }

    public void movies(){
        for (int i = 0; i < movies.size(); i++){
            TextUI.displayMSG(i+1 + " " + movies.get(i).getMediaName());
        }
    }

    public void runLoop(){
        while (on){
            mainMenuOptions();
        }
    }

    public void mainMenuOptions(){
        String menuChoice = menu.mainMenu();
        if (menuChoice.equalsIgnoreCase("M")){
            TextUI.displayMSG("Movies: ");
            movies();
            chooseMovie();
        } else if (menuChoice.equalsIgnoreCase("S")) {
            TextUI.displayMSG("Series - to be done");
        } else if (menuChoice.equalsIgnoreCase("LI")) {
            TextUI.displayMSG("Lists");
            menu.listMenu(currentUser);
        } else if (menuChoice.equalsIgnoreCase("F")) {
            currentMedia = search.searchByTitle(medias);
            mediaAction(currentMedia);
        } else if (menuChoice.equalsIgnoreCase("SET")) {
            userSettingsMenu();
        } else if (menuChoice.equalsIgnoreCase("LO")) {
            TextUI.displayMSG("Thank you for watching today.");
            end();
            on = false;
        }
    }

    public void userSettingsMenu(){
        TextUI.displayMSG("=====Settings=====");
        String tmpChoice = TextUI.promptText("Change username(U), Change password(C), Delete account(D), Main menu(M)\n" +
                "Enter choice: ");
        if (tmpChoice.equalsIgnoreCase("U")){
            currentUser.setUsername(createUser.username());
        } else if (tmpChoice.equalsIgnoreCase("C")) {
            currentUser.setPassword(createUser.password());
        } else if (tmpChoice.equalsIgnoreCase("D")) {
            users.remove(currentUser);
            end();
            on = false;
        } else if ((tmpChoice.equalsIgnoreCase("M"))) {
            menu.mainMenu();
        }
    }

    public void end() {
        playerToText();
        saveUserLists();
    }

    public void saveUserLists(){
        currentUser.mediaToString(currentUser.getSaved(), "Saved");
        currentUser.mediaToString(currentUser.getSeen(), "Seen");
        currentUser.mediaToString(currentUser.getSpecialPlayLists(), "Special");
    }

    public void playerToText(){
        ArrayList<String> playersAsText = new ArrayList<>();
        for (User u : users) {
            playersAsText.add(u.toString());
        }
        FileIO.saveData(playersAsText, "data/userdata.csv");
    }
}
