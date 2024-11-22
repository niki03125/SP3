package src;

public abstract class Media {
private String mediaName;
private int releaseYear;
private String genre;
private float IMDBScore;
private int minAge;

    public Media(String mediaName, int releaseYear, String genre, float IMDBScore) {
        this.mediaName = mediaName;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.IMDBScore = IMDBScore;
    }

    public Media(String mediaName, int releaseYear, String genre, float IMDBScore, int minAge) {
        this.mediaName = mediaName;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.IMDBScore = IMDBScore;
        this.minAge = minAge;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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

    public void playMedia(String mediaName){
        TextUI.displayMSG("Playing: " + this.mediaName);
        this.user.addToSeen().add(mediaName);
    }
}
