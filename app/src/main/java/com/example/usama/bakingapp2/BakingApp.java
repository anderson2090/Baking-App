package com.example.usama.bakingapp2;


import android.app.Application;

import com.example.usama.bakingapp2.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class BakingApp extends Application {

    private List<Recipe> recipes;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
