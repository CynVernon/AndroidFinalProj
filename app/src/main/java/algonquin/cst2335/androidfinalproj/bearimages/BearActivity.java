package algonquin.cst2335.androidfinalproj.bearimages;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import algonquin.cst2335.androidfinalproj.databinding.BearActivityBinding;

public class BearActivity extends AppCompatActivity {

    private BearActivityBinding binding;
    private BearModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(this).get(BearModel.class);

        binding = BearActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }
}
