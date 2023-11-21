package com.zybooks.reciperepository;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;



public class MainActivity extends AppCompatActivity {

    private EditText recipeName;
    private EditText recipeIngredients;
    private EditText imageUrl;
    private EditText recipeAmount;
    private EditText img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    RecipeRepository reciRepo = new RecipeRepository();

    public void addRecipe() {
        recipeName = findViewById(R.id.recipe_name);
        recipeIngredients = findViewById(R.id.recipe_ingredients);
        imageUrl = findViewById(R.id.image_url);
        img = findViewById(R.id.image_url);
        recipeAmount = findViewById(R.id.amount_text);
        String ingredient = recipeIngredients.getText().toString();
        String[] amount = recipeAmount.toString().split(",");
        String[] ingredients = ingredient.split(",");
        int[] amounts = new int[amount.length];
        for (int i = 0; i < amount.length; i++) {
            amounts[i] = Integer.parseInt(amount[i]);
        }
        Recipe reci = new Recipe(recipeName.getText().toString(), ingredients,amounts, img.getText().toString());
        reciRepo.getRecipeList().add(reci);
    }
}