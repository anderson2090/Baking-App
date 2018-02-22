package com.example.usama.bakingapp2.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.usama.bakingapp2.R;

import java.util.ArrayList;

public class StepsFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.steps_fragment, container, false);
        recyclerView = view.findViewById(R.id.steps_recycler_view);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerAdapter = new RecyclerAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);


        return view;

    }

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

        ArrayList<String> steps = new ArrayList<>();

        public RecyclerAdapter() {
            for (int i = 0; i <= 100; i++) {
                steps.add("Step " + i);
            }
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
            holder.stepCardTextView.setText(steps.get(position));
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


