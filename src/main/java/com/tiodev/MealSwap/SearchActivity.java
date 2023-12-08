package com.tiodev.MealSwap;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.tiodev.MealSwap.Adapter.SearchAdapter;
import com.tiodev.MealSwap.RoomDB.AppDatabase;
import com.tiodev.MealSwap.RoomDB.User;
import com.tiodev.MealSwap.RoomDB.UserDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 1000;

    private EditText search;
    private ImageView back_btn;
    private RecyclerView rcview;
    private List<User> dataPopular = new ArrayList<>();
    private SearchAdapter adapter;
    private List<User> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Find views
        search = findViewById(R.id.search);
        back_btn = findViewById(R.id.back_to_home);
        rcview = findViewById(R.id.rcview);

        // Show and focus the keyboard
        search.requestFocus();
        InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getSystemService(INPUT_METHOD_SERVICE));

        // Get database
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "db_name").allowMainThreadQueries()
                .createFromAsset("database/recipe.db")
                .build();
        UserDao userDao = db.userDao();

        // Get all recipes from the database
        recipes = userDao.getAll();

        // Filter the Popular category on activity start
        for (int i = 0; i < recipes.size(); i++) {
            if (recipes.get(i).getCategory().contains("Popular")) {
                dataPopular.add(recipes.get(i));
            }
        }

        // Set layout manager to recyclerView
        rcview.setLayoutManager(new LinearLayoutManager(this));

        // Hide keyboard when recyclerView item clicked
        rcview.setOnTouchListener((v, event) -> {
            imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
            return false;
        });

        // Set adapter to search recyclerView
        adapter = new SearchAdapter(dataPopular, getApplicationContext());
        rcview.setAdapter(adapter);

        // Search from all recipes when Edittext data changed
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Method required*
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Method required*
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) { // Search if new alphabet added
                    filter(s.toString());
                }
            }
        });

        // Exit activity
        back_btn.setOnClickListener(v -> {
            imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
            finish();
        });
    }

    // Filter the searched item from all recipes
    public void filter(String text) {
        List<User> filterList = new ArrayList<>();

        for (int i = 0; i < recipes.size(); i++) { // Loop for checking searched item in the recipe list
            CharSequence title = recipes.get(i).getTittle().toLowerCase(Locale.ROOT);
            if (((String) title).contains(text.toLowerCase(Locale.ROOT))) {
                filterList.add(recipes.get(i));
            }
        }

        // Update search recyclerView with new item
        adapter.filterList(filterList);
    }
}
