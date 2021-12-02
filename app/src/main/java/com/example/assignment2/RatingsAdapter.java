package com.example.assignment2;

import android.content.Context;
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
    /*int ratingNumber;*/

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
        MovieRatings movieRatings = (MovieRatings)
                ratingsList.get(position);
        holder.viewTitle.setText(movieRatings.getMovieTitle());
        holder.viewRating.setText(movieRatings.getMovieRating());
    }

    @Override
    public int getItemCount() {
        return ratingsList.size();
    }
}
