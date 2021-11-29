package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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

    String queryString = "https://imdb-api.com/en/API/SearchTitle/k_ak8k6cl8/";
    String queryString2 = "https://imdb-api.com/en/API/Ratings/k_ak8k6cl8/tt1375666";
    TextView txtDisplayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);
        resultList = findViewById(R.id.resultList);
        btnImdb = findViewById(R.id.btnIMDB);
        txtDisplayList = findViewById(R.id.txtDisplayList);
        getData();

        btnImdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callVolley(queryString/*, queryString2*/);

                /*myArrChecked = adapter.getArrayListChecked();
                System.out.println(myArrChecked);
                for (int i = 0; i < myArrChecked.size(); i++){
                    String titleName = myArrChecked.get(i);
                    System.out.println(titleName);
                }*/
            }
        });
    }

    public void callVolley(String newUrl/*, String newUrl2*/){
        RequestQueue queue = Volley.newRequestQueue(this);
        myArrChecked = adapter.getArrayListChecked();
        for (int i = 0; i<myArrChecked.size(); i++){
            String titleName = myArrChecked.get(i);
            String movieQueryString = newUrl + titleName;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, movieQueryString,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("First response: " + response);
                            JSONObject jobject = null;
                            try {
                                jobject = new JSONObject(response);
                                JSONArray array = jobject.getJSONArray("results");
                                JSONObject obj1 = array.getJSONObject(0);
                                System.out.println(obj1.toString());
                                String id = obj1.getString("id");
                                System.out.println(id);
                                txtDisplayList.setText("id = " + id);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("That didn't work");
                }
            });
            queue.add(stringRequest);
        }






        /*StringRequest stringRequest2 = new StringRequest(Request.Method.GET, newUrl2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Second response: " + response);
                        JSONObject jobject2 = null;
                        try {
                            jobject2 = new JSONObject(response);
                            String rating = jobject2.getString("imDb");
                            txtDisplayList.setText(rating);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }

                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("Rating didn't work");
            }
        });*/

        /*queue.add(stringRequest2);*/
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