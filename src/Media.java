package src;

import java.util.ArrayList;

public abstract class Media {
private String mediaName;
private int releaseYear;
private ArrayList<String> genre;
private float IMDBScore;
private int minAge;
private int duration;

    public Media() {
    }

    public Media(String mediaName) {
        this.mediaName = mediaName;
    }

    public Media(String mediaName, int releaseYear, ArrayList<String> genre, float IMDBScore) {
        this.mediaName = mediaName;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.IMDBScore = IMDBScore;
    }

    public Media(String mediaName, int releaseYear, ArrayList<String> genre, float IMDBScore, int minAge) {
        this.mediaName = mediaName;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.IMDBScore = IMDBScore;
        this.minAge = minAge;
    }

    public Media(String mediaName, int releaseYear, ArrayList<String> genre, float IMDBScore, int minAge, int duration) {
        this.mediaName = mediaName;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.IMDBScore = IMDBScore;
        this.minAge = minAge;
        this.duration = duration;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public ArrayList<String> getGenre() {
        return genre;
    }

    public float getIMDBScore() {
        return IMDBScore;
    }

    public void setIMDBScore(float IMDBScore) {
        this.IMDBScore = IMDBScore;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    @Override
    public String toString() {
        return "Media{" +
                "mediaName='" + mediaName + '\'' +
                ", genre=" + genre +
                ", IMDBScore=" + IMDBScore +
                '}';
    }
}
