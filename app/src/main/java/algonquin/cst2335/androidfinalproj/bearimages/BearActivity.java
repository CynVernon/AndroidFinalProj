package algonquin.cst2335.androidfinalproj.bearimages;// BearActivity.java

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import algonquin.cst2335.androidfinalproj.R;
import algonquin.cst2335.androidfinalproj.bearimages.db.BearImageDatabase;
import algonquin.cst2335.androidfinalproj.databinding.BearActivityBinding;

public class BearActivity extends AppCompatActivity {

    private BearActivityBinding binding;
    private BearModel model;
    private EditText editWidth, editHeight;
    private Button generateImageBtn, showSavedImagesBtn;
    private BearImageDatabase bearImageDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(this).get(BearModel.class);

        binding = BearActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize views
        editWidth = findViewById(R.id.editWidth);
        editHeight = findViewById(R.id.editHeight);
        generateImageBtn = findViewById(R.id.generateImageBtn);
        showSavedImagesBtn = findViewById(R.id.showSavedImagesBtn);

        // Get the database instance
        bearImageDatabase = BearImageDatabase.getInstance(this);

        // Set an onClickListener for the Generate Image button
        generateImageBtn.setOnClickListener(v -> generateAndSaveImage());

        // Set an onClickListener for the Show Saved Images button
        showSavedImagesBtn.setOnClickListener(v -> showSavedImages());
    }

    private void generateAndSaveImage() {
        String width = editWidth.getText().toString();
        String height = editHeight.getText().toString();

        // Check if the inputs are valid
        if (width.isEmpty() || height.isEmpty()) {
            // Handle empty input, show a toast message, etc.
            return;
        }

        // Convert the width and height to integers
        int imageWidth = Integer.parseInt(width);
        int imageHeight = Integer.parseInt(height);

        // Save the user's input in SharedPreferences for the next time
        saveUserInput(imageWidth, imageHeight);

        // Generate the image URL
        String imageUrl = "https://placebear.com/" + imageWidth + "/" + imageHeight;

        // Save the image URL to the database
        bearImageDatabase.bearImageDao().insertBearImage(imageUrl);
    }

    private void saveUserInput(int imageWidth, int imageHeight) {
        SharedPreferences sharedPreferences = getSharedPreferences("BearPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("imageWidth", imageWidth);
        editor.putInt("imageHeight", imageHeight);
        editor.apply();
    }

    private void showSavedImages() {
        // Implement the logic to show the list of saved images.
        // You can use a new activity, fragment, or dialog to display the list.
        // Fetch the saved images from the database and show them to the user.
    }
}
