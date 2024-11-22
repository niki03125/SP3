import java.util.ArrayList;

public class Season{
    private int seasonID;
    private ArrayList<Episode> episodes;

    public Season(int seasonID) {
        this.seasonID = seasonID;
        this.episodes = episodes;
    }

    public int getSeasonID() {
        return seasonID;
    }

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    public void addEpisode(Episode episode){
        episodes.add(episode);
    }

    public void removeEpisode(Episode episode){
        episodes.remove(episode);
    }
}