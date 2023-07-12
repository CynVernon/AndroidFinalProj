package algonquin.cst2335.androidfinalproj.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import algonquin.cst2335.androidfinalproj.R;
import algonquin.cst2335.androidfinalproj.data.MainViewModel;
import algonquin.cst2335.androidfinalproj.databinding.ActivityCurrencyConverterBinding;


public class CurrencyConverter extends AppCompatActivity {
    private MainViewModel model;
    private ActivityCurrencyConverterBinding variableBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = new ViewModelProvider(this).get(MainViewModel.class);
        // Initialize the variableBinding using the generated binding class
        variableBinding = ActivityCurrencyConverterBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

    }
}
