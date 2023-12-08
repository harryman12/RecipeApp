package com.tiodev.MealSwap;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.tiodev.MealSwap.RoomDB.AppDatabase;
import com.tiodev.MealSwap.RoomDB.User;

public class AddRecipeActivity extends AppCompatActivity {

    EditText editTextName, editTextRecipe, editTextIngredients, editTextSteps;
    Button buttonAdd, buttonSelectImage;
    ImageView imageViewRecipe;
    String imageUri = "default_image_url";
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 2;
    Spinner spinnerCategory;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        editTextName = findViewById(R.id.editTextName);
        editTextRecipe = findViewById(R.id.editTextRecipe);
        editTextIngredients = findViewById(R.id.editTextIngredients);
        editTextSteps = findViewById(R.id.editTextSteps);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonSelectImage = findViewById(R.id.buttonSelectImage);
        imageViewRecipe = findViewById(R.id.imageViewRecipe);

        spinnerCategory = findViewById(R.id.spinnerCategory);
        adapter = ArrayAdapter.createFromResource(this, R.array.food_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        buttonSelectImage.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_READ_EXTERNAL_STORAGE);
            } else {
                openImageSelector();
            }
        });

        buttonAdd.setOnClickListener(v -> {
            String category = spinnerCategory.getSelectedItem().toString();
            String name = editTextName.getText().toString().trim();
            String recipeDescription = editTextRecipe.getText().toString().trim();
            String ingredients = editTextIngredients.getText().toString().trim();
            String steps = editTextSteps.getText().toString().trim();

            if (!name.isEmpty() && !recipeDescription.isEmpty() && !ingredients.isEmpty() && !steps.isEmpty()) {
                User newUser = new User(imageUri, name, recipeDescription, ingredients, steps);

                AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "db_name").allowMainThreadQueries().build();
                db.userDao().insertAll(newUser);

                Toast.makeText(AddRecipeActivity.this, "Recipe Added", Toast.LENGTH_SHORT).show();

                editTextName.setText("");
                editTextRecipe.setText("");
                editTextIngredients.setText("");
                editTextSteps.setText("");
                imageViewRecipe.setImageResource(android.R.color.transparent);
                imageUri = "default_image_url";

                Intent intent = new Intent(AddRecipeActivity.this, RecipeActivity.class);
                intent.putExtra("img", imageUri);
                intent.putExtra("title", name);
                intent.putExtra("ing", "Time: " + recipeDescription + "\n" + ingredients);
                intent.putExtra("des", steps);
                intent.putExtra("category", category);
                startActivity(intent);
            } else {
                Toast.makeText(AddRecipeActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openImageSelector() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImageSelector();
            } else {
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            imageViewRecipe.setImageURI(selectedImageUri);
            imageUri = selectedImageUri.toString();
            Log.d("AddRecipeActivity", "Selected Image URI: " + imageUri);
        }
    }
}

