package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class Favourites extends AppCompatActivity {
    Button saveFavourites;
    RecyclerView favouritesList;
    ResultAdapter adapter;
    ArrayList<String> myArrChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        saveFavourites = (Button) findViewById(R.id.btnSaveFavourites);
        favouritesList = findViewById(R.id.favouritesList);

        if (isNetworkAvailable(Favourites.this)){
            getFavourites();;
        }else{
            Toast.makeText(Favourites.this, "Your device isn't connected to the internet", Toast.LENGTH_LONG).show();
        }


        saveFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*myArrChecked = adapter.getArrayListChecked();*/
                myArrChecked = adapter.getArrayListChecked();
                System.out.println(myArrChecked);
                for (int i = 0; i < myArrChecked.size(); i++){
                    String titleName = myArrChecked.get(i);
                    System.out.println(titleName);
                    updateDataUnchecked(titleName);
                }
            }
        });
    }

    public void getFavourites(){

        ParseQuery<ParseObject> movieQuery = new ParseQuery<ParseObject>("Movies");
        movieQuery.orderByAscending("title");
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
                    Toast.makeText(Favourites.this, "Failed to receive data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void initData(List<ParseObject> objects){
        adapter = new ResultAdapter(this, objects, 2);
        favouritesList.setLayoutManager(new LinearLayoutManager(this));
        favouritesList.setAdapter(adapter);
    }

    public void updateDataUnchecked(String title_name){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Movies");
        query.whereEqualTo("Title", title_name);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null){
                    String objectId = object.getObjectId().toString();
                    query.getInBackground(objectId, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            if (e == null){
                                object.put("fav", false);
                                object.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null){
                                            System.out.println(title_name + " is updated to False");
                                            Toast.makeText(Favourites.this, "This movie has been removed from your Favourites"
                                                    , Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

    }
    public boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
