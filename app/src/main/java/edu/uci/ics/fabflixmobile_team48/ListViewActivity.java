package edu.uci.ics.fabflixmobile_team48;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainPage.EXTRA_MESSAGE);
        // TODO: this should be retrieved from the backend server
        final ArrayList<Movie> movies = new ArrayList<>();
        String[] searchedMovies = message.split("\n");
        for (int i = 0; i < searchedMovies.length; i++) {
            String[] movieInfo = searchedMovies[i].split(";");
            movies.add(new Movie(movieInfo[0], movieInfo[1], movieInfo[2], movieInfo[3], movieInfo[4], movieInfo[5]));
        }

        MovieList adapter = new MovieList(movies, this);

        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Movie movie = movies.get(position);
            String moviemessage = String.format("Clicked on position: %d, name: %s, %s", position, movie.getTitle(), movie.getYear());
            Toast.makeText(getApplicationContext(), moviemessage, Toast.LENGTH_SHORT).show();
        });
    }
}
