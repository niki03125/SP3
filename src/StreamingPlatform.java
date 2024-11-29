package src;

import java.util.ArrayList;

public class StreamingPlatform {
    private String appName;
    private ArrayList<User> users;
    private ArrayList<Media> medias;
    private ArrayList<Movie> movies;
    private ArrayList<Series> series;
    private User currentUser;
    private Menu menu;
    private Load load;
    private Login login;
    private Save save;

    boolean on = true;

    public StreamingPlatform(String appName) {
        this.appName = appName;
        this.users = new ArrayList<User>();
        this.medias = new ArrayList<Media>();
        this.movies = new ArrayList<Movie>();
        this.series = new ArrayList<Series>();
        this.menu = new Menu(users, movies, medias);
        this.load = new Load(users, movies, medias, series);
        this.login = new Login(users);
        this.save = new Save(users);
    }

    public void setup() {
        load.loadUsers();
        load.loadMovies();
        load.loadSeries();
        currentUser = login.userLoginOrRegister();
        runLoop();
    }

    public void runLoop(){
        while (on){
            menu.mainMenu(currentUser);
            if (menu.isTurnOff()){
                end();
            }
        }
    }

    public void end() {
        save.usersToText();
        save.saveUserLists(currentUser);
        on = false;
    }
}
