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
        if (password.length() < 6 || !password.matches(".*[0-9].*") || !checkUpperCase(password)){
            TextUI.displayMSG("Password must be at least 6 character, contain a number and one capital letter. Please try again");
            password = password();
        }
        return password;
    }
    private boolean checkUpperCase(String password){
        char character;
        for (int i = 0; i < password.length(); i++){
            character = password.charAt(i);
            if (Character.isUpperCase(character)) {
                return true;
            }
        }
        return false;
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

    public void searchByTitle(){
        String input = TextUI.promptText("Search: ");
        for (int i = 0; i < medias.size(); i++) {
            if (medias.get(i).getMediaName().equalsIgnoreCase(input)) {
                currentMedia = medias.get((i));
                mediaAction(medias.get(i));
            }
        }
    }

    public void chooseMovie(){
        int choice = TextUI.promptNumeric("Please write the number of the movie you want to choose.");
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

    private void mediaActionMenu(){
        String tmpChoice;
        if (currentUser.getSaved().contains(currentMedia)){
            tmpChoice = TextUI.promptText("You have the following options: Play(P), Remove from list(R), Main menu(M) ");
            if (tmpChoice.equalsIgnoreCase("P")){
                playMedia();
            } else if (tmpChoice.equalsIgnoreCase("R")) {
                currentUser.removeFromSaved(currentMedia);
            } else if (tmpChoice.equalsIgnoreCase("M")) {
                mainMenu();
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
                mainMenu();
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
            TextUI.displayMSG("Movies: ");
            movies();
            chooseMovie();
        } else if (menuChoice.equalsIgnoreCase("S")) {
            TextUI.displayMSG("Series - to be done");
            //Serier();
            //choose serie();
            // choose season();
            // choose episode();
            playMedia();
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

    private void userSettings(){
        TextUI.displayMSG("=====Settings=====");
        String tmpChoice = TextUI.promptText("Change username(U), Change password(C), Delete account(D) ");
        if (tmpChoice.equalsIgnoreCase("U")){
            currentUser.setUsername(username());
        } else if (tmpChoice.equalsIgnoreCase("C")) {
            currentUser.setPassword(password());
        } else if (tmpChoice.equalsIgnoreCase("D")) {
            users.remove(currentUser);
            end();
            on = false;
        }
    }

    public void listMenu(){

        // Create a menu where you can choose a list you want to see
        ArrayList<String> listMenu = new ArrayList<>(Arrays.asList("SavedList(SA)", "SeenList(SE)", "SpecialPlayList(SP)"));
        TextUI.displayMSG("=====LISTMENU=====");
        TextUI.displayMSG(String.valueOf(listMenu));

        // useing the promptText to user for a choise
        String choice = TextUI.promptText("Please enter what list, you want to see: ");

        //if user choose SeenList
        if(choice.equalsIgnoreCase("SE")){
            TextUI.displayMSG("Here is your seenList: ");
            ArrayList<Media> userSeenList = currentUser.getSeen();
            if(userSeenList.isEmpty()){
                TextUI.displayMSG("Your seenList is empty");
               menu();
            }else{
                for(Media media : userSeenList) {
                    TextUI.displayMSG(media.getMediaName());
                }
            }
        }else if(choice.equalsIgnoreCase("SA")){ // if emty, tell and go to media
            TextUI.displayMSG("Here is your savedList: ");
            ArrayList<Media> userSavedList = currentUser.getSaved();
            if(userSavedList.isEmpty()){
                TextUI.displayMSG("Your savedList is empty");
                menu();
            }else{
                for (Media media : userSavedList){
                TextUI.displayMSG((media.getMediaName()));
                }
            }
        }else if(choice.equalsIgnoreCase("SP")){
            TextUI.displayMSG("Here is your specialPlayLists you made: ");
            ArrayList<Media> userSpecialPlayListes = currentUser.getSpecialPlayLists();
            if(userSpecialPlayListes.isEmpty()){
                TextUI.displayMSG("Your specialPlayList is empty");
                menu();
            }else{
                int index = 1;
                for(Media playList: userSpecialPlayListes){
                    TextUI.displayMSG("PlayList "+ index + ": ");
                    for(Media media: userSpecialPlayListes){
                        TextUI.displayMSG(media.getMediaName());
                    }
                    index++;
                }
            }
        }else{
            TextUI.displayMSG("Invalid choice. Please choose a valid list( SA, SE, SP)");
        }
    }


    public void end() {
        ArrayList<String> playersAsText = new ArrayList<>();
        for (User u : users) {
            playersAsText.add(u.toString());
        }
        FileIO.saveData(playersAsText, "data/userdata.csv");
        currentUser.mediaToString(currentUser.getSaved());
        currentUser.mediaToString(currentUser.getSeen());
        currentUser.mediaToString(currentUser.getSpecialPlayLists());

    }
}

