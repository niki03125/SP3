import java.util.ArrayList;

public class Series extends Media{
    private ArrayList<Season> seasons;

    public Series(String mediaName, int releaseYear, String genre, float IMDBScore) {
        super(mediaName, releaseYear, genre, IMDBScore);
        this.seasons = new ArrayList<>();
    }

    public Series(String mediaName, int releaseYear, String genre, float IMDBScore, int minAge) {
        super(mediaName, releaseYear, genre, IMDBScore, minAge);
        this.seasons = new ArrayList<>();
    }

    public void addSeason(Season season){
        seasons.add(season);
    }

    public void removeSeason(Season season){
        seasons.remove(season);
    }
}
