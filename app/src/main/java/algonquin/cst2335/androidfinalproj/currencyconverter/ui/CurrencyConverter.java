package algonquin.cst2335.androidfinalproj.currencyconverter.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import algonquin.cst2335.androidfinalproj.R;
import algonquin.cst2335.androidfinalproj.currencyconverter.data.CurrencyConverterModel;
import algonquin.cst2335.androidfinalproj.currencyconverter.data.ResultDetailsModel;
import algonquin.cst2335.androidfinalproj.databinding.ActivityCurrencyConverterBinding;
import algonquin.cst2335.androidfinalproj.currencyconverter.ui.ResultDetailsFragment;


public class CurrencyConverter extends AppCompatActivity {
    private CurrencyConverterModel model;
    private ActivityCurrencyConverterBinding variableBinding;

    protected RequestQueue queue = null;

    ArrayList<Result> results = new ArrayList<>();

    Boolean hasBeenConverted = false;

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
        String newAmount = pref.getString("NewAmount", "");
        String newCurrency = pref.getString("NewCurrency", "");

        variableBinding.currencyEnter.setText(currency);
        variableBinding.amountEnter.setText(amount);
        variableBinding.newCurrencyEnter.setText(newCurrency);


        //onclick listener for viewing history
        variableBinding.historyBtn.setOnClickListener(clk -> {
            if(hasBeenConverted() == true) {

                resultsPage.putExtra("Converted", true);

                setConverted(false);
            }
            else if (hasBeenConverted() == false) {

                resultsPage.putExtra("Converted", false);

            }

            startActivity(resultsPage);

        });

        //on click listener for converting
        variableBinding.convertBtn.setOnClickListener( clk ->{

            //temporary holder for new amount
            String temp = "new amount";

            //verifying the data entered
            if(verifyData() == true ){
                //shared preferences, putting the strings there
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("Currency", variableBinding.currencyEnter.getText().toString());
                editor.putString("Amount", variableBinding.amountEnter.getText().toString());
                editor.putString("NewAmount", temp);
                editor.putString("NewCurrency", variableBinding.newCurrencyEnter.getText().toString());
               editor.apply();

                resultsPage.putExtra("Currency", variableBinding.currencyEnter.getText().toString());
                resultsPage.putExtra("Amount", variableBinding.amountEnter.getText().toString());
                resultsPage.putExtra("NewAmount", temp);
                resultsPage.putExtra("NewCurrency", variableBinding.newCurrencyEnter.getText().toString());

               //thing
                setConverted(true);

                //viewmodel for fragment?
                ResultDetailsModel fragment = new ResultDetailsModel();
                //fragment.setViewModel(model);

                //loading the fragment
                ResultDetailsFragment resultFragment = new ResultDetailsFragment();
                FragmentManager fMgr = getSupportFragmentManager();
                FragmentTransaction tx = fMgr.beginTransaction();
                tx.replace(R.id.fragmentLocation, resultFragment);
                tx.commit();
                tx.addToBackStack("");
            }
            else {

            }

        });

    } //end of onCreate()

    public void setConverted(Boolean answer){
        this.hasBeenConverted = answer;
    }

    public boolean hasBeenConverted(){
        return hasBeenConverted;
    };

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
