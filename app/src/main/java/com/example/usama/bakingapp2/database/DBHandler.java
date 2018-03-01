package com.example.usama.bakingapp2.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.usama.bakingapp2.model.DBRecipe;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "recipes.db";
    public static final String TABLE_RECIPES = "recipes";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_RECIPE_NAME = "recipe_name";
    public static final String COLUMN_INGREDIENTS = "ingredients";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_RECIPES_TABLE_QUERY = "CREATE TABLE " + TABLE_RECIPES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_RECIPE_NAME + " TEXT, " +
                COLUMN_INGREDIENTS + " TEXT" +
                ")";

        sqLiteDatabase.execSQL(CREATE_RECIPES_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        onCreate(sqLiteDatabase);

    }

    public void addRecipe(String recipeName, StringBuilder ingredients) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RECIPE_NAME, recipeName);
        contentValues.put(COLUMN_INGREDIENTS, ingredients.toString());
        SQLiteDatabase database = this.getWritableDatabase();
        database.insert(TABLE_RECIPES, null, contentValues);
        database.close();
    }
    public boolean tableIsEmpty() {
        SQLiteDatabase database = this.getWritableDatabase();
        String count = "SELECT count(*) FROM " + TABLE_RECIPES;
        Cursor cursor = database.rawQuery(count, null);
        cursor.moveToFirst();
        int icount = cursor.getInt(0);
        cursor.close();
        return icount > 0;


    }

    public ArrayList<DBRecipe> getRecipes() {
        String query = "SELECT * FROM " + TABLE_RECIPES;
        SQLiteDatabase database = this.getWritableDatabase();

        ArrayList<DBRecipe> dbRecipes = new ArrayList<>();

        try {
            Cursor cursor = database.rawQuery(query, null);
            try {
                if (cursor.moveToFirst()) {
                    do {
                        String recipeName = cursor.getString(1);
                        String recipeIngredients = cursor.getString(2);

                        DBRecipe dbRecipe = new DBRecipe(recipeName, recipeIngredients);

                        dbRecipes.add(dbRecipe);
                    } while (cursor.moveToNext());
                }
            } finally {
                try {
                    cursor.close();
                } catch (Exception ignore) {
                }
            }
        } finally {
            try {
                database.close();
            } catch (Exception ignore) {
            }
        }

        return dbRecipes;
    }
}
