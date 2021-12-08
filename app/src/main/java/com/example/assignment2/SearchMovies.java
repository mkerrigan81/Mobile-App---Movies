package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchMovies extends AppCompatActivity {
    EditText txtSearchBox;
    Button btnLookup;
    String str1, title, actor, director;

    RecyclerView searchResultList;
    ResultAdapter adapter;
    ArrayList<String> myArrChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movies);
        searchResultList = findViewById(R.id.searchRecyclerView);
        btnLookup = findViewById(R.id.btnLookup);
        txtSearchBox = findViewById(R.id.txtSearchBox);

        btnLookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str1 = txtSearchBox.getText().toString();
                if (isNetworkAvailable(SearchMovies.this)){
                    getData();
                }else{
                    Toast.makeText(SearchMovies.this, "Your device isn't connected to the internet", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void getData(){
        //We're setting our str1 value as the value entered into txtSearchBox
        str1 = txtSearchBox.getText().toString();
        //Starting our query looking in the Movies class
        ParseQuery<ParseObject> titleQuery = new ParseQuery<ParseObject>("Movies");
        //Here we're adding more queries to check for a movie where the value in the Title column
        //starts or ends with the value from our str1
        titleQuery.whereMatches("Title", str1, "i");
        titleQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objList, ParseException e) {
                if (e == null){
                    System.out.println("Title Size" + objList.size());
                    //If the query is successful we call our initData(objList) method again to display results
                    //to the searchResultList
                    initData(objList);

                    if (objList.isEmpty()){
                        ParseQuery<ParseObject> actorQuery = new ParseQuery<ParseObject>("Movies");
                        actorQuery.whereMatches("Actor", str1, "i");
                        actorQuery.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> objList2, ParseException e) {
                                if (e == null){
                                    System.out.println("Actor Size" + objList2.size());
                                    //If the query is successful we call our initData(objList) method again to display results
                                    //to the searchResultList
                                    initData(objList2);

                                    if(objList.isEmpty() && objList2.isEmpty()){
                                        ParseQuery<ParseObject> directorQuery = new ParseQuery<ParseObject>("Movies");
                                        directorQuery.whereMatches("Director", str1, "i");
                                        directorQuery.findInBackground(new FindCallback<ParseObject>() {
                                            @Override
                                            public void done(List<ParseObject> objList3, ParseException e) {
                                                if (e == null){
                                                    System.out.println("Director Size" + objList3.size());
                                                    //If the query is successful we call our initData(objList) method again to display results
                                                    //to the searchResultList
                                                    initData(objList3);
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        });
                    }
                }
                //In the case that there is no movies in the database, the user will be presented
                //with an error message
                else{
                    Log.d("ParseQuery", e.getMessage());
                    Toast.makeText(SearchMovies.this, "Failed to receive data", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void initData(List<ParseObject> objects){
        adapter = new ResultAdapter(this, objects,4);
        searchResultList.setLayoutManager(new LinearLayoutManager(this));
        searchResultList.setAdapter(adapter);
    }

    public boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}