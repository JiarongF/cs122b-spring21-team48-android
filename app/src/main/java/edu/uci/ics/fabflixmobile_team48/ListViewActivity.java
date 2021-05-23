package edu.uci.ics.fabflixmobile_team48;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListViewActivity extends Activity {
    public static final String EXTRA_MESSAGE = "edu.uci.ics.fabflixmobile_team48.MESSAGE";
    private final String host = "3.138.107.189";
    private final String port = "8443";
    private final String domain = "cs122b-spring21-team-48";
    private final String baseURL = "https://" + host + ":" + port + "/" + domain;
    private int pageIndex = 0;
    private String singleMovieId;  // for sending to single movie page

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainPage.EXTRA_MESSAGE);

        // If message length is 0 (no movies found), terminate
        if (message.length() == 0) {
            return;
        }

        // TODO: this should be retrieved from the backend server
        final ArrayList<Movie> movies = new ArrayList<>();
        String[] searchedMovies = message.split("\n");
        for (int i = 0; i < searchedMovies.length; i++) {
            String[] movieInfo = searchedMovies[i].split(";&");
            movies.add(new Movie(movieInfo[0], movieInfo[1], movieInfo[2], movieInfo[3], movieInfo[4], movieInfo[5]));
            System.out.println(movies.get(i).toString());
        }

        // show the first 20 movies when created
        final int LIST_SIZE = movies.size();
        int endIndex = Math.min(20, LIST_SIZE);
        MovieList adapter = new MovieList(new ArrayList<Movie>(movies.subList(0, endIndex)), this);

        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

        // shows numerical results
        String displayMessage = "Showing 1-" + endIndex + " of " + LIST_SIZE + " results";
        TextView displayView = findViewById(R.id.displayTextView);
        displayView.setText(displayMessage);

        // on next button clicked
        Button nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pageIndex++;
                System.out.println("Page index is " + pageIndex);
                displayResults(LIST_SIZE, movies);
            }
        });

        // on prev button clicked
        Button prevBtn = findViewById(R.id.prevBtn);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               if (pageIndex > 0) {
                   pageIndex--;
                   System.out.println("Page index is " + pageIndex);
                   displayResults(LIST_SIZE, movies);
               }
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            position = getPosition(position);
            Movie movie = movies.get(position);
            String moviemessage = String.format("Clicked on position: %d, name: %s, %s", position, movie.getTitle(), movie.getYear());
//            Toast.makeText(getApplicationContext(), moviemessage, Toast.LENGTH_SHORT).show();
            sendToSingleMovie(movie);   // sends to single movie
            System.out.println(moviemessage);
        });
    }

    // get new position if pageIndex changed
    public int getPosition(int prevPosition) {
        int newPosition = prevPosition;
        newPosition += pageIndex * 20;
        return newPosition;
    }

    // Will display either the next/prev 20 movies or up to LIST_SIZE movies if less than 20
    public void displayResults(final int LIST_SIZE, ArrayList<Movie> movieList) {
        System.out.println("Displaying movie results");

        int offset = Math.min(pageIndex * 20, LIST_SIZE);   // start

        // If offset is LIST_SIZE, decrement pageIndex, end the function
        if (offset == LIST_SIZE) {
            pageIndex--;
            return;
        }
        int endIndex = offset + 20;
        endIndex = Math.min(endIndex, LIST_SIZE);   // end index is either offset + 20 or LIST_SIZE

        MovieList adapter = new MovieList(new ArrayList<Movie>(movieList.subList(offset, endIndex)), this);

        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
        String displayMessage = "Showing " + (offset + 1) + "-" + endIndex + " of " + LIST_SIZE + " results";
        TextView displayView = findViewById(R.id.displayTextView);
        displayView.setText(displayMessage);

    }

    // sends to SingleMovieActivity the movie that was clicked
    public void sendToSingleMovie(Movie movie) {
        this.singleMovieId = movie.getId();
        String[] temp = movie.getId().split(":");   // get second half
        this.singleMovieId = temp[1];

        // use the same network queue across our application
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        // request type is GET
        final StringRequest searchRequest = new StringRequest(
                Request.Method.GET,
                baseURL + "/api/single-movie?id="+singleMovieId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse (String response){
                        //message.setText(response);
                        try {
                            JSONArray jsonarry = new JSONArray(response);
                            System.out.println("json array is " + jsonarry);
                            String searchResult = "";
                            System.out.println("Movie Id is: " + singleMovieId);
                            for(int i = 0; i < jsonarry.length();i++){
                                JSONObject jsobj = jsonarry.getJSONObject(i);
                                String title = jsobj.getString("title");
                                String year = jsobj.getString("movie_Year");
                                String director = jsobj.getString("director");
                                String genres = jsobj.getString("genres");
                                String stars = jsobj.getString("stars");
                                Movie movie = new Movie(singleMovieId,title,year,director,genres,stars);
                                searchResult += movie.toString();
                            }
                            //System.out.println(searchResult);
                            Intent movieListPage = new Intent(ListViewActivity.this, SingleMovieActivity.class);
                            movieListPage.putExtra(EXTRA_MESSAGE,searchResult);
                            // activate the list page.
                            startActivity(movieListPage);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println((CharSequence) volleyError);
            }
        });
        // maximum retry policy is when request takes 50 seconds
        searchRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // important: queue.add is where the login request is actually sent
        queue.add(searchRequest);
    }

}
