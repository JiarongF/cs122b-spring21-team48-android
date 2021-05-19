package edu.uci.ics.fabflixmobile_team48;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainPage extends AppCompatActivity {

    private EditText title;
    private TextView message;
    private Button searchButton;
    public static final String EXTRA_MESSAGE = "edu.uci.ics.fabflixmobile_team48.MESSAGE";


    /*
      In Android, localhost is the address of the device or the emulator.
      To connect to your machine, you need to use the below IP address
     */
    private final String host = "10.0.2.2";
    private final String port = "8443";
    private final String domain = "cs122b-spring21-team-48-war";
    private final String baseURL = "https://" + host + ":" + port + "/" + domain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // upon creation, inflate and initialize the layout
        setContentView(R.layout.main);
        title = findViewById(R.id.title);
        message = findViewById(R.id.message);
        searchButton = findViewById(R.id.search);

        //assign a listener to call a function to handle the user request when clicking a button
        searchButton.setOnClickListener(view -> search());
    }

    public void search() {

        // use the same network queue across our application
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        // request type is POST
        final StringRequest searchRequest = new StringRequest(
                Request.Method.GET,
                baseURL + "/api/movies?title="+title.getText().toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse (String response){
                        //message.setText(response);
                        try {
                            JSONArray jsonarry = new JSONArray(response);
                            String searchResult = "";
                            for(int i = 0; i < Math.min(jsonarry.length(),20);i++){
                               JSONObject jsobj = jsonarry.getJSONObject(i);
                               String id = jsobj.getString("movie_id");
                               String title = jsobj.getString("movie_title");
                               String year = jsobj.getString("movie_year");
                               String director = jsobj.getString("director");
                               String genres = jsobj.getString("first_three_genres");
                               String stars = jsobj.getString("first_three_stars");
                               Movie movie = new Movie(id,title,year,director,genres,stars);
                               searchResult += movie.toString();
                            }
                            //System.out.println(searchResult);
                            Intent movieListPage = new Intent(MainPage.this, ListViewActivity.class);
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
                message.setText((CharSequence) volleyError);
            }
        }) /**{
            @Override
            protected Map<String, String> getParams() {
                // POST request form data
                final Map<String, String> params = new HashMap<>();
                params.put("title", title.getText().toString());
                System.out.println("search title: "+title.getText().toString());
                return params;
            }
        }*/;

        // important: queue.add is where the login request is actually sent
        queue.add(searchRequest);

    }
}