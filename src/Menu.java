package src;

import java.util.ArrayList;
import java.util.Arrays;

import static src.User.password;
import static src.User.username;

public class Menu {
    private ArrayList<User> users;
    private Login login;
    private ArrayList<Movie> movies;
    private ArrayList<Series> series;
    private ArrayList<Media> medias;
    private User currentUser;
    private Media currentMedia;
    private Search search;
    private boolean turnOff;

    public Menu(ArrayList<User> users, ArrayList<Movie> movies, ArrayList<Series> series, ArrayList<Media> medias) {
        this.users = users;
        this.login = new Login(users);
        this.movies = movies;
        this.series = series;
        this.medias = medias;
        this.search = new Search();
    }

    public String mainMenuOptions(){
        ArrayList<String> menu = new ArrayList<>(Arrays.asList("Movies(M)", "Series(S)", "Lists(LI)", "Search(F)", "Settings(SET)", "Logout(LO)"));
        TextUI.displayMSG("=====MENU=====");
        TextUI.displayMSG(String.valueOf(menu));
        return TextUI.promptText("Please enter what you want to do: ");
    }

    public void listMenu(){

        // Create a menu where you can choose a list you want to see
        ArrayList<String> listMenu = new ArrayList<>(Arrays.asList("SavedList(SA)", "SeenList(SE)", "SpecialPlayList(SP) "));
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
            TextUI.displayMSG("Invalid choice. Please choose a valid list( SA, SE, SP) ");
            listMenu();
        }
    }

    public void userSettingsMenu(){
        TextUI.displayMSG("=====Settings=====");
        String tmpChoice = TextUI.promptText("Change username(U), Change password(C), Delete account(D), Main menu(M)\n" +
                "Enter choice: ");
        if (tmpChoice.equalsIgnoreCase("U")){
            currentUser.setUsername(username(users));
        } else if (tmpChoice.equalsIgnoreCase("C")) {
            currentUser.setPassword(password());
        } else if (tmpChoice.equalsIgnoreCase("D")) {
            users.remove(currentUser);
            login.userLoginOrRegister();
        } else if ((tmpChoice.equalsIgnoreCase("M"))) {
            mainMenuOptions();
        }
    }


    public void chooseMovie(){
        int choice = TextUI.promptNumeric("Please write the number of the movie you want to choose: ");
        // Check if the input is valid:
        if (choice < 1 || choice > movies.size()) {
            movies();
            TextUI.displayMSG("\nInvalid choice. Please select a number from the list.");
            chooseMovie();
            return;
        }
        currentMedia = movies.get(choice - 1); //Get the chosen movie and convert user input to 0-based index:
        TextUI.displayMSG("You selected: " + currentMedia.getMediaName() + "\nIMDB Score: " + currentMedia.getIMDBScore());
        mediaActionMenu();
    }

    public void chooseSeries(){
        int choice = TextUI.promptNumeric("Please write the number of the series you want to choose: ");
        try{
            currentMedia = series.get(choice - 1);
            TextUI.displayMSG("You selected: " + currentMedia.getMediaName() + "\nIMDB Score: " + currentMedia.getIMDBScore());
            mediaActionMenu();

        }catch (IndexOutOfBoundsException e){
            series();
            TextUI.displayMSG("\nInvalid choice. Please select a number from the list.");
            chooseSeries();
        }
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
                mainMenu(currentUser);
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
                mainMenu(currentUser);
            } else if (tmpChoice.equalsIgnoreCase("S")) {
                currentUser.addToSpecialPlayLists(currentMedia);
                TextUI.displayMSG("You have now added: " + currentMedia.getMediaName() +" to your specialPlayList");
            } else {
                TextUI.displayMSG("Invalid choice. Please try again");
                mediaActionMenu();
            }
        }
    }

    public void mainMenu(User currentUser){
        String menuChoice = mainMenuOptions();
        this.currentUser = currentUser;
        if (menuChoice.equalsIgnoreCase("M")){
            TextUI.displayMSG("Movies: ");
            movies();
            chooseMovie();
        } else if (menuChoice.equalsIgnoreCase("S")) {
            TextUI.displayMSG("Series: ");
            series();
            chooseSeries();
        } else if (menuChoice.equalsIgnoreCase("LI")) {
            listMenu();
        } else if (menuChoice.equalsIgnoreCase("F")) {
            currentMedia = search.searchByTitle(medias);
            mediaAction(currentMedia);
        } else if (menuChoice.equalsIgnoreCase("SET")) {
            userSettingsMenu();
        } else if (menuChoice.equalsIgnoreCase("LO")) {
            TextUI.displayMSG("Thank you for watching today.");
            turnOff = true;
        }
    }

    public void movies(){
        for (int i = 0; i < movies.size(); i++){
            TextUI.displayMSG(i+1 + " " + movies.get(i).getMediaName());
        }
    }

    public void series(){
        for (int i = 0; i < series.size(); i++){
            TextUI.displayMSG(i+1 + " " + series.get(i).getMediaName());
        }
    }

    public void playMedia()   {
        TextUI.displayMSG("Now watching: " + currentMedia.getMediaName());
        currentUser.addToSeen(currentMedia);
    }

    public boolean isTurnOff() {
        return turnOff;
    }
}
