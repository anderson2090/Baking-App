package com.example.usama.bakingapp2.model;



public class DBRecipe {

    private String dbRecipeName;
    private String dbRecipeIngredients;

    public DBRecipe() {
    }
    public DBRecipe(String dbRecipeName, String dbRecipeIngredients) {
        this.dbRecipeName = dbRecipeName;
        this.dbRecipeIngredients = dbRecipeIngredients;
    }
    public String getDbRecipeName() {
        return dbRecipeName;
    }

    public void setDbRecipeName(String dbRecipeName) {
        this.dbRecipeName = dbRecipeName;
    }

    public String getDbRecipeIngredients() {
        return dbRecipeIngredients;
    }

    public void setDbRecipeIngredients(String dbRecipeIngredients) {
        this.dbRecipeIngredients = dbRecipeIngredients;
    }
}
