package values.mainactivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import algonquin.cst2335.androidfinalproj.aviationtracker.AviationActivity;
import algonquin.cst2335.androidfinalproj.bearimages.BearActivity;
import algonquin.cst2335.androidfinalproj.currencyconverter.ui.CurrencyConverter;
import algonquin.cst2335.androidfinalproj.databinding.ActivityMainBinding;
import algonquin.cst2335.androidfinalproj.triviadatabase.WelcomeActivity;

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
        Intent triviaPage = new Intent(MainActivity.this, WelcomeActivity.class);
        Intent aviationPage = new Intent(MainActivity.this, AviationActivity.class);
        Intent bearPage = new Intent(MainActivity.this, BearActivity.class);

        //onclick listener for the currency button
        variableBinding.currencyBtn.setOnClickListener( clk -> {
            //moving to currencyConverter page
            startActivity(currencyPage);
        });

        //onclick listener for the trivia database question
        variableBinding.triviaBtn.setOnClickListener(clk -> {
            startActivity(triviaPage);
        });

        //onclick listener for the aviation images button
        variableBinding.aviationBtn.setOnClickListener(clk -> {
            startActivity(aviationPage);

        });

        //onclick Listener for the bear images button
        variableBinding.bearBtn.setOnClickListener(clk -> {
            startActivity(bearPage);
        });
    }
}
