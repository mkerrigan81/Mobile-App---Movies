package com.example.assignment2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultHolder> {

    Context context;
    List<ParseObject> list;
    int pageId;
    public ArrayList<String> arrayListChecked = new ArrayList<String>();

    public ResultAdapter(Context context, List<ParseObject> list, int page_id){
        this.list = list;
        this.context = context;
        pageId = page_id;
    }

    @Override
        public ResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.result_cell, parent, false);
            return new ResultHolder(v);
        }
    @Override
    public void onBindViewHolder(ResultHolder holder, int position) {
        switch(pageId){

            //Case for first page i.e DisplayMovies
            case 1:
                ParseObject object = list.get(position);
                if (object.getString("Title") != null){
                    holder.name.setText(object.getString("Title"));
                    holder.radio.setVisibility(View.INVISIBLE);
                }
                else
                    holder.name.setText("null");

                holder.cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (holder.cb.isChecked()){
                            System.out.println(list.get(position).getString("Title"));
                            arrayListChecked.add(list.get(position).getString("Title"));
                        }
                        else{
                            arrayListChecked.remove(list.get(position).getString("Title"));
                        }
                    }
                });
                break;

            //Case for second page i.e Favourites
            case 2:
                ParseObject favouriteObject = list.get(position);
                if (favouriteObject.getBoolean("fav")){
                    holder.name.setText(favouriteObject.getString("Title"));
                    holder.radio.setVisibility(View.INVISIBLE);
                    holder.cb.setChecked(true);
                }
                else {
                    holder.name.setVisibility(View.GONE);
                    //Remove spaces for movies which aren't in the favourites from the RecyclerView
                    holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
                    holder.cb.setVisibility(View.INVISIBLE);
                    holder.radio.setVisibility(View.INVISIBLE);

                }

                holder.cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!holder.cb.isChecked()){
                            System.out.println(list.get(position).getString("Title"));
                            arrayListChecked.add(list.get(position).getString("Title"));
                        }
                        else{
                            arrayListChecked.remove(list.get(position).getString("Title"));
                        }
                    }
                });
                break;

            //Case for third page i.e EditMovies
            case 3:
                ParseObject editObject = list.get(position);

                if (editObject.getString("Title") != null){
                    holder.name.setText(editObject.getString("Title"));
                    holder.cb.setVisibility(View.INVISIBLE);
                    holder.radio.setVisibility(View.INVISIBLE);
                }
                else{
                    holder.name.setText("null");

                }

                holder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("TAG", "Movie was clicked");

                        //Creating an intent so I can pass all the values from the fields over to my helper activity
                        Intent intent = new Intent(context, EditMovieDetails.class);
                        intent.putExtra("Title", list.get(position).getString("Title"));
                        intent.putExtra("Year", list.get(position).getInt("Year"));
                        intent.putExtra("Director", list.get(position).getString("Director"));
                        intent.putExtra("Actor", list.get(position).getString("Actor"));
                        intent.putExtra("Rating", list.get(position).getInt("Rating"));
                        intent.putExtra("Review", list.get(position).getString("Review"));
                        intent.putExtra("Favourite", list.get(position).getBoolean("fav"));
                        context.startActivity(intent);
                    }
                });
                break;

            //Case for fourth page i.e SearchMovies
            case 4:
                ParseObject searchObject = list.get(position);

                if (searchObject.getString("Title")!= null){
                    holder.name.setText(searchObject.getString("Title"));
                    holder.cb.setVisibility(View.INVISIBLE);
                    holder.radio.setVisibility(View.INVISIBLE);
                }else{
                    holder.name.setText("null");
                }
                break;

            //Case for fifth page i.e Ratings
            case 5:
                ParseObject imdbObject = list.get(position);
                RadioButton lastCheckedRB = null;

                if (imdbObject.getString("Title") != null){
                    holder.name.setText(imdbObject.getString("Title"));
                    holder.cb.setVisibility(View.INVISIBLE);
                    holder.radio.setVisibility(View.INVISIBLE);
                }
                else{
                    holder.name.setText("null");
                }
                /*holder.radio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (holder.radio.isChecked()){
                            System.out.println(list.get(position).getString("Title"));
                            arrayListChecked.add(list.get(position).getString("Title"));

                        }else{
                            arrayListChecked.remove(list.get(position).getString("Title"));
                        }
                    }
                });*/

                holder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println(list.get(position).getString("Title"));
                        arrayListChecked.add(list.get(position).getString("Title"));
                    }
                });
                break;
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public ArrayList<String> getArrayListChecked(){
        return arrayListChecked;
    }

    public void reset(){
        arrayListChecked.removeAll(arrayListChecked);
    }
}
