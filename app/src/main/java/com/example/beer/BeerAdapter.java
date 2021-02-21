package com.example.beer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.R.drawable.btn_star_big_off;
import static android.R.drawable.btn_star_big_on;
import static androidx.core.content.ContextCompat.startActivity;

public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.ViewHolder> {

    private List<Beer> beers;

    public BeerAdapter(List<Beer> beers){
        this.beers = beers;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView_name;
        TextView textView_description;
        ImageView imageView_beer;
        ImageView imageView_favorite;

        public ViewHolder (View itemView){
            super(itemView);
            textView_name = itemView.findViewById(R.id.textView_name);
            textView_description = itemView.findViewById(R.id.textView_description);
            imageView_beer = itemView.findViewById(R.id.imageView_beer);
            imageView_favorite = itemView.findViewById(R.id.imageView_favorite);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View beerView = inflater.inflate(R.layout.item_beer, parent, false);
        ViewHolder viewHolder = new ViewHolder(beerView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Beer beer = beers.get(position);
        holder.textView_name.setText(beer.getName());
        holder.textView_description.setText(beer.getDescription());

        Picasso.get().load(beer.getImageURL()).into(holder.imageView_beer);

        holder.imageView_beer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, FourthActivity.class);
                intent.putExtra("beer", beer);
                context.startActivity(intent);
            }
        });

        if (beer.isFavorite())
            holder.imageView_favorite.setBackgroundResource(btn_star_big_on);
        else
            holder.imageView_favorite.setBackgroundResource(btn_star_big_off);

        holder.imageView_favorite.setOnClickListener(v -> {
            if (beer.isFavorite())
                beer.setFavorite(false);
            else
                beer.setFavorite(true);
            this.notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return beers.size();
    }

    public void filterList(ArrayList<Beer> filteredList){
        beers = filteredList;
        notifyDataSetChanged();
    }
}
