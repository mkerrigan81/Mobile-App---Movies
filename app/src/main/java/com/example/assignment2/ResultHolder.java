package com.example.assignment2;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ResultHolder extends RecyclerView.ViewHolder {
    TextView name;
    EditText title, year, director, actors, review, favourite, lookup;
    CheckBox cb;
    RadioButton radio;
    RatingBar ratingBar;
    String compareTitle, compareDirector, compareActor;


    public ResultHolder(View itemView) {
        super(itemView);
        //Setting all our variables to the appropriate item in the layout view
        name = itemView.findViewById(R.id.textViewName);
        cb = itemView.findViewById(R.id.checkBox);
        radio = itemView.findViewById(R.id.radioButton);

        title = itemView.findViewById(R.id.txtTitleResult);
        year = itemView.findViewById(R.id.txtYearResult);
        director = itemView.findViewById(R.id.txtDirectorResult);
        actors = itemView.findViewById(R.id.txtActorResult);
        review = itemView.findViewById(R.id.txtReviewResult);
        ratingBar = itemView.findViewById(R.id.ratingBar);
        favourite = itemView.findViewById(R.id.txtFavouriteResult);

        /*lookup = itemView.findViewById(R.id.txtSearchBox);
        str1 = lookup.getText().toString();*/
    }
}
