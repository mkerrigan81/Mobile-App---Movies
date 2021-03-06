package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;

import org.w3c.dom.Text;

public class RegisterMovie extends AppCompatActivity {
    int year, rating;
    String Year, Rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_movie);

        TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
        TextView txtYear = (TextView) findViewById(R.id.txtYear);
        TextView txtDirector = (TextView) findViewById(R.id.txtDirector);
        TextView txtActors = (TextView) findViewById(R.id.txtActorsActresses);
        TextView txtRatings = (TextView) findViewById(R.id.txtRating);
        TextView txtReview = (TextView) findViewById(R.id.txtReview);
        Button btnSave = (Button) findViewById(R.id.btnSave);

        //Setting onClickListener so that when the Save button is clicked
        //the data entered will populate the Back4App database
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable(RegisterMovie.this)){

                    ParseObject movie = new ParseObject("Movies");//Parsing Movie object into my Back4App database

                    movie.put("Title", txtTitle.getText().toString());//Parsing Movie Title into Title field in my database

                    Year = txtYear.getText().toString();//Converting value from txtYear to int so it can be parsed into the number field in my database
                    year = Integer.parseInt(Year);
                    movie.put("Year", year);

                    movie.put("Director", txtDirector.getText().toString());//Parsing Director into my database

                    movie.put("Actor", txtActors.getText().toString());//Parsing Actor into my database

                    Rating = txtRatings.getText().toString(); //Converting value from txtRatings to int so it can be parsed into database
                    rating = Integer.parseInt(Rating);
                    movie.put("Rating", rating);

                    movie.put("Review", txtReview.getText().toString()); //Parsing Review into my database

                    //Check condition to check that the Rating is between 1 and 10
                    //and that the Year is more than 1894
                    if (rating > 1 && rating < 10 && year > 1894){
                        movie.saveInBackground(e -> {

                            System.out.println("Movie saved");
                            Toast.makeText(RegisterMovie.this, "Movie Registered",
                                    Toast.LENGTH_LONG).show();
                        });
                    }
                    //If the conditions aren't met the user will be detailed with an error message
                    else{
                        Toast.makeText(RegisterMovie.this, "Please check data entered for Year and Rating",
                                Toast.LENGTH_LONG).show();
                        System.out.println("Couldn't save Movie");
                    }
                }else{
                    Toast.makeText(RegisterMovie.this, "Your device isn't connected to the internet", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    //Method to check connectivity to internet
    public boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

/////////METHODS FOR UNIT TESTING//////////////
    public boolean yearIsValid(int yearValid){
        year = yearValid;

        if (yearValid > 1894){
            return true;
        }else {
            return false;
        }
    }

    public boolean ratingIsValid(int ratingValid){
        rating = ratingValid;

        if (ratingValid >= 1 && ratingValid <= 10){
            return true;
        }else{
            return false;
        }
    }
}