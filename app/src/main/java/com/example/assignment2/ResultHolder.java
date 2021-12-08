package com.example.assignment2;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ResultHolder extends RecyclerView.ViewHolder {
    TextView name, displayList;
    EditText title, year, director, actors, review, favourite;
    CheckBox cb;
    RadioButton radio;
    RatingBar ratingBar;
    ImageView movieImage;

    //For ratings page
    TextView viewTitle, viewRating;


    public ResultHolder(View itemView) {
        super(itemView);
        //Setting all our variables to the appropriate item in the layout view
        name = itemView.findViewById(R.id.textViewName);
        cb = itemView.findViewById(R.id.checkBox);
        radio = itemView.findViewById(R.id.radioButton);

        //For editMovie question
        title = itemView.findViewById(R.id.txtTitleResult);
        year = itemView.findViewById(R.id.txtYearResult);
        director = itemView.findViewById(R.id.txtDirectorResult);
        actors = itemView.findViewById(R.id.txtActorResult);
        review = itemView.findViewById(R.id.txtReviewResult);
        ratingBar = itemView.findViewById(R.id.ratingBar);
        favourite = itemView.findViewById(R.id.txtFavouriteResult);

        //For rating question
        /*displayList = itemView.findViewById(R.id.txtDisplayList);*/

        viewTitle = itemView.findViewById(R.id.textViewTitle);
        viewRating = itemView.findViewById(R.id.textViewRating);
        movieImage = itemView.findViewById(R.id.movieImage);
    }
}
