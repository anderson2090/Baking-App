package com.example.usama.bakingapp2.fragments;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.usama.bakingapp2.BakingApp;
import com.example.usama.bakingapp2.R;
import com.example.usama.bakingapp2.model.RecipeIngredient;

import java.util.ArrayList;

public class IngredientsFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Parcelable listState;
    final String LIST_STATE = "LIST_STATE";
    int currentRecipeIndex;
    BakingApp bakingApp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ingredients_fragment, container, false);

        Bundle arguments = getArguments();
        currentRecipeIndex = arguments.getInt("currentRecipe");
        bakingApp = (BakingApp) getActivity().getApplication();

        recyclerView = view.findViewById(R.id.ingredients_recycler_view);
        adapter = new RecyclerAdapter(currentRecipeIndex);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        listState = layoutManager.onSaveInstanceState();
        outState.putParcelable(LIST_STATE, listState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            listState = savedInstanceState.getParcelable(LIST_STATE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (listState != null) {
            layoutManager.onRestoreInstanceState(listState);
        }
    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
        ArrayList<RecipeIngredient> ingredients = new ArrayList<>();

        int currentRecipeIndex;


        public RecyclerAdapter(int currentRecipeIndex) {

           this.currentRecipeIndex = currentRecipeIndex;
           ingredients = bakingApp.getRecipes().get(currentRecipeIndex).getIngredients();
        }

        @Override
        public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ingredient_card, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
            holder.ingredientsCardTextView.setText(ingredients.get(position).getIngredient());
        }

        @Override
        public int getItemCount() {
            return ingredients.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView ingredientsCardTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                ingredientsCardTextView = itemView.findViewById(R.id.ingredient_card_text_view);
            }
        }
    }

}
