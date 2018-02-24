package com.example.usama.bakingapp2.fragments;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.usama.bakingapp2.BakingApp;
import com.example.usama.bakingapp2.R;
import com.example.usama.bakingapp2.model.Step;

import java.util.ArrayList;

public class StepsFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    Parcelable listState;
    final String LIST_STATE = "LIST STATE";
    int currentRecipeIndex;
    BakingApp bakingApp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.steps_fragment, container, false);

        Bundle arguments = getArguments();
        currentRecipeIndex = arguments.getInt("currentRecipe");
        bakingApp = (BakingApp) getActivity().getApplication();

        recyclerView = view.findViewById(R.id.steps_recycler_view);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerAdapter = new RecyclerAdapter(currentRecipeIndex);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);




        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            listState = savedInstanceState.getParcelable(LIST_STATE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        listState = layoutManager.onSaveInstanceState();
        outState.putParcelable(LIST_STATE, listState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (listState != null) {
            layoutManager.onRestoreInstanceState(listState);
        }
    }

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


        int currentRecipeIndex;
        ArrayList<Step> steps = new ArrayList<>();

        public RecyclerAdapter(int currentRecipeIndex) {

            this.currentRecipeIndex = currentRecipeIndex;
            this.steps = bakingApp.getRecipes().get(currentRecipeIndex).getSteps();
        }

        @Override
        public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.step_card, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
            holder.stepCardTextView.setText(steps.get(position).getShortDescription());
        }

        @Override
        public int getItemCount() {
            return steps.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView stepCardTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                stepCardTextView = itemView.findViewById(R.id.step_card_text_view);
            }
        }
    }

}


