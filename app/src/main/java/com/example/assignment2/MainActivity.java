package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onRegister(View view){
        startActivity(new Intent(MainActivity.this, RegisterMovie.class));
    }

    public void goDisplayMovies(View view){
        startActivity(new Intent(MainActivity.this, DisplayMovies.class));
    }

    public void goFavourites(View view){
        startActivity(new Intent(MainActivity.this, Favourites.class));
    }

    public void goEditMovies(View view){
        startActivity(new Intent(MainActivity.this, EditMovies.class));
    }

    public void goSearchMovies(View view){
        startActivity(new Intent(MainActivity.this, SearchMovies.class));
    }

    public void goRatings(View view){
        startActivity(new Intent(MainActivity.this, Ratings.class));
    }
}