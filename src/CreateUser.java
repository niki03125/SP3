package src;

import java.time.Year;
import java.util.ArrayList;

public class CreateUser {
    private ArrayList<User> users;
    private User currentUser;

    public CreateUser(ArrayList<User> users, User currentUser) {
        this.users = users;
        this.currentUser = currentUser;
    }

    public User userRegister() {

        String username = username();
        String password = password();
        int birthdayYear = birthyear();
        String gender = gender();

        User user = new User(username, password, birthdayYear, gender);
        users.add(user);
        currentUser = user;

        TextUI.displayMSG("You have now been registered");
        return currentUser;
    }

    public String username() {
        String username = TextUI.promptText("Please enter username: ");
        if (checkForDuplicateUser(username)) {
            TextUI.displayMSG("The username is already taken, please chose another one.");
            username = username();
        }
        return username;
    }

    public boolean checkForDuplicateUser(String username) {
        boolean isDuplicate = false;
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                isDuplicate = true;
            }
        }
        return isDuplicate;
    }

    public String password() {
        String password = TextUI.promptText("Please enter password: ");
        if (password.length() < 6 || !password.matches(".*[0-9].*") || !checkUpperCase(password)){
            TextUI.displayMSG("Password must be at least 6 character, contain a number and one capital letter. Please try again");
            password = password();
        }
        return password;
    }

    public boolean checkUpperCase(String password){
        char character;
        for (int i = 0; i < password.length(); i++){
            character = password.charAt(i);
            if (Character.isUpperCase(character)) {
                return true;
            }
        }
        return false;
    }

    public int birthyear() {
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

    public String gender() {
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
}
