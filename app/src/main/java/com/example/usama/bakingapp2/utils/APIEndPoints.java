package com.example.usama.bakingapp2.utils;


import com.example.usama.bakingapp2.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIEndPoints {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipe();
}
