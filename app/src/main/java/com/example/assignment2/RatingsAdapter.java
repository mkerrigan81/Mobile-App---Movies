package com.example.assignment2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseObject;

import java.util.List;

public class RatingsAdapter extends RecyclerView.Adapter<ResultHolder> {
    Context context;
    List<Object> ratingsList;

    public RatingsAdapter(Context context, List<Object> ratingsList){
        this.context = context;
        this.ratingsList = ratingsList;
    }

    @Override
    public ResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ratings_cell, parent, false);
        return new ResultHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultHolder holder, int position) {
        //Populating our Ratings RecyclerView with Title, Rating and Bitmap image of image of movie
        MovieRatings movieRatings = (MovieRatings) ratingsList.get(position);
        holder.viewTitle.setText(movieRatings.getMovieTitle());
        holder.viewRating.setText(movieRatings.getMovieRating());
        holder.movieImage.setImageBitmap(movieRatings.getMovieImage());

        holder.viewTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Creating intent");
                /*Intent intent = new Intent(context, MovieImage.class);
                intent.putExtra("MovieImage", ((MovieRatings) ratingsList.get(position)).getMovieImage());*/

                Bundle bundle = new Bundle();
                bundle.putParcelable("MovieImage", ((MovieRatings) ratingsList.get(position)).getMovieImage());
            }
        });

    }

    @Override
    public int getItemCount() {
        return ratingsList.size();
    }

    //Supplementary method to clear RecylcerView
    public void removeAt(){
        int size = ratingsList.size();
        ratingsList.clear();
        notifyItemRangeRemoved(0, size);
    }
}
