package algonquin.cst2335.androidfinalproj.currencyconverter.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import algonquin.cst2335.androidfinalproj.R;
import algonquin.cst2335.androidfinalproj.aviationtracker.AviationActivity;
import algonquin.cst2335.androidfinalproj.bearimages.BearActivity;
import algonquin.cst2335.androidfinalproj.triviadatabase.WelcomeActivity;
import algonquin.cst2335.androidfinalproj.currencyconverter.data.CurrencyConverterModel;
import algonquin.cst2335.androidfinalproj.currencyconverter.data.ResultDetailsModel;
import algonquin.cst2335.androidfinalproj.databinding.ActivityCurrencyConverterBinding;


public class CurrencyConverter extends AppCompatActivity {
    private CurrencyConverterModel model;
    private ActivityCurrencyConverterBinding variableBinding;

    protected RequestQueue queue = null;
    Boolean hasBeenConverted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setting the main view model
        model = new ViewModelProvider(this).get(CurrencyConverterModel.class);

        // Initialize the variableBinding using the generated binding class
        variableBinding = ActivityCurrencyConverterBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        //tool bar lol
        setSupportActionBar(variableBinding.toolbar);

        //volley queue
        queue = Volley.newRequestQueue(this);

        //creating intent for results page
        Intent resultsPage = new Intent(CurrencyConverter.this, ResultsPage.class);

        //shared preferences
        SharedPreferences pref = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();


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


            //getting all vars
            String oldC = variableBinding.currencyEnter.getText().toString();
            String newC = variableBinding.newCurrencyEnter.getText().toString();
            String amountToC = variableBinding.amountEnter.getText().toString();
            //temporary holder for new amount
            String temp = "new amount";
            String url = "";

            //verifying the data entered
            if(verifyData() == true ){

               //thing
                setConverted(true);

                //API key: 15b1ecf20ebd3f7f75267fe3bdc61e2d53a081df
                String key = "15b1ecf20ebd3f7f75267fe3bdc61e2d53a081df";

                //https://api.getgeoapi.com/v2/currency/convert?format=json&from=AUD&to=CAD&amount=1&api_key=YOUR_API_KEY&format=json

                //Must use a number in the JSON request to convert
                double num = Double.parseDouble(amountToC);

                //JSON Object things.
                url = "https://api.getgeoapi.com/v2/currency/convert?format=json&from=" + oldC+ "&to="+ newC +"&amount=" + num +"&api_key="+ key + "&format=json";

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        (response) ->{
                            Toast.makeText(this, "successful api things", Toast.LENGTH_SHORT).show();
                            try{
                                JSONObject ratesObject = response.getJSONObject("rates");
                                JSONObject mainObject = ratesObject.getJSONObject(newC);

                               String nA = mainObject.getString("rate_for_amount");

                                //String cA = String.valueOf(convertedAmount);
                                double amountToFormat = Double.parseDouble(nA);
                                String formattedResult = String.format("%.2f", amountToFormat);

                              variableBinding.result.setText(formattedResult);

                              //adding to shared prefs
                              editor.putString("NewAmount", formattedResult);
                              resultsPage.putExtra("NewAmount", formattedResult);
                              //editor.apply();

                            } catch(JSONException e){

                            }
                        },
                        (error) -> {
                            Toast.makeText(this, "bad api things", Toast.LENGTH_SHORT).show();
                        });
                queue.add(request);

                //shared preferences, putting the strings there
                editor.putString("Currency", oldC);
                editor.putString("Amount", amountToC);
                // editor.putString("NewAmount", temp);
                editor.putString("NewCurrency", newC);
                editor.apply();

                resultsPage.putExtra("Currency", variableBinding.currencyEnter.getText().toString());
                resultsPage.putExtra("Amount", variableBinding.amountEnter.getText().toString());
                //resultsPage.putExtra("NewAmount", temp);
                resultsPage.putExtra("NewCurrency", variableBinding.newCurrencyEnter.getText().toString());

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
        String amount = variableBinding.amountEnter.getText().toString();
        String newCurrency = variableBinding.newCurrencyEnter.getText().toString();

        if (currency.length() != 3){
            Toast.makeText(this, "Please enter a valid amount",  Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (amount.length() == 0){
            Toast.makeText(this, "Please enter a valid amount",  Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(newCurrency.length() != 3){
            Toast.makeText(this, "Please enter a valid amount",  Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }

    } //end of verifyData()

    //the menubar inflater
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.currency_menu_toolbar, menu);

        return true;
    }

    //da actual menu bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent bearPage = new Intent(CurrencyConverter.this, BearActivity.class);
        Intent triviaPage = new Intent(CurrencyConverter.this, WelcomeActivity.class);
        Intent aviationPage = new Intent(CurrencyConverter.this, AviationActivity.class);

        if (item.getItemId() == R.id.triv_time) {
            startActivity(triviaPage);
        }
        else if(item.getItemId() == R.id.avia_trac) {
            startActivity(aviationPage);
        }
        else if(item.getItemId() == R.id.bear_imag){
            startActivity(bearPage);
        }
        else if(item.getItemId() == R.id.action_help){

            //loading the fragment
            InstructionFragment resultFragment = new InstructionFragment();
            FragmentManager fMgr = getSupportFragmentManager();
            FragmentTransaction tx = fMgr.beginTransaction();
            tx.replace(R.id.fragmentLocation, resultFragment);
            tx.commit();
            tx.addToBackStack("");
        }

        return true;
    } //end of menu bar

}