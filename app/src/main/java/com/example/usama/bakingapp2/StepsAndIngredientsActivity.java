package com.example.usama.bakingapp2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.usama.bakingapp2.fragments.IngredientsFragment;
import com.example.usama.bakingapp2.fragments.StepsFragment;

public class StepsAndIngredientsActivity extends RootActivity {

    final String STEPS_FRAGMENT = "STEPS FRAGMENT";
    final String INGREDIENTS_FRAGMENT = "INGREDIENTS FRAGMENT";
    final String CURRENT_FRAGMENT = "CURRENT FRAGMENT";
    static int fragmentIndex = 0;
    Fragment[] fragments = {new StepsFragment(), new IngredientsFragment()};
    FragmentManager fragmentManager;
    AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_and_ingredients);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragmentManager = getFragmentManager();
        addFragment(fragments[fragmentIndex], STEPS_FRAGMENT);


        bottomNavigation = findViewById(R.id.bottom_navigation);

        AHBottomNavigationItem steps = new AHBottomNavigationItem(R.string.steps, R.drawable.ic_stairs, R.color.colorAccent);
        AHBottomNavigationItem ingredients = new AHBottomNavigationItem(R.string.ingredients, R.drawable.ic_harvest, R.color.colorAccent);

        bottomNavigation.addItem(steps);
        bottomNavigation.addItem(ingredients);
        bottomNavigation.setCurrentItem(fragmentIndex);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                if (position == 0) {
                    addFragment(fragments[position], STEPS_FRAGMENT);


                } else if (position == 1) {
                    addFragment(fragments[position], INGREDIENTS_FRAGMENT);

                }
                fragmentIndex = position;
                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_FRAGMENT, fragmentIndex);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            fragmentIndex = savedInstanceState.getInt(CURRENT_FRAGMENT);
        }
    }


    public void addFragment(Fragment fragment, String tag) {

        if (fragmentManager.findFragmentByTag(tag) == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.steps_and_ingredient_root_layout, fragment)
                    .commit();
        }
    }


}
