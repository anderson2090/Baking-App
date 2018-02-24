package com.example.usama.bakingapp2.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.usama.bakingapp2.BakingApp;
import com.example.usama.bakingapp2.R;
import com.example.usama.bakingapp2.model.Recipe;
import com.example.usama.bakingapp2.model.Step;

import java.util.ArrayList;
import java.util.List;

public class StepDetailsFragment extends Fragment {

    BakingApp bakingApp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_details_fragment, container, false);

        Step currentStep = getArguments().getParcelable("currentStep");

        TextView stepTextView = view.findViewById(R.id.current_step_text_view);
        stepTextView.setText(currentStep.getShortDescription());

        return view;
    }
}
