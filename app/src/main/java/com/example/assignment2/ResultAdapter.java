package com.example.assignment2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

            case 2:
                ParseObject favouriteObject = list.get(position);
                if (favouriteObject.getBoolean("fav")){
                    holder.name.setText(favouriteObject.getString("Title"));
                    holder.radio.setVisibility(View.INVISIBLE);
                    holder.cb.setChecked(true);
                }
                else {
                    holder.name.setText("");
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

            case 5:
                ParseObject imdbObject = list.get(position);

                if (imdbObject.getString("Title") != null){
                    holder.name.setText(imdbObject.getString("Title"));
                    holder.cb.setVisibility(View.INVISIBLE);
                }
                else{
                    holder.name.setText("null");
                }

                holder.radio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (holder.radio.isChecked()){
                            System.out.println(list.get(position).getString("Title"));
                            arrayListChecked.add(list.get(position).getString("Title"));
                        }else{
                            arrayListChecked.remove(list.get(position).getString("Title"));
                        }
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
}
