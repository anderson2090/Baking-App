package com.example.usama.bakingapp2;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usama.bakingapp2.model.Recipe;
import com.example.usama.bakingapp2.utils.APIClient;
import com.example.usama.bakingapp2.utils.APIEndPoints;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import static com.example.usama.bakingapp2.utils.NetworkHelper.hasNetworkConnection;
import static com.example.usama.bakingapp2.utils.ScreenSizeHelper.LARGE;
import static com.example.usama.bakingapp2.utils.ScreenSizeHelper.screenSize;

public class MainActivity extends RootActivity {

    BakingApp app;
    RecyclerView recyclerView;
    RelativeLayout mainActivityRootLayout;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    Parcelable listState;
    final String STATE = "STATE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        app = (BakingApp) getApplication();
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mainActivityRootLayout = (RelativeLayout) findViewById(R.id.main_activity_root_layout);

        // Using Retrofit 2 Synchronously inside AsyncTask Loader
        getLoaderManager().initLoader(0, null, new LoaderManager.LoaderCallbacks<List<Recipe>>() {
            @Override
            public Loader<List<Recipe>> onCreateLoader(int i, Bundle bundle) {
                return new RecipeLoadingTask(getApplicationContext());
            }

            @Override
            public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> recipes) {

                if (!hasNetworkConnection(getApplicationContext())) {
                    Snackbar.make(mainActivityRootLayout,
                            "Please Check Your Internet connection",
                            Snackbar.LENGTH_INDEFINITE).setAction(R.string.retry, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            progressBar.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(), R.string.retrying, Toast.LENGTH_SHORT).show();
                            //Reload Current Activity
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);
                        }
                    }).show();
                    progressBar.setVisibility(View.GONE);
                } else {

                    app.setRecipes(recipes);
                    recyclerView = (RecyclerView) findViewById(R.id.main_activity_recycler_view);
                    if (screenSize(getApplicationContext()) == LARGE) {
                        layoutManager = new GridLayoutManager(getApplicationContext(), 3);
                    } else {
                        layoutManager = new LinearLayoutManager(getApplicationContext());
                    }

                    recyclerView.setLayoutManager(layoutManager);
                    adapter = new RecyclerAdapter();
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                    if (listState != null) {
                        layoutManager.onRestoreInstanceState(listState);
                    }

                }
            }

            @Override
            public void onLoaderReset(Loader<List<Recipe>> loader) {

            }
        }).forceLoad();


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (layoutManager != null) {
            listState = layoutManager.onSaveInstanceState();
            outState.putParcelable(STATE, listState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            listState = savedInstanceState.getParcelable(STATE);
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

    private static class RecipeLoadingTask extends AsyncTaskLoader<List<Recipe>> {
        List<Recipe> recipes;

        public RecipeLoadingTask(Context context) {
            super(context);
        }

        @Override
        public List<Recipe> loadInBackground() {


            final APIEndPoints apiService = APIClient.getClient().create(APIEndPoints.class);
            retrofit2.Call<List<Recipe>> call = apiService.getRecipe();

            try {
                recipes = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return recipes;
        }
    }

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecipeViewHolder> {


        List<Recipe> recipes = app.getRecipes();


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


            /* Display the image if there is one in the JSON response
               Otherwise load the placeholder image.
             */
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

        class RecipeViewHolder extends RecyclerView.ViewHolder {

            RelativeLayout recipeCardLayout;
            TextView recipeNameTextView;
            ImageView backGroundImageView;
            CardView recipeCard;

            RecipeViewHolder(View itemView) {
                super(itemView);
                backGroundImageView = itemView.findViewById(R.id.recipe_background_image_view);
                recipeCardLayout = itemView.findViewById(R.id.recipe_card_layout);
                recipeNameTextView = itemView.findViewById(R.id.recipe_name_text_view);
                recipeCard = itemView.findViewById(R.id.recipe_card);
                recipeCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(),StepsAndIngredientsActivity.class);
                        intent.putExtra("currentRecipe",getAdapterPosition());
                        startActivity(intent);
                    }
                });
            }
        }

    }


}
