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
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MainPage extends AppCompatActivity {

    private EditText title;
    private EditText year;
    private EditText director;
    private EditText star_name;
    private TextView message;
    private Button searchButton;

    /*
      In Android, localhost is the address of the device or the emulator.
      To connect to your machine, you need to use the below IP address
     */
    private final String host = "10.0.2.2";
    private final String port = "8080";
    private final String domain = "cs122b-spring21-team-48-war";
    private final String baseURL = "http://" + host + ":" + port + "/" + domain;


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

        message.setText("Trying to search");
        // use the same network queue across our application
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        // request type is POST
        final StringRequest searchRequest = new StringRequest(
                Request.Method.GET,
                baseURL + "/api/movies",
                response -> {
                    // TODO: should parse the json response to redirect to appropriate functions
                    //  upon different response value.
                    Log.d("login.success", response);
                    // initialize the activity(page)/destination
                    Intent moviePage = new Intent(MainPage.this, MovieList.class);
                    // activate the list page.
                    startActivity(moviePage);
                },
                error -> {
                    // error
                    Log.d("login.error", error.toString());
                }) {
            @Override
            protected Map<String, String> getParams() {
                // POST request form data
                final Map<String, String> params = new HashMap<>();
                params.put("title", title.getText().toString());

                return params;
            }
        };

        // important: queue.add is where the login request is actually sent
        queue.add(searchRequest);

    }
}