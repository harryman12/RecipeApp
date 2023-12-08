package com.tiodev.MealSwap;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tiodev.MealSwap.Adapter.RecipeAdaptar;

import java.util.List;

public class ViewRecipesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper db;
    List<RecipeActivity> recipeList;
    RecipeAdaptar adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipes);

        recyclerView = findViewById(R.id.recyclerView);
        db = new DatabaseHelper(this);

        recipeList = db.getAllRecipes();
        adapter = new RecipeAdaptar(recipeList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
