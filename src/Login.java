package src;

import java.util.ArrayList;

public class Login {
    private ArrayList<User> users;
    private User currentUser;

    public Login(ArrayList<User> users) {
        this.users = users;
    }

    public User userLoginOrRegister() {
        logo();
        TextUI.displayMSG("" +
                "Welcome to our WBBT Services \n" +
                "Login = (L) \n" +
                "Register = (R)");

        String choice = TextUI.promptText("Do you want to login to an existing account or register a new account? ");
        if (choice.equalsIgnoreCase("L")) {
            userLogin();
        } else if (choice.equalsIgnoreCase("R")) {
            userRegister();
        } else {
            TextUI.displayMSG("Invalid option. Redirecting to start menu");
            userLoginOrRegister();
        }
        return currentUser;
    }

    public void userLogin() {
        TextUI.displayMSG("You have chosen to login");
        String username = TextUI.promptText("Please enter your username: ");
        String password = TextUI.promptText("Please enter your password: ");

        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) {         //The code is cheking if the username and password is in the ArrayList, if both is correct.
                TextUI.displayMSG(user.getUsername() + " has logged in");
                currentUser = user;
                return;
            }
        }
        TextUI.displayMSG("Login has failed. Username or password is incorrect");
        String flag = TextUI.promptText("Do you want to login(L) or register(R)? ");
        if (flag.equalsIgnoreCase("L")) {
            userLogin();
        } else if (flag.equalsIgnoreCase("R")) {
            userRegister();
        } else {
            TextUI.displayMSG("Invalid option");
            userLoginOrRegister();
        }
    }

    public void userRegister() {
        String username = User.username(users);
        String password = User.password();
        int birthdayYear = User.birthyear();
        String gender = User.gender();

        User user = new User(username, password, birthdayYear, gender);
        users.add(user);
        currentUser = user;
        TextUI.displayMSG("You have now been registered");
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void logo(){
        TextUI.displayMSG("\n" +
                "██     ██ ██████  ██████  ████████  \n" +
                "██     ██ ██   ██ ██   ██    ██     \n" +
                "██  █  ██ ██████  ██████     ██     \n" +
                "██ ███ ██ ██   ██ ██   ██    ██     \n" +
                " ███ ███  ██████  ██████     ██     \n");
    }
}
