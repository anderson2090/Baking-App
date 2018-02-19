package com.example.usama.bakingapp2.model;


import android.os.Parcel;
import android.os.Parcelable;

public class RecipeIngredient implements Parcelable {

    private String quantity;
    private String measure;
    private String ingredient;

    public RecipeIngredient() {
    }

    public RecipeIngredient(String quantity, String measure, String ingredients) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredients;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.quantity);
        dest.writeString(this.measure);
        dest.writeString(this.ingredient);
    }

    protected RecipeIngredient(Parcel in) {
        this.quantity = in.readString();
        this.measure = in.readString();
        this.ingredient = in.readString();
    }

    public static final Parcelable.Creator<RecipeIngredient> CREATOR = new Parcelable.Creator<RecipeIngredient>() {
        @Override
        public RecipeIngredient createFromParcel(Parcel source) {
            return new RecipeIngredient(source);
        }

        @Override
        public RecipeIngredient[] newArray(int size) {
            return new RecipeIngredient[size];
        }
    };
}
