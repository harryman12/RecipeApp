package com.zybooks.reciperepository;

import java.util.ArrayList;
import java.util.List;

public class RecipeRepository {

    private ArrayList<Recipe> recipeList;

    public ArrayList<Recipe> getRecipeList() {
        return this.recipeList;
    }

    public void AddRecipe(Recipe recipe) {
        recipeList.add(recipe);
    }
    public Recipe getRecipe(String name) {
        for(Recipe recipe : recipeList) {
            if (recipe.getRecipeName() == name) {
                return recipe;
            }
        }
        return null;
    }

    public Recipe[] getRecipeByTag(String tag) {
        List<Recipe> match = null;
        for (Recipe recipe : recipeList) {
            for (String string : recipe.getTags()) {
                if (tag == string && !match.contains(recipe)) {
                    match.add(recipe);
                }
            }
        }
        Recipe[] all = match.toArray(new Recipe[0]);
        return all;
    }
}
