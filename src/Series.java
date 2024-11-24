package src;

import java.util.ArrayList;

public class Series extends Media {
    private int endYear;
    private ArrayList<Season> seasons;

    public Series(String mediaName, int releaseYear, int endYear, ArrayList<String> genre, float IMDBScore, ArrayList<Season> seasons) {
        super(mediaName, releaseYear, genre, IMDBScore);
        this.endYear = endYear;
        this.seasons = seasons;
    }

    public void addSeason(Season season){
        seasons.add(season);
    }

    public void removeSeason(Season season){
        seasons.remove(season);
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
                "Season{" +
                "seasons=" + seasons.size() +
                '}';
    }
}
