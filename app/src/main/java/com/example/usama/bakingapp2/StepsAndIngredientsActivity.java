package com.example.usama.bakingapp2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.usama.bakingapp2.fragments.IngredientsFragment;
import com.example.usama.bakingapp2.fragments.StepsFragment;

public class StepsAndIngredientsActivity extends RootActivity {

    final String STEPS_FRAGMENT = "STEPS FRAGMENT";
    final String INGREDIENTS_FRAGMENT = "INGREDIENTS FRAGMENT";
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_and_ingredients);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragmentManager = getFragmentManager();
        addFragment(new StepsFragment(), STEPS_FRAGMENT);


        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

        AHBottomNavigationItem steps = new AHBottomNavigationItem(R.string.steps, R.drawable.ic_stairs, R.color.colorAccent);
        AHBottomNavigationItem ingredients = new AHBottomNavigationItem(R.string.ingredients, R.drawable.ic_harvest, R.color.colorAccent);

        bottomNavigation.addItem(steps);
        bottomNavigation.addItem(ingredients);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (position == 0) {
                    addFragment(new StepsFragment(), STEPS_FRAGMENT);


                } else if (position == 1) {
                    addFragment(new IngredientsFragment(), INGREDIENTS_FRAGMENT);

                }
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

    public void addFragment(Fragment fragment, String tag) {

        if (fragmentManager.findFragmentByTag(tag) == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.steps_and_ingredient_root_layout, fragment)
                    .commit();
        }
    }


}
