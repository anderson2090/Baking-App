package com.example.usama.bakingapp2;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.usama.bakingapp2.fragments.IngredientsFragment;
import com.example.usama.bakingapp2.fragments.StepsFragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class StepsAndIngredientsActivity extends RootActivity {


    final String CURRENT_FRAGMENT = "CURRENT FRAGMENT";
    final String STEPS_FRAGMENT_TAG = "STEPS FRAGMENT";
    final String INGREDIENTS_FRAGMENT_TAG = "INGREDIENTS FRAGMENT";
    static int fragmentIndex = 0;
    StepsFragment stepsFragment = new StepsFragment();
    IngredientsFragment ingredientsFragment = new IngredientsFragment();

    LinkedHashMap<String, Fragment> fragmentLinkedHashMap = new LinkedHashMap<>();
    FragmentManager fragmentManager;
    AHBottomNavigation bottomNavigation;
    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_and_ingredients);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        fragmentLinkedHashMap.put(STEPS_FRAGMENT_TAG, stepsFragment);
        fragmentLinkedHashMap.put(INGREDIENTS_FRAGMENT_TAG, ingredientsFragment);
        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();


        if (savedInstanceState != null) {
            fragmentIndex = savedInstanceState.getInt(CURRENT_FRAGMENT);
            currentFragment = fragmentManager.getFragment(savedInstanceState,
                    new ArrayList<>(fragmentLinkedHashMap.keySet()).get(fragmentIndex));

        } else {
            currentFragment = new ArrayList<>(fragmentLinkedHashMap.values()).get(fragmentIndex);
            addFragment(currentFragment,
                    new ArrayList<>(fragmentLinkedHashMap.keySet()).get(fragmentIndex));
        }

        bottomNavigation = findViewById(R.id.bottom_navigation);

        AHBottomNavigationItem steps = new AHBottomNavigationItem(R.string.steps, R.drawable.ic_stairs, R.color.colorAccent);
        AHBottomNavigationItem ingredients = new AHBottomNavigationItem(R.string.ingredients, R.drawable.ic_harvest, R.color.colorAccent);

        bottomNavigation.addItem(steps);
        bottomNavigation.addItem(ingredients);
        bottomNavigation.setCurrentItem(fragmentIndex);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {


                addFragment(new ArrayList<>(fragmentLinkedHashMap.values()).get(position),
                        new ArrayList<>(fragmentLinkedHashMap.keySet()).get(position));

                fragmentIndex = position;
                return true;
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_FRAGMENT, fragmentIndex);
        currentFragment = new ArrayList<>(fragmentLinkedHashMap.values()).get(fragmentIndex);
        if (new ArrayList<>(fragmentLinkedHashMap.values()).get(fragmentIndex).isAdded()) {
            fragmentManager.putFragment(outState,
                    new ArrayList<>(fragmentLinkedHashMap.keySet()).get(fragmentIndex),
                    currentFragment);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            fragmentIndex = savedInstanceState.getInt(CURRENT_FRAGMENT);
        }
    }


    public void addFragment(Fragment fragment, String tag) {
        if ((getIntent().getExtras()) != null) {
            fragment.setArguments(getIntent().getExtras());
        }
        if (fragmentManager.findFragmentByTag(tag) == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.steps_and_ingredient_root_layout, fragment)
                    .commit();
        }
    }


}
