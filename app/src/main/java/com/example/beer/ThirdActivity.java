package com.example.beer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity {
    private ArrayList<Beer> beers;
    private RecyclerView recyclerView;
    private TextView textView_title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        recyclerView = findViewById(R.id.recyclerView_beer);
        textView_title = findViewById(R.id.textView_title);
        beers = new ArrayList<>();

        Intent intent = getIntent();

        try{
            JSONArray jsonArray = new JSONArray(intent.getStringExtra("data"));
            if (jsonArray.length() <= 1)
                textView_title.setText("We found " + jsonArray.length() + " result.");
            else
                textView_title.setText("We found " + jsonArray.length() + " results.");

            // update arraylist with data
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String food_pairings = jsonObject.getJSONArray("food_pairing").toString();
                food_pairings = food_pairings.replace("\"", "").replace("[","").replace("]", "").replace(",", ", ");

                Beer beer = new Beer(jsonObject.getString("name"),
                        jsonObject.getString("description"), jsonObject.getString("abv"),
                        jsonObject.getString("first_brewed"), food_pairings,
                        jsonObject.getString("brewers_tips"),jsonObject.getString("image_url"));

                beers.add(beer);
            }
            BeerAdapter adapter = new BeerAdapter(beers);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(itemDecoration);
        }catch (JSONException e){
            e.printStackTrace();
        }





    }
}
