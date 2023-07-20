package algonquin.cst2335.androidfinalproj.aviationtracker;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import algonquin.cst2335.androidfinalproj.databinding.AviationActivityBinding;

public class AviationActivity extends AppCompatActivity {
    private AviationModel model;

    private AviationActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(this).get(AviationModel.class);

        binding = AviationActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}
