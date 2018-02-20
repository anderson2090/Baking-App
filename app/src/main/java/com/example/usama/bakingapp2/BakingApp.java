package com.example.usama.bakingapp2;


import android.app.Application;
import android.widget.Toast;

import com.example.usama.bakingapp2.model.Recipe;
import com.example.usama.bakingapp2.utils.APIClient;
import com.example.usama.bakingapp2.utils.APIEndPoints;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

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
