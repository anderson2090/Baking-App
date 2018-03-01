package com.example.usama.bakingapp2;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.usama.bakingapp2.database.DBHandler;
import com.example.usama.bakingapp2.model.DBRecipe;

import java.util.ArrayList;


public class BakingAppWidgetProvider extends AppWidgetProvider {

    static int index = 0;


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        DBHandler dbHandler = new DBHandler(context, null, null, 1);
        ArrayList<DBRecipe> recipes = dbHandler.getRecipes();
        update(context, appWidgetManager, appWidgetIds,
                recipes.get(index).getDbRecipeName(),
                recipes.get(index).getDbRecipeIngredients());
    }

    public void update(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds,
                       String recipeName, String ingredients) {
        for (int appWidgetId : appWidgetIds) {


            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

            remoteViews.setTextViewText(R.id.widget_recipe_name, recipeName);
            remoteViews.setTextViewText(R.id.widget_ingredients_text_view, ingredients);
            Intent nextIntent = new Intent(context, BakingAppWidgetProvider.class);
            nextIntent.setAction("nextIntent");

            Intent previouslyIntent = new Intent(context, BakingAppWidgetProvider.class);
            previouslyIntent.setAction("previousAction");

            PendingIntent nexrPendingIntent = PendingIntent.getBroadcast(context, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent previousPendingIntent = PendingIntent.getBroadcast(context, 0, previouslyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widget_next_button, nexrPendingIntent);
            remoteViews.setOnClickPendingIntent(R.id.widget_previous_button, previousPendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        DBHandler dbHandler = new DBHandler(context, null, null, 1);
        ArrayList<DBRecipe> recipes = dbHandler.getRecipes();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetsId = appWidgetManager.getAppWidgetIds(new ComponentName(context, BakingAppWidgetProvider.class));

        if (intent.getAction().equals("nextIntent")) {
            if (index != recipes.size() - 1) {
                index++;

                update(context, appWidgetManager, appWidgetsId,
                        recipes.get(index).getDbRecipeName(),
                        recipes.get(index).getDbRecipeIngredients());
            }
        } else if (intent.getAction().equals("previousAction")) {
            if (index != 0) {
                index--;
                update(context, appWidgetManager, appWidgetsId,
                        recipes.get(index).getDbRecipeName(),
                        recipes.get(index).getDbRecipeIngredients());
            }
        }


        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {

    }

    @Override
    public void onDisabled(Context context) {

    }
}

