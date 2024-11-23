package src;

import java.util.ArrayList;

public class Series extends Media {
    private int endYear;
    private ArrayList<Season> seasons;

    public Series(String mediaName, int releaseYear, ArrayList<String> genre, float IMDBScore, int endYear, ArrayList<Season> seasons) {
        super(mediaName, releaseYear, genre, IMDBScore);
        this.endYear = endYear;
        this.seasons = seasons;
    }

    public Series(String mediaName, int releaseYear, int endYear, ArrayList<String> genre, float IMDBScore) {
        super(mediaName, releaseYear, genre, IMDBScore);
        this.endYear = endYear;
        this.seasons = new ArrayList<>();
    }

    public Series(String mediaName, int releaseYear, int endYear, ArrayList<String> genre, float IMDBScore, int minAge) {
        super(mediaName, releaseYear, genre, IMDBScore, minAge);
        this.endYear = endYear;
        this.seasons = new ArrayList<>();
    }

    public void addSeason(Season season){
        seasons.add(season);
    }

    public void removeSeason(Season season){
        seasons.remove(season);
    }
}
