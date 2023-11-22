package com.zybooks.mealswap20;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {
    EditText search;
    ImageView back_btn;
    RecyclerView recyclerView;
    List<User> recipes;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Find views
        search = findViewById(R.id.search);
        back_btn = findViewById(R.id.search_icon);
        recyclerView = findViewById(R.id.recyclerView);

        //Show and focus the keyboard
        search.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        //Get database
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                "db_name").allowMainThreadQueries().createFromAsset("database/recipe.db").build();
        UserDao userDao = db.userDao();

        //Get al recipes from database
        recipes = userDao.getAll();

        //set layout manager to recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Hide keyboard when recyclerView item clicked
        recyclerView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                inputMethodManager.hideSoftInputFromWindow(search.getWindowToken(), 0);
                return false;
            }
        });

        //Search from all recipes when Edittext data changed
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals("")){ //Search if new alphabet added
                    filter(s.toString());

            }
        });
    }

    //Filter the searched item from all recipes
    public void filter(String text) {
            List<User> filterList = new ArrayList<>();

            for(int i = 0; i < recipes.size(); i++) { //Loop for check searched item in recipe list
                if (recipes.get(i).getTittle().toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))) {
                    filterList.add(recipes.get(i));
                }
            }

            //Update search recyclerView with new items
            adapter.filterList(filterList);
        }
}
