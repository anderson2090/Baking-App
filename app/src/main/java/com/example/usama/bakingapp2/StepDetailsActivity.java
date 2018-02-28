package com.example.usama.bakingapp2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.usama.bakingapp2.fragments.StepDetailsFragment;

public class StepDetailsActivity extends AppCompatActivity {

    final String STEP_DETAILS_FRAGMENT_TAG = "STEP DETAILS FRAGMENT";
    StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState != null) {
            stepDetailsFragment = (StepDetailsFragment) getSupportFragmentManager().getFragment(savedInstanceState, "stepDetailsFrag");
        } else {


            stepDetailsFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_details_activity_root_layout, stepDetailsFragment, STEP_DETAILS_FRAGMENT_TAG)
                    .commit();
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "stepDetailsFrag", stepDetailsFragment);
    }
}
