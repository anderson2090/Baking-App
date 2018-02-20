package com.example.usama.bakingapp2;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usama.bakingapp2.model.Recipe;
import com.example.usama.bakingapp2.utils.APIClient;
import com.example.usama.bakingapp2.utils.APIEndPoints;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    BakingApp app;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    Parcelable listState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        app = (BakingApp) getApplication();


        final APIEndPoints apiService = APIClient.getClient().create(APIEndPoints.class);
        retrofit2.Call<List<Recipe>> call = apiService.getRecipe();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {


                app.setRecipes(response.body());
                recyclerView = (RecyclerView) findViewById(R.id.main_activity_recycler_view);
                layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                adapter = new RecyclerAdapter();
                recyclerView.setAdapter(adapter);
                if (listState != null) {
                    layoutManager.onRestoreInstanceState(listState);
                }


            }

            @Override
            public void onFailure(retrofit2.Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });
//        AsyncHttpClient client = new AsyncHttpClient();
//        client.get(url, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                String response = null;
//                try {
//                    response = new String(responseBody, "UTF-8");
//
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//
//                Gson gson = new Gson();
//                Type founderListType = new TypeToken<ArrayList<Recipe>>() {
//                }.getType();
//
//                List<Recipe> recipes = gson.fromJson(response, founderListType);
//                app.setRecipes(recipes);
//
//                recyclerView = (RecyclerView) findViewById(R.id.main_activity_recycler_view);
//                layoutManager = new LinearLayoutManager(getApplicationContext());
//                recyclerView.setLayoutManager(layoutManager);
//                adapter = new RecyclerAdapter();
//                recyclerView.setAdapter(adapter);
//
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//
//            }
//        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        listState = layoutManager.onSaveInstanceState();
        outState.putParcelable("state", listState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            listState = savedInstanceState.getParcelable("state");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (layoutManager != null) {
            if (listState != null) {
                layoutManager.onRestoreInstanceState(listState);
            }
        }
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


        public List<Recipe> recipes = app.getRecipes();


        public RecyclerAdapter() {
            /*for (int i = 0; i <= 100; i++) {
                recipes.add("Recipe " + i);
            }*/
        }

        @Override
        public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_card_layout, parent, false);
            RecipeViewHolder viewHolder = new RecipeViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final RecipeViewHolder holder, int position) {
            holder.recipeNameTextView.setText(recipes.get(position).getName());

            /*
            Uncomment the following line of code
            to make sure that this
            code will work if there's an image
            in the JSON response.
             */

            //recipes.get(position).setImage("http://i.imgur.com/DvpvklR.png");

            if (!recipes.get(position).getImage().equals("")) {
                Picasso.with(getApplicationContext())
                        .load(recipes.get(position).getImage())
                        .placeholder(R.drawable.no_image_available)
                        .error(R.drawable.no_image_available)
                        .into(holder.backGroundImageView);
            } else {
                Picasso.with(getApplicationContext())
                        .load(R.drawable.no_image_available)
                        .into(holder.backGroundImageView);
            }

        }

        @Override
        public int getItemCount() {
            return recipes.size();
        }

    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout recipe_card_layout;
        public TextView recipeNameTextView;
        public ImageView backGroundImageView;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            backGroundImageView = itemView.findViewById(R.id.recipe_background_image_view);
            recipe_card_layout = itemView.findViewById(R.id.recipe_card_layout);
            recipeNameTextView = itemView.findViewById(R.id.recipe_name_text_view);
        }
    }

//    public void loadData(){
//        final APIEndPoints apiService = APIClient.getClient().create(APIEndPoints.class);
//        retrofit2.Call<List<Recipe>> call = apiService.getRecipe();
//        call.enqueue(new Callback<List<Recipe>>() {
//            @Override
//            public void onResponse(retrofit2.Call<List<Recipe>> call, Response<List<Recipe>> response) {
//
//                List<Recipe> recipes = response.body();
//
//                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(retrofit2.Call<List<Recipe>> call, Throwable t) {
//                Toast.makeText(getApplicationContext(),"Failure",Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


}
