package com.tiodev.MealSwap.RoomDB;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipe")
public class User {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @NonNull
    public String img; // Image URL or URI

    @NonNull
    public String tittle; // Title of the recipe

    @NonNull
    public String des; // Description of the recipe

    @NonNull
    public String ing; // Ingredients of the recipe

    @NonNull
    public String category; // Category of the recipe

    public User(String img, String tittle, String des, String ing, String category) {
        this.img = img;
        this.tittle = tittle;
        this.des = des;
        this.ing = ing;
        this.category = category;
    }

    // Getters and Setters
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getIng() {
        return ing;
    }

    public void setIng(String ing) {
        this.ing = ing;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

