package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Movie;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DisplayMovies extends AppCompatActivity {
    //Setting class level variables
    Button favourites;
    RecyclerView resultList;
    ResultAdapter adapter;
    ArrayList<String> myArrChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movies);
        favourites = findViewById(R.id.btnFavs);
        resultList = findViewById(R.id.resultList);

        if (isNetworkAvailable(DisplayMovies.this)){
            getData();
        }else{
            Toast.makeText(DisplayMovies.this, "Your device isn't connected to the internet", Toast.LENGTH_LONG).show();
        }


        //Handles when the Favourites button has been clicked
        favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myArrChecked = adapter.getArrayListChecked();
                System.out.println(myArrChecked);
                for (int i = 0; i < myArrChecked.size(); i++){
                    String titleName = myArrChecked.get(i);
                    System.out.println(titleName);
                    updateDataChecked(titleName);
                }
            }
        });
    }

    //Helper method which is called in the mainMethod
    //this is the method used to create the movieQuery and display movies to page
    public void getData(){

        ParseQuery<ParseObject> movieQuery = new ParseQuery<ParseObject>("Movies");
        //Query to get movies sorted alphabetically
        movieQuery.orderByAscending("Title");
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
                    Toast.makeText(DisplayMovies.this, "Failed to receive data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //Creating method which will be called in the getData method
    //which helps return the movies to objList
    public void initData(List<ParseObject> objects){
        adapter = new ResultAdapter(this, objects,1);
        resultList.setLayoutManager(new LinearLayoutManager(this));
        resultList.setAdapter(adapter);
    }

    //Additional method for the Favourites button
    //This method updates the Fav field in the database if the favourites button is clicked
    //When one or more movies have been checked
    private void updateDataChecked(String title_Name){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Movies");
        query.whereEqualTo("Title", title_Name);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null){
                    String objectId = object.getObjectId().toString();
                    query.getInBackground(objectId, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            if (e == null){
                                object.put("fav", true);
                                object.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        //If the movie has successfully been added to the favourites we are
                                        //going to display the user with a message
                                        if (e == null){
                                            System.out.println(title_Name + " is updated to true");
                                            Toast.makeText(DisplayMovies.this, "This movie has been added to your Favourites"
                                                    , Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            }
                        }
                    });
                }
            }
        });
    }

    public boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}