package com.example.assignment2;

public class MovieRatings {

    private final String movieTitle;
    private final String movieRating;

    public MovieRatings(String movieTitle, String movieRating){
        this.movieRating = movieRating;
        this.movieTitle = movieTitle;
    }

    public String getMovieTitle(){
        return movieTitle;
    }
    public String getMovieRating(){
        return movieRating;
    }
}
