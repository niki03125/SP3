package src;

import java.util.ArrayList;
import java.util.Arrays;

import static src.User.password;
import static src.User.username;

public class Menu {
    private ArrayList<User> users;
    private Login login;

    public Menu(ArrayList<User> users) {
        this.users = users;
        this.login = new Login(users);
    }

    public String mainMenuOptions(){
        ArrayList<String> menu = new ArrayList<>(Arrays.asList("Movies(M)", "Series(S)", "Lists(LI)", "Search(F)", "Settings(SET)", "Logout(LO)"));
        TextUI.displayMSG("=====MENU=====");
        TextUI.displayMSG(String.valueOf(menu));
        return TextUI.promptText("Please enter what you want to do: ");
    }

    public void listMenu(User currentUser){

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
            TextUI.displayMSG("Invalid choice. Please choose a valid list( SA, SE, SP)");
            listMenu(currentUser);
        }
    }

    public void userSettingsMenu(User currentUser){
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
}
