package edu.uci.ics.fabflixmobile_team48;

import android.content.Intent;
import android.os.Bundle;
//import androidx.appcompat.app.ActionBarActivity;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;


import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private TextView message;
    private Button loginButton;

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
        setContentView(R.layout.login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        message = findViewById(R.id.message);
        loginButton = findViewById(R.id.login);

        //assign a listener to call a function to handle the user request when clicking a button
        loginButton.setOnClickListener(view -> login());
    }

    public void login() {

        //message.setText("Trying to login");
        // use the same network queue across our application
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        // request type is POST
        final StringRequest loginRequest = new StringRequest(
                Request.Method.POST,
                baseURL + "/api/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse (String response){
                        //message.setText(response);
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String status = jsonObj.getString("status");
                            System.out.println("Status is " + status);
                            String login_message = jsonObj.getString("message");
                            if (status.equals("success")) {
                                // initialize the activity(page)/destination
                                Intent mainPage = new Intent(Login.this, MainPage.class);
                                // activate the list page.
                                startActivity(mainPage);
                            } else {
                                // message.setText(jsonObj.getString("message"));
                                message.setText(login_message);
                                // message.setText("Invalid username or password");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                message.setText((CharSequence) volleyError);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // POST request form data
                final Map<String, String> params = new HashMap<>();
                params.put("email", email.getText().toString());
                params.put("password", password.getText().toString());

                return params;
            }
        };

        // important: queue.add is where the login request is actually sent
        queue.add(loginRequest);

    }
}