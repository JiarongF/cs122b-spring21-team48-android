package edu.uci.ics.fabflixmobile_team48;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import android.app.SearchManager;
//import android.widget.SearchView;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

   //ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // upon creation, inflate and initialize the layout
        setContentView(R.layout.activity_main);
/**
        List<String> mylist = new ArrayList<>();
        mylist.add("Eraser");
        mylist.add("Pencil");
        mylist.add("Pen");
        mylist.add("Books");
        mylist.add("Ruler");

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,mylist);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.my_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.search_icon);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Here!");
        searchView.setOnQueryTextListener(this);
        return true;
    }
    @Override
    public boolean onQueryTextSubmit(String s){
        return false;
    }
    @Override
    public boolean onQueryTextChange(String s){
        //arrayAdapter.getFilter().filter(s);
        return true;
    }
}
