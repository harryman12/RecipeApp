package com.tiodev.MealSwap;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.tiodev.MealSwap.Adapter.AdapterPopular;
import com.tiodev.MealSwap.RoomDB.AppDatabase;
import com.tiodev.MealSwap.RoomDB.User;
import com.tiodev.MealSwap.RoomDB.UserDao;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    ImageView salad, main, drinks, dessert, menu;
    RecyclerView rcview_home;
    List<User> dataPopular = new ArrayList<>();
    EditText editText;
    Button addRecipeButton; // Button for adding new recipe

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize views
        salad = findViewById(R.id.salad);
        main = findViewById(R.id.MainDish);
        drinks = findViewById(R.id.Drinks);
        dessert = findViewById(R.id.Desserts);
        rcview_home = findViewById(R.id.rcview_popular);
        editText = findViewById(R.id.editText);
        menu = findViewById(R.id.imageView);
        addRecipeButton = findViewById(R.id.add_recipe_button); // Initialize the add recipe button

        // Set layout for recyclerView
        rcview_home.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Set popular recipes
        setPopularList();

        // Set onClick listeners
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        // Category buttons - start new activity with intent method "start"
        salad.setOnClickListener(v -> start("Salad", "Salad"));
        main.setOnClickListener(v -> start("Dish", "Main dish"));
        drinks.setOnClickListener(v -> start("Drinks", "Drinks"));
        dessert.setOnClickListener(v -> start("Desserts", "Dessert"));

        // Open search activity
        editText.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, SearchActivity.class)));

        // Open add recipe activity
        addRecipeButton.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, AddRecipeActivity.class)));
    }

    private void setPopularList() {
        // Get database
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "db_name").allowMainThreadQueries()
                .createFromAsset("database/recipe.db")
                .build();
        UserDao userDao = db.userDao();

        // Get all recipes from database and filter for the "Popular" category
        List<User> recipes = userDao.getAll();
        for (User recipe : recipes) {
            if (recipe.getCategory().contains("Popular")) {
                dataPopular.add(recipe);
            }
        }

        // Set popular list to adapter
        AdapterPopular adapter = new AdapterPopular(dataPopular, getApplicationContext());
        rcview_home.setAdapter(adapter);
    }

    private void start(String category, String title) {
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        intent.putExtra("Category", category);
        intent.putExtra("title", title);
        startActivity(intent);
    }
}
