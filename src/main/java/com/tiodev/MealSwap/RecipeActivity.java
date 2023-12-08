package com.tiodev.MealSwap;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Arrays;

public class RecipeActivity extends AppCompatActivity {

    private ImageView img, backBtn, overlay, zoomImage;
    private TextView txt, ing, time, steps;
    private String[] ingList;
    private Button stepBtn, ingBtn;
    private boolean isImgCrop = false;
    private ScrollView scrollView, scrollViewStep;
    private int id;
    private String recipe;
    private String name;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        // Initialize views
        initializeViews();

        // Load recipe details
        loadRecipeDetails();

        // Set up button click listeners
        setUpButtonClickListeners();
    }

    private void initializeViews() {
        img = findViewById(R.id.recipe_img);
        txt = findViewById(R.id.tittle);
        ing = findViewById(R.id.ing);
        time = findViewById(R.id.time);
        stepBtn = findViewById(R.id.steps_btn);
        ingBtn = findViewById(R.id.ing_btn);
        backBtn = findViewById(R.id.back_btn);
        steps = findViewById(R.id.steps_txt);
        scrollView = findViewById(R.id.ing_scroll);
        scrollViewStep = findViewById(R.id.steps);
        overlay = findViewById(R.id.image_gradient);
        zoomImage = findViewById(R.id.zoom_image);
    }

    @SuppressLint("SetTextI18n")
    private void loadRecipeDetails() {
        // Load recipe image from link
        try {
            Glide.with(this).load(getIntent().getStringExtra("img")).into(img);
        } catch (Exception e) {
            // Handle Glide loading error
            e.printStackTrace();
        }

        String imageUri = getIntent().getStringExtra("img");
        if (imageUri != null && !imageUri.isEmpty()) {
            Glide.with(this)
                    .load(imageUri)
                    .error(R.drawable.error) // Set your error image here
                    .into(img);
        } else {
            img.setImageResource(R.drawable.error); // Set error image directly if URI is null/empty
        }

        // Set recipe title
        txt.setText(getIntent().getStringExtra("tittle"));

        // Set recipe ingredients
        ingList = getIntent().getStringExtra("ing").split("\n");
        // Set time
        time.setText(ingList[0]);

        // Join ingredients using emoji
        ing.setText("\uD83D\uDFE2  " + String.join("\n\uD83D\uDFE2  ", Arrays.copyOfRange(ingList, 1, ingList.length)));

        // Set recipe steps
        steps.setText(getIntent().getStringExtra("des"));
    }

    private void setUpButtonClickListeners() {
        ingBtn.setOnClickListener(v -> {
            setButtonStyle(ingBtn, stepBtn, scrollView, scrollViewStep);
        });

        stepBtn.setOnClickListener(v -> {
            setButtonStyle(ingBtn, stepBtn, scrollViewStep, scrollView);
        });

        zoomImage.setOnClickListener(view -> {
            toggleImageZoom();
        });

        backBtn.setOnClickListener(v -> finish());
    }

    private void setButtonStyle(Button selectedButton, Button otherButton, ScrollView selectedScrollView, ScrollView otherScrollView) {
        selectedButton.setBackgroundResource(R.drawable.btn_ing);
        selectedButton.setTextColor(getColor(R.color.orange_1000));
        otherButton.setBackground(null);
        otherButton.setTextColor(getColor(R.color.black));

        selectedScrollView.setVisibility(View.VISIBLE);
        otherScrollView.setVisibility(View.GONE);
    }

    private void toggleImageZoom() {
        if (!isImgCrop) {
            setImageScaleType(ImageView.ScaleType.FIT_CENTER, 0);
            isImgCrop = true;
        } else {
            setImageScaleType(ImageView.ScaleType.CENTER_CROP, 255);
            isImgCrop = false;
        }
    }

    private void setImageScaleType(ImageView.ScaleType scaleType, int alpha) {
        img.setScaleType(scaleType);
        overlay.setImageAlpha(alpha);
        try {
            Glide.with(this).load(getIntent().getStringExtra("img")).into(img);
        } catch (Exception e) {
            // Handle Glide loading error
            e.printStackTrace();
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public String getRecipe() {
        return recipe;
    }
}
