// BearActivity.java
package algonquin.cst2335.androidfinalproj.bearimages;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import algonquin.cst2335.androidfinalproj.databinding.BearActivityBinding;

public class BearActivity extends AppCompatActivity {

    private BearActivityBinding binding;
    private BearModel model;
    private BearImageDatabase bearImageDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(this).get(BearModel.class);

        binding = BearActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get the database instance
        bearImageDatabase = BearImageDatabase.getInstance(this);

        binding.generateImageBtn.setOnClickListener(v -> generateAndSaveImage());
        binding.showSavedImagesBtn.setOnClickListener(v -> showSavedImages());
    }

    private void generateAndSaveImage() {
        String width = binding.editWidth.getText().toString();
        String height = binding.editHeight.getText().toString();

        // Check if the inputs are valid
        if (width.isEmpty() || height.isEmpty()) {
            Toast.makeText(this, "Please enter valid width and height.", Toast.LENGTH_SHORT).show();
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
        BearImageDatabase bearImageEntity = new BearImageDatabase(imageWidth, imageHeight, imageUrl) {
            @NonNull
            @Override
            protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
                return null;
            }

            @NonNull
            @Override
            protected InvalidationTracker createInvalidationTracker() {
                return null;
            }

            @Override
            public void clearAllTables() {

            }

            @Override
            public BearImageDao bearImageDao() {
                return null;
            }
        };
        model.insertBearImage(bearImageEntity);
    }

    private void saveUserInput(int imageWidth, int imageHeight) {
        SharedPreferences sharedPreferences = getSharedPreferences("BearPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("imageWidth", imageWidth);
        editor.putInt("imageHeight", imageHeight);
        editor.apply();
    }

    private void showSavedImages() {
        // Start the SavedImagesActivity to display the list of saved images.
        // You can use a new activity, fragment, or dialog to display the list.
        // Fetch the saved images from the database and pass them to the SavedImagesActivity.
        // The SavedImagesActivity should use BearImageAdapter to display the images.
        // You can also implement the logic to delete images from the database in the SavedImagesActivity.
    }
}
