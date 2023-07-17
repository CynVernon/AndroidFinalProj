package algonquin.cst2335.androidfinalproj.mainactivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import algonquin.cst2335.androidfinalproj.currencyconverter.ui.CurrencyConverter;
import algonquin.cst2335.androidfinalproj.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private MainViewModel model;
    private ActivityMainBinding variableBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting the view model
        model = new ViewModelProvider(this).get(MainViewModel.class);

        //setting the variable binding
        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        // Creating the intent setting
        Intent currencyPage = new Intent(MainActivity.this, CurrencyConverter.class);


        //onclick listener for the currency button
        variableBinding.currencyBtn.setOnClickListener( clk -> {
            //moving to currencyConverter page
            startActivity(currencyPage);

        });

    }
}
