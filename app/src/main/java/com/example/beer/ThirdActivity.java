package com.example.beer;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SearchView;
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
    private BeerAdapter adapter;
    private RecyclerView recyclerView;
    private TextView textView_title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Intent intent = getIntent();
        fillBeerList(intent);
        setUpRecyclerView();
        textView_title = findViewById(R.id.textView_title);
        if (beers.size() <= 1)
            textView_title.setText("We found " + beers.size() + " result.");
        else
            textView_title.setText("We found " + beers.size() + " results.");

        EditText editText = findViewById(R.id.editText_search);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }

    private void fillBeerList(Intent intent){
        beers = new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray(intent.getStringExtra("data"));
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
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView_beer);
        adapter = new BeerAdapter(beers);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
    }

    private void filter(String text){
        ArrayList<Beer> filteredList = new ArrayList<>();

        for (Beer item : beers){
            if (item.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        if (beers.size() <= 1)
            textView_title.setText("We found " + filteredList.size() + " result.");
        else
            textView_title.setText("We found " + filteredList.size() + " results.");
        adapter.filterList(filteredList);
    }
}
