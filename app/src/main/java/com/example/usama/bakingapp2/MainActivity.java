package com.example.usama.bakingapp2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.main_activity_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);


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
