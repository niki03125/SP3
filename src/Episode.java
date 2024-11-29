package src;

public class Episode extends Media{
    private int episodeID;

    public Episode(int episodeID) {
        this.episodeID = episodeID;
    }

    public int getEpisodeID() {
        return episodeID;
    }

    public void setEpisodeID(int episodeID) {
        this.episodeID = episodeID;
    }

    @Override
    public String
    toString() {
        return "Episode{" +
                "episodeID=" + episodeID +
                '}';
    }
}
