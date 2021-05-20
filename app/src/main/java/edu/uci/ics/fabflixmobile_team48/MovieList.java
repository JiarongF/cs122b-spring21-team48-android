package edu.uci.ics.fabflixmobile_team48;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class MovieList extends ArrayAdapter<Movie> {

    private final ArrayList<Movie> movies;

    public MovieList(ArrayList<Movie> movies, Context context) {
        super(context, R.layout.row, movies);
        this.movies = movies;
    }

    /*
      In Android, localhost is the address of the device or the emulator.
      To connect to your machine, you need to use the below IP address
     */
//    private final String host = "10.0.2.2";
//    private final String port = "8443";
//    private final String domain = "cs122b-spring21-team-48-war";
//    private final String baseURL = "https://" + host + ":" + port + "/" + domain;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.row, parent, false);

        Movie movie = movies.get(position);

        TextView titleView = view.findViewById(R.id.title);
        TextView yearView = view.findViewById(R.id.year);
        TextView directorView = view.findViewById(R.id.director);
        TextView genresView = view.findViewById(R.id.genres);
        TextView starsView = view.findViewById(R.id.stars);

        titleView.setText(movie.getTitle());
        // need to cast the year to a string to set the label
        yearView.setText(movie.getYear());
        directorView.setText(movie.getDirector());
        genresView.setText(movie.getGenres());
        starsView.setText(movie.getStars());

        return view;
    }
}
