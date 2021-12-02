package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Ratings extends AppCompatActivity {

    Button btnImdb;
    RecyclerView resultList;
    ResultAdapter adapter;
    ArrayList<String> myArrChecked;

    String queryString = "https://imdb-api.com/en/API/SearchTitle/k_tsrkk0q3/"; //First queryString to get the title
    String queryString2 = "https://imdb-api.com/en/API/Ratings/k_tsrkk0q3/"; //Second queryString to get ID

    //For displaying movie ratings
    RecyclerView ratingList;//RecyclerView which will display the movies and the movie ratings when a title is clicked
    RatingsAdapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Object> viewItems = new ArrayList<>();

    /*ImageView imageView;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);
        resultList = findViewById(R.id.resultList);
        btnImdb = findViewById(R.id.btnIMDB);
        ratingList = findViewById(R.id.ratingList);

        //Call method to populate initial movie list
        getData();

        btnImdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callVolley(queryString, queryString2);
            }
        });
    }

    public void callVolley(String newUrl, String newUrl2){
        RequestQueue queue = Volley.newRequestQueue(this);
        myArrChecked = adapter.getArrayListChecked();
        for (int i = 0; i < myArrChecked.size(); i++){ //for loop to run through array to see which movie is checked

            String titleName = myArrChecked.get(i); //setting new String variable to value of checked movie
            String movieQueryString = newUrl + titleName;//setting new String variable to be newUrl variable + the title of movie
            //First request using the first URL to get the movie title
            StringRequest stringRequest = new StringRequest(Request.Method.GET, movieQueryString,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("First response: " + response);
                            JSONObject jobject = null;
                                try {
                                    for (int j = 0; j < response.length(); j++){ //for loop to run through values from the response
                                        jobject = new JSONObject(response);
                                        JSONArray array = jobject.getJSONArray("results");
                                        JSONObject obj1 = array.getJSONObject(j); // setting our JSONObject as all the titles rather than just 1
                                        System.out.println(obj1.toString());
                                        String id = obj1.getString("id");
                                        String imageUrl = obj1.getString("image");
                                        System.out.println("id = " + id);

                                        /*ImageRequest imageRequest = new ImageRequest(imageUrl,
                                                new Response.Listener<Bitmap>() {
                                                    @Override
                                                    public void onResponse(Bitmap response) {
                                                        *//*imageView.setImageBitmap(response);*//*
                                                    }
                                                }, 0, 0, null, null, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                System.out.println("Image didn't work");
                                            }
                                        });
                                        queue.add(imageRequest);*/

                                        String titleQueryString = newUrl2 + id; //Once we get the id from the first response, we parse it onto the end of our second URL
                                        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, titleQueryString, //Our second StringRequest using titleQueryString to get the rating of movies from first Request
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        System.out.println("Second response: " + response);
                                                        JSONObject jobject2 = null;
                                                            try {
                                                                jobject2 = new JSONObject(response);
                                                                System.out.println(jobject2.toString());
                                                                String rating = jobject2.getString("imDb");
                                                                String title = jobject2.getString("title");
                                                                MovieRatings movieRatings = new MovieRatings(title, rating);
                                                                viewItems.add(movieRatings);
                                                                /*txtDisplayList.append("Movie: " + title + " | " + "Rating: " + rating + "\n" + "\n" + "\n");//Setting our TextView to the title and ratings of movies*/


                                                                layoutManager = new LinearLayoutManager(Ratings.this);
                                                                ratingList.setLayoutManager(layoutManager);
                                                                mAdapter = new RatingsAdapter(Ratings.this, viewItems);
                                                                ratingList.setAdapter(mAdapter);

                                                            }catch (JSONException e){
                                                                e.printStackTrace();//Error message to system for debugging purposes
                                                            }
                                                    }
                                                }
                                                , new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                System.out.println("Rating didn't work");//Error message to system for debugging purposes
                                            }
                                        });
                                        queue.add(stringRequest2);//Adding stringRequest2 to queue
                                    }
                                } catch (JSONException e) {//Error message to system for debugging purposes
                                    e.printStackTrace();
                                }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("That didn't work");//Error message to system for debugging purposes
                }
            });
            queue.add(stringRequest);//Adding stringRequest to queue
        }
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