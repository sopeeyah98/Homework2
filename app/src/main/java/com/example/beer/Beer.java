package com.example.beer;

import android.os.Parcel;
import android.os.Parcelable;

public class Beer implements Parcelable {
    private String name;
    private String description;
    private String abv;
    private String first_brewed;
    private String food_parings;
    private String brewers_tips;
    private String imageURL;
    private boolean favorite;

    public Beer(){
        favorite = false;
    }
    public Beer(String name, String description, String abv, String first_brewed, String food_parings, String brewers_tips, String imageURL) {
        this.name = name;
        this.description = description;
        this.abv = abv;
        this.first_brewed = first_brewed;
        this.food_parings = food_parings;
        this.brewers_tips = brewers_tips;
        this.imageURL = imageURL;
        favorite = false;
    }

    private Beer(Parcel in){
        name = in.readString();
        description = in.readString();
        abv = in.readString();
        first_brewed = in.readString();
        food_parings = in.readString();
        brewers_tips = in.readString();
        imageURL = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAbv() {
        return abv;
    }

    public void setAbv(String abv) {
        this.abv = abv;
    }

    public String getFirst_brewed() {
        return first_brewed;
    }

    public void setFirst_brewed(String first_brewed) {
        this.first_brewed = first_brewed;
    }

    public String getFood_parings() {
        return food_parings;
    }

    public void setFood_parings(String food_parings) {
        this.food_parings = food_parings;
    }

    public String getBrewers_tips() {
        return brewers_tips;
    }

    public void setBrewers_tips(String brewers_tips) {
        this.brewers_tips = brewers_tips;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(abv);
        parcel.writeString(first_brewed);
        parcel.writeString(food_parings);
        parcel.writeString(brewers_tips);
        parcel.writeString(imageURL);
    }
    public static final Parcelable.Creator<Beer> CREATOR = new Parcelable.Creator<Beer>() {
        public Beer createFromParcel(Parcel in) {
            return new Beer(in);
        }

        public Beer[] newArray(int size) {
            return new Beer[size];

        }
    };
}
