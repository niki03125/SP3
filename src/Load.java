package src;

import java.time.Year;
import java.util.ArrayList;

public class Load {
    private ArrayList<User> users;
    private ArrayList<Movie> movies;
    private ArrayList<Media> medias;
    private ArrayList<Series> series;

    public Load(ArrayList<User> users, ArrayList<Movie> movies, ArrayList<Media> medias, ArrayList<Series> series) {
        this.users = users;
        this.movies = movies;
        this.medias = medias;
        this.series = series;
    }

    public void loadUsers() {
        ArrayList<String> data = FileIO.readData("data/userdata.csv");
        for (String s : data) {
            String[] values = s.split(";");
            users.add(new User(values[0].trim(), values[1].trim(), Integer.parseInt(values[2].trim()), values[3].trim()));
        }
    }

    public void loadMovies() {
        ArrayList<String> data = FileIO.readData("data/movie.txt");
        for (String s : data) {
            String[] values = s.split(";");
            Movie tmpMovie = new Movie(values[0], Integer.parseInt(values[1].trim()), getGenres(values[2].trim()), Float.parseFloat(values[3].replace(",", ".").trim()));
            movies.add(tmpMovie);
            medias.add(tmpMovie);
        }
    }

    public void loadSeries() {
        ArrayList<String> data = FileIO.readData("data/series.txt");
        for (String s : data) {
            String[] values = s.replace(" ", "").split(";");
            String seriesName = values[0];
            ArrayList<Integer> runYears = getStartAndEndYear(values[1]);
            ArrayList<String> genres = getGenres(values[2]);
            float IMDBScore = Float.parseFloat(values[3].replace(",", "."));
            ArrayList<Season> seasons = getSeasons(values[4]);
            Series tmpSeries = new Series(seriesName, runYears.get(0), runYears.get(1), genres, IMDBScore, seasons);

            //this line adds series in double, keeping this line if its necessary for the season and episode class
            //series.add(new Series(seriesName, runYears.get(0), runYears.get(1), genres, IMDBScore, seasons)); Series tmpSeries = new Series(seriesName, runYears.get(0), runYears.get(1), genres, IMDBScore, seasons);

            series.add(tmpSeries);
            medias.add(tmpSeries);
        }
    }

    public ArrayList<String> getGenres(String value) {
        ArrayList<String> res = new ArrayList<>();
        String[] tmp = value.split(",");
        for (String s : tmp) {
            res.add(s);
        }
        return res;
    }

    public ArrayList<Integer> getStartAndEndYear(String value) {
        ArrayList<Integer> res = new ArrayList<>();
        if (value.contains("-")) {
            String[] tmp = value.split("-");
            if (tmp.equals(1)) {
                res.add(Integer.parseInt(tmp[0]));
                res.add(Integer.parseInt(tmp[1]));
            } else {
                res.add(Integer.parseInt(tmp[0]));
                res.add(Year.now().getValue());
            }
        } else {            //Start and end year is the same
            res.add(Integer.parseInt(value));
            res.add(Integer.parseInt(value));
        }
        return res;
    }

    public ArrayList<Season> getSeasons(String value) {
        ArrayList<Season> seasons = new ArrayList<>();
        String[] tmp = value.split(",");
        for (int i = 0; i < tmp.length; i++) {
            String[] s = tmp[i].split("-");
            seasons.add(new Season(i + 1, getEpisodes(Integer.parseInt(s[1]))));
        }
        return seasons;
    }

    public ArrayList<Episode> getEpisodes(int value) {
        ArrayList<Episode> episodes = new ArrayList<>();
        for (int i = 0; i < value; i++) {
            episodes.add(new Episode(i + 1));
        }
        return episodes;
    }
}
