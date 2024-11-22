package src;

public class Movie extends Media {
    private int duration;
    public Movie(String mediaName, int releaseYear, String genre, float IMDBScore) {
        super(mediaName, releaseYear, genre, IMDBScore);
    }

    public Movie(String mediaName, int releaseYear, String genre, float IMDBScore, int duration, int minAge) {
        super(mediaName, releaseYear, genre, IMDBScore, minAge);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
