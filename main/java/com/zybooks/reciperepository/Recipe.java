package com.zybooks.reciperepository;

import android.media.Image;
import com.squareup.picasso.Picasso;
public class Recipe {

    private String recipeName;
    private String[] recipeIngredients;
    private int[] portions;
    private String[] tags;
    private String imgUrl;

    public Recipe(String name, String[] ingredients, int[] amount, String mimg) {
        recipeName = name;
        recipeIngredients = ingredients;
        portions = amount;
        imgUrl = mimg;
    }

    public int[] getPortions() {
        return this.portions;
    }

    public void setPortions(int[] amount) {
        this.portions = amount;
    }

    public String getRecipeName() {
        return this.recipeName;
    }

    public void setRecipeName(String name) {
        this.recipeName = name;
    }

    public String[] getRecipeIngredients() {
        return this.recipeIngredients;
    }

    public void setRecipeIngredients(String[] ingredients) {
        this.recipeIngredients = ingredients;
    }

    public String getImg() {
        return this.imgUrl;
    }

    public void setImg(String mimg) {
        this.imgUrl = mimg;
    }
    public void setTags(String[] tag) {
        this.tags = tag;
    }

    public String[] getTags() {
        return this.tags;
    }

}
