package algonquin.cst2335.androidfinalproj.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import algonquin.cst2335.androidfinalproj.data.CurrencyConverterModel;
import algonquin.cst2335.androidfinalproj.databinding.ActivityCurrencyConverterBinding;


public class CurrencyConverter extends AppCompatActivity {
    private CurrencyConverterModel model;
    private ActivityCurrencyConverterBinding variableBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setting the main view model
        model = new ViewModelProvider(this).get(CurrencyConverterModel.class);

        // Initialize the variableBinding using the generated binding class
        variableBinding = ActivityCurrencyConverterBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        //creating intent for results page
        Intent resultsPage = new Intent(CurrencyConverter.this, Results.class);

        //shared preferences
        SharedPreferences pref = getSharedPreferences("MyData", Context.MODE_PRIVATE);

        String currency = pref.getString("Currency", "");
        String amount = pref.getString("Amount", "");
        String newCurrency = pref.getString("NewCurrency", "");

        variableBinding.currencyEnter.setText(currency);
        variableBinding.amountEnter.setText(amount);
        variableBinding.newCurrencyEnter.setText(newCurrency);

        //on click listener for converting
        variableBinding.convertBtn.setOnClickListener( clk ->{

            SharedPreferences.Editor editor = pref.edit();
            editor.putString("Currency", variableBinding.currencyEnter.getText().toString());
            editor.putString("Amount", variableBinding.amountEnter.getText().toString());
            editor.putString("NewCurrency", variableBinding.newCurrencyEnter.getText().toString());

            editor.apply();

            //going to resultsPage
            startActivity(resultsPage);

        });



    }
}
