package com.example.beer;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class FourthActivity extends AppCompatActivity {
    private TextView textView_name;
    private TextView textView_abv;
    private TextView textView_firstBrewed;
    private TextView textView_description;
    private TextView textView_foodPairings;
    private TextView textView_brewersTips;
    private ImageView imageView_beer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        textView_name = findViewById(R.id.textView_name);
        textView_abv = findViewById(R.id.textView_abv);
        textView_firstBrewed = findViewById(R.id.textView_firstBrewed);
        textView_description = findViewById(R.id.textView_description);
        textView_foodPairings = findViewById(R.id.textView_foodPairings);
        textView_brewersTips = findViewById(R.id.textView_brewersTips);
        imageView_beer = findViewById(R.id.imageView);

        Intent intent = getIntent();
        Beer beer = intent.getParcelableExtra("beer");

        textView_name.setText(beer.getName());
        textView_abv.setText("ABV: " + beer.getAbv() + "%");
        textView_firstBrewed.setText("First brewed: " + beer.getFirst_brewed());
        textView_description.setText("Description: " + beer.getDescription());
        textView_foodPairings.setText("Food Pairings: " + beer.getFood_parings());
        textView_brewersTips.setText("Brewster's tips: " + beer.getBrewers_tips());
        Picasso.get().load(beer.getImageURL()).into(imageView_beer);
    }
}
