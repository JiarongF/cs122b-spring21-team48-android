package edu.uci.ics.fabflixmobile_team48;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SingleMovieActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // upon creation, inflate and initialize the layout
        setContentView(R.layout.singlemovie);

        Intent intent = getIntent();
        String message = intent.getStringExtra(ListViewActivity.EXTRA_MESSAGE);

        // get the single movie information
        final ArrayList<Movie> movies = new ArrayList<>();
        String[] searchedMovies = message.split("\n");
        for (int i = 0; i < searchedMovies.length; i++) {
            String[] movieInfo = searchedMovies[i].split(";&");
            movies.add(new Movie(movieInfo[0], movieInfo[1], movieInfo[2], movieInfo[3], movieInfo[4], movieInfo[5]));
            System.out.println("Printing from single movie activity: " + movies.get(i).toString());
        }

        MovieList adapter = new MovieList(movies, this);
        ListView listView = findViewById(R.id.singleMovieList);
        listView.setAdapter(adapter);
    }
}
