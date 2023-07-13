package algonquin.cst2335.androidfinalproj.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.androidfinalproj.data.CurrencyConverterModel;
import algonquin.cst2335.androidfinalproj.databinding.ActivityCurrencyConverterBinding;


public class CurrencyConverter extends AppCompatActivity {
    private CurrencyConverterModel model;
    private ActivityCurrencyConverterBinding variableBinding;

    ArrayList<Result> results = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setting the main view model
        model = new ViewModelProvider(this).get(CurrencyConverterModel.class);

        // Initialize the variableBinding using the generated binding class
        variableBinding = ActivityCurrencyConverterBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        //creating intent for results page
        Intent resultsPage = new Intent(CurrencyConverter.this, ResultsPage.class);

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

            //verifying the data entered
            if(verifyData() == true ){
                //shared preferences, putting the strings there
               /* SharedPreferences.Editor editor = pref.edit();
                editor.putString("Currency", variableBinding.currencyEnter.getText().toString());
                editor.putString("Amount", variableBinding.amountEnter.getText().toString());
                editor.putString("NewCurrency", variableBinding.newCurrencyEnter.getText().toString());

                editor.apply();
               // adding the shared prefs to the next page?
               resultsPage.putExtra("Currency",variableBinding.currencyEnter.getText().toString());
                resultsPage.putExtra("Amount", variableBinding.amountEnter.getText().toString());
                resultsPage.putExtra("NewCurrency", variableBinding.newCurrencyEnter.getText().toString());
                */


                //going to resultsPage
                startActivity(resultsPage);
            }
            else {

            }



        });



    } //end of onCreate()

    public boolean verifyData(){

        String currency = variableBinding.currencyEnter.getText().toString();

        if (currency.length() > 3) {
            Toast.makeText(this, "Currency format: 3 Letters (ex. CAD, USD)",  Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (currency.length() == 0){
            Toast.makeText(this, "Currency format: 3 Letters (ex. CAD, USD)",  Toast.LENGTH_SHORT).show();
            return false;
        } else if (currency.length() < 3 ){
            Toast.makeText(this, "Currency format: 3 Letters (ex. CAD, USD)",  Toast.LENGTH_SHORT).show();
            return false;
        } else{
            return true;
        }

        //todo: Verify new Currency format
        //todo: verify number?
    } //end of verifyData()
}
