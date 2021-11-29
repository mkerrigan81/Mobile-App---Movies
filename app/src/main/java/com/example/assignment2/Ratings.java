package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class Ratings extends AppCompatActivity {

    Button imdb;
    RecyclerView resultList;
    ResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);
        resultList = findViewById(R.id.resultList);
        imdb = findViewById(R.id.btnIMDB);
        getData();
    }

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
                    Toast.makeText(Ratings.this, "Failed to receive data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void initData(List<ParseObject> objects){
        adapter = new ResultAdapter(this, objects,5);
        resultList.setLayoutManager(new LinearLayoutManager(this));
        resultList.setAdapter(adapter);
    }
}