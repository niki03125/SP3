package src;

import java.io.IOException;
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
    private Load load;
    private Login login;

    boolean on = true;

    public StreamingPlatform(String appName) {
        this.appName = appName;
        this.users = new ArrayList<User>();
        this.medias = new ArrayList<Media>();
        this.movies = new ArrayList<Movie>();
        this.series = new ArrayList<Series>();
        this.menu = new Menu(users);
        this.search = new Search();
        this.load = new Load(users, movies, medias, series);
        this.login = new Login(users);
    }

    public void setup() {
        load.loadUsers();
        load.loadMovies();
        load.loadSeries();
        currentUser = login.userLoginOrRegister();
        runLoop();
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
            tmpChoice = TextUI.promptText("You have the following options: Play(P), Remove from savedList(R), Main menu(M), Remove from specialPlayList(S)");
            if (tmpChoice.equalsIgnoreCase("P")){
                playMedia();
            } else if (tmpChoice.equalsIgnoreCase("R")) {
                currentUser.removeFromSaved(currentMedia);
                TextUI.displayMSG("You have now removed: " + currentMedia.getMediaName() +" from your savedList");
            } else if (tmpChoice.equalsIgnoreCase("M")) {
                mainMenu();
            } else if (tmpChoice.equalsIgnoreCase("S")) {
                currentUser.removeFromSpecialPlayLists(currentMedia);
                TextUI.displayMSG("You have now removed: " + currentMedia.getMediaName() +" from your specialPlayList");
            } else {
                TextUI.displayMSG("Invalid choice. Please try again");
                mediaActionMenu();
            }
        } else {
            tmpChoice = TextUI.promptText("You have the following options: Play(P), Add to savedList(A), Main menu(M), Add to specialPlayList(S)\n" +
                    "Please enter your choice: ");
            if (tmpChoice.equalsIgnoreCase("P")){
                playMedia();
            } else if (tmpChoice.equalsIgnoreCase("A")) {
                currentUser.addToSaved(currentMedia);
                TextUI.displayMSG("You have now added: " + currentMedia.getMediaName() +" from your savedList");
            } else if (tmpChoice.equalsIgnoreCase("M")) {
                mainMenu();
            } else if (tmpChoice.equalsIgnoreCase("S")) {
                currentUser.addToSpecialPlayLists(currentMedia);
                TextUI.displayMSG("You have now added: " + currentMedia.getMediaName() +" to your specialPlayList");
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
            mainMenu();
        }
    }

    public void mainMenu(){
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
            menu.userSettingsMenu(currentUser);
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
            currentUser.setUsername(username());
        } else if (tmpChoice.equalsIgnoreCase("C")) {
            currentUser.setPassword(password());
        } else if (tmpChoice.equalsIgnoreCase("D")) {
            currentUser.deleteUserAccount(users, currentUser);

            on = false;
            end();
        } else if ((tmpChoice.equalsIgnoreCase("M"))) {
            menu.mainMenu();
        }
    }

    public void end() {
        usersToText();
        saveUserLists();
        on = false;
    }

    public void saveUserLists(){
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
