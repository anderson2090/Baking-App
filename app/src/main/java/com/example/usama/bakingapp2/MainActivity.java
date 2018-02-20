package com.example.usama.bakingapp2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.usama.bakingapp2.model.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    BakingApp app;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        app = (BakingApp) getApplication();

        recyclerView = (RecyclerView) findViewById(R.id.main_activity_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);



        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = null;
                try {
                    response = new String(responseBody, "UTF-8");

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                Gson gson = new Gson();
                Type founderListType = new TypeToken<ArrayList<Recipe>>() {
                }.getType();

                List<Recipe> recipes = gson.fromJson(response, founderListType);
                app.setRecipes(recipes);



            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class RecyclerAdapter extends RecyclerView.Adapter<RecipeViewHolder> {


        public ArrayList<String> recipes = new ArrayList<>();

        public RecyclerAdapter() {
            for (int i = 0; i <= 100; i++) {
                recipes.add("Recipe " + i);
            }
        }

        @Override
        public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_card_layout, parent, false);
            RecipeViewHolder viewHolder = new RecipeViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecipeViewHolder holder, int position) {
            holder.recipeNameTextView.setText(recipes.get(position));
        }

        @Override
        public int getItemCount() {
            return recipes.size();
        }

    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        public TextView recipeNameTextView;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeNameTextView = itemView.findViewById(R.id.recipe_name_text_view);
        }
    }


}
