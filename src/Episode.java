package src;

public class Episode {
    private String title;
    private int episodeID;
    private int duration;

    public Episode(int episodeID) {
        this.episodeID = episodeID;
    }

    public Episode(String title, int episodeID) {
        this.title = title;
        this.episodeID = episodeID;
    }

    public Episode(String title, int episodeID, int duration) {
        this.title = title;
        this.episodeID = episodeID;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEpisodeID() {
        return episodeID;
    }

    public void setEpisodeID(int episodeID) {
        this.episodeID = episodeID;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String
    toString() {
        return "Episode{" +
                "episodeID=" + episodeID +
                '}';
    }
}
