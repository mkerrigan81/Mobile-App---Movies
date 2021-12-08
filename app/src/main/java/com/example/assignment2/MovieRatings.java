package com.example.assignment2;

import android.graphics.Bitmap;

public class MovieRatings {

    private final String movieTitle;
    private final String movieRating;
    private final Bitmap imageUrl;

    public MovieRatings(String movieTitle, String movieRating, Bitmap imageUrl){
        this.movieRating = movieRating;
        this.movieTitle = movieTitle;
        this.imageUrl = imageUrl;
    }

    public String getMovieTitle(){
        return movieTitle;
    }
    public String getMovieRating(){
        return movieRating;
    }
    public Bitmap getMovieImage(){
        return imageUrl;}
}
