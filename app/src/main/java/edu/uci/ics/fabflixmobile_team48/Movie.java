package edu.uci.ics.fabflixmobile_team48;

import java.util.ArrayList;

public class Movie {
        private final String id;

        private final String title;

        private final String year;

        private final String director;

        private final String genres;

        private final String stars;

        public Movie(String id, String title, String year, String director, String genres, String stars) {
            this.id = id;
            this.title = title;
            this.year = year;
            this.director = director;
            this.genres = genres;
            this.stars = stars;

        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getYear() {
            return year;
        }

        public String getDirector() {
            return director;
        }

        public String getGenres() {
            return genres;
        }

        public String getStars() {
            return stars;
        }

    public String toString() {

        return "Id:" + getId() + ";" +
                "Title:" + getTitle() + ";" +
                "Year:" + getYear() + ";" +
                "Director:" + getDirector() + ";" +
                "Genres:" + getGenres()+ ";" +
                "Stars:"+ getStars() + "\n";
    }


}