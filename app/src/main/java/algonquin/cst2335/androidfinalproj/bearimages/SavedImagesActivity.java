package algonquin.cst2335.androidfinalproj.bearimages;// SavedImagesActivity.java

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.androidfinalproj.R;

public class SavedImagesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewSavedImages;
    private BearImageAdapter bearImageAdapter;
    private BearImageDatabase bearImageDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_images);

        recyclerViewSavedImages = findViewById(R.id.recyclerViewSavedImages);
        recyclerViewSavedImages.setLayoutManager(new LinearLayoutManager(this));

        // Get the database instance
        bearImageDatabase = BearImageDatabase.getInstance(this);

        // Fetch the saved images from the database
        List<BearImage> savedImages = (List<BearImage>) bearImageDatabase.bearImageDao().getAllBearImages();

        // Create and set the adapter for the RecyclerView
        bearImageAdapter = new BearImageAdapter(savedImages);
        recyclerViewSavedImages.setAdapter(bearImageAdapter);
    }
}
