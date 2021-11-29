package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class EditMovieDetails extends AppCompatActivity {
    Button btnUpdate;
    ResultAdapter adapter;
    private EditText titleUpdate, yearUpdate, directorUpdate, actorUpdate, reviewUpdate, favouriteUpdate;
    private RatingBar ratingBar;

    private String titleName, yearName, directorName, actorName, reviewName, favouriteName;
    private float ratingName;
    private int year, rating;
    private boolean favourite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie_details);
        getData();

        btnUpdate = findViewById(R.id.btnUpdate);
        Log.d("TAG", "Activity started");
        //Call to getIncomingIntent method
        getIncomingIntent();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //When the btnUpdate is clicked we set the values of all our variables we declared above
                titleUpdate = findViewById(R.id.txtTitleResult);
                titleName = titleUpdate.getText().toString();

                //Here we have to convert the value from yearUpdate from a string to an int
                yearUpdate = findViewById(R.id.txtYearResult);
                yearName = yearUpdate.getText().toString();
                year = Integer.parseInt(yearName);
                if (year < 1893){
                    Toast.makeText(EditMovieDetails.this, "Please ensure Year is more recent than 1893", Toast.LENGTH_LONG).show();
                }

                directorUpdate = findViewById(R.id.txtDirectorResult);
                directorName = directorUpdate.getText().toString();

                //Here we are converting the value from ratingBar from a float to an int
                ratingBar = findViewById(R.id.ratingBar);
                ratingName = ratingBar.getRating();
                rating = (int)Math.round(ratingName);
                if (rating < 1 || rating > 10){
                    Toast.makeText(EditMovieDetails.this, "Please ensure Rating is between 1 and 10", Toast.LENGTH_LONG).show();
                }

                actorUpdate = findViewById(R.id.txtActorResult);
                actorName = actorUpdate.getText().toString();

                reviewUpdate = findViewById(R.id.txtReviewResult);
                reviewName = reviewUpdate.getText().toString();

                //Setting the fav field so that if the text from favouriteUpdate equals Favourite it is set as True
                //otherwise it is set as false
                favouriteUpdate = findViewById(R.id.txtFavouriteResult);
                if (favouriteUpdate.getText().toString().equals("Favourite") || favouriteUpdate.getText().toString().equals("FAVOURITE") || favouriteUpdate.getText().toString().equals("favourite")){
                    favourite = true;
                }else if (favouriteUpdate.getText().toString().equals("Not Favourite") || favouriteUpdate.getText().toString().equals("not favourite") || favouriteUpdate.getText().toString().equals("NOT FAVOURITE")){
                    favourite = false;
                }else{
                    Toast.makeText(EditMovieDetails.this, "Please specify if this movie is a 'Favourite' or 'Not Favourite'", Toast.LENGTH_LONG).show();
                }


                //We also call the updateMovie() method
                updateMovie();
            }
        });
    }

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
                    Toast.makeText(EditMovieDetails.this, "Failed to receive data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void initData(List<ParseObject> objects){
        adapter = new ResultAdapter(this, objects, 3);
    }

    //Method to update the current movie with new details
    public void updateMovie(){
        //creating a query using the Movies class
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Movies");
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null){
                    String objectId = object.getObjectId().toString();
                    query.getInBackground(objectId, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            //If the query is passed, we set each field with the new data that has been set to the field variables
                            if (e == null){
                                if (year > 1894 && rating >= 1 && rating <=10 && favouriteUpdate.getText().toString().equals("Favourite") || favouriteUpdate.getText().toString().equals("Not Favourite")){
                                object.put("Title", titleName);
                                object.put("Year", year);
                                object.put("Director", directorName);
                                object.put("Actor", actorName);
                                object.put("Rating", rating);
                                object.put("Review", reviewName);
                                object.put("fav", favourite);

                                object.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null){
                                            System.out.println(titleName + " Movie has been updated");
                                            Toast.makeText(EditMovieDetails.this, "Movie has been updated"
                                                    , Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            System.out.println("Failed to update data " + e.getLocalizedMessage());
                                            }
                                        }
                                    });
                                }
                                else{
                                    Toast.makeText(EditMovieDetails.this, "Data won't be updated while there are errors in fields", Toast.LENGTH_LONG).show();
                                }
                            }
                            else{
                                System.out.println("Failed to update Movie " + e.getLocalizedMessage());
                            }
                        }
                    });
                }
                else{
                    System.out.println(titleName + " Problem somewhere " + e.getLocalizedMessage());
                }
            }
        });

    }


    //Method to get the intent that we set up in our result adapter
    //this will get the values for the movie details
    private void getIncomingIntent(){
        Log.d("TAG", "Check for incoming intents");
        if (getIntent().hasExtra("Title") && getIntent().hasExtra("Year") && getIntent().hasExtra("Director")
            && getIntent().hasExtra("Actor") && getIntent().hasExtra("Review")){
            Log.d("TAG", "Intent found");

            String titleResult = getIntent().getStringExtra("Title");
            int yearResult = getIntent().getIntExtra("Year", 0);
            String directorResult = getIntent().getStringExtra("Director");
            String actorResult = getIntent().getStringExtra("Actor");
            int ratingResult = getIntent().getIntExtra("Rating", 0);
            String reviewResult = getIntent().getStringExtra("Review");
            boolean favouriteResult = getIntent().getBooleanExtra("Favourite", false);

            //Call to the setResult method
            setResult(titleResult, yearResult, directorResult, actorResult, ratingResult, reviewResult, favouriteResult);
        }
    }
    //Method to set the result to the TextViews in our layout file
    private void setResult(String titleResult, int yearResult, String directorResult, String actorResult, int ratingResult, String reviewResult, boolean favouriteResult){
        Log.d("TAG", "Setting title result");

        EditText title = findViewById(R.id.txtTitleResult);
        title.setText(titleResult);

        //Since the year is an int value we have to set it to a string and then
        //set it as the value in the EditText
        EditText year = findViewById(R.id.txtYearResult);
        String yearResults = String.valueOf(yearResult);
        year.setText(yearResults);

        EditText director = findViewById(R.id.txtDirectorResult);
        director.setText(directorResult);

        EditText actor = findViewById(R.id.txtActorResult);
        actor.setText(actorResult);

        //Setting the value of the RatingBar to the int value of ratingResult
        RatingBar rating = findViewById(R.id.ratingBar);
        rating.setRating(ratingResult);

        EditText review = findViewById(R.id.txtReviewResult);
        review.setText(reviewResult);

        EditText favourite = findViewById(R.id.txtFavouriteResult);
        String favouriteResults = String.valueOf(favouriteResult);

        //Doing a check condition to check if the value of favouriteResult is True, we set the
        //text of the EditText to "Favourite"
        if (favouriteResult){
            favourite.setText("Favourite");
        }else{
            favourite.setText("Not Favourite");
        }
    }
}