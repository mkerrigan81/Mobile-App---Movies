package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class EditMovies extends AppCompatActivity{
    //Setting class level variables
    RecyclerView editList;
    ResultAdapter adapter;
    ArrayList<String> myArrChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movies);
        editList = findViewById(R.id.editList);

        if (isNetworkAvailable(EditMovies.this)){
            getData();
        }else{
            Toast.makeText(EditMovies.this, "Your device isn't connected to the internet", Toast.LENGTH_LONG).show();
        }
    }

    //Helper method which is called in the mainMethod
    //this is the method used to create the movieQuery and display movies to page
    public void getData(){
        ParseQuery<ParseObject> movieQuery = new ParseQuery<ParseObject>("Movies");
        movieQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objList, ParseException e) {
                if (e == null){
                    System.out.println("Size" + objList.size());
                    initData(objList);
                }
                //In the case that there is no movies in the database, the user will be presented
                //with an error message
                else{
                    Log.d("ParseQuery", e.getMessage());
                    Toast.makeText(EditMovies.this, "Failed to receive data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void initData(List<ParseObject> objects){
        adapter = new ResultAdapter(this, objects,3);
        editList.setLayoutManager(new LinearLayoutManager(this));
        editList.setAdapter(adapter);
    }
    public boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}