package algonquin.cst2335.androidfinalproj.currencyconverter.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import algonquin.cst2335.androidfinalproj.R;
import algonquin.cst2335.androidfinalproj.aviationtracker.AviationActivity;
import algonquin.cst2335.androidfinalproj.bearimages.BearActivity;
import algonquin.cst2335.androidfinalproj.triviadatabase.WelcomeActivity;
import algonquin.cst2335.androidfinalproj.currencyconverter.data.CurrencyConverterModel;
import algonquin.cst2335.androidfinalproj.databinding.ActivityCurrencyConverterBinding;

/**
 * This page contains the main interactive screen for the currency converter. On it, the user can enter a decimal amount and the currency
 * it's currently in, and the currency they'd like to convert to. When the user presses the convert button, the listener uses an API and
 * a volley request to get a JSON object of the new converted amount. The user can also press the history button which will take them to
 * ResultsPage for their conversion history.
 *
 * @author Cyn Vernon
 * @version 1.0
 */
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

        //setting the toolbar
        setSupportActionBar(variableBinding.toolbar);

        //establishing the volley queue
        queue = Volley.newRequestQueue(this);

        //creating intent for results page
        Intent resultsPage = new Intent(CurrencyConverter.this, ResultsPage.class);

        //shared preferences
        SharedPreferences pref = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        //loading the options from shared prefs
        String currency = pref.getString("Currency", "");
        String amount = pref.getString("Amount", "");
        String newCurrency = pref.getString("NewCurrency", "");

        //setting the last entered amounts/values
        variableBinding.currencyEnter.setText(currency);
        variableBinding.amountEnter.setText(amount);
        variableBinding.newCurrencyEnter.setText(newCurrency);

        //onclick listener for viewing history. If statement to prevent duplicates from constantly being entered
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

            //empty string that will be used for the URL
            String url = "";

            //verifying the data entered before moving activities
            if(verifyData() == true ){

               //setting the converted boolean to true
                setConverted(true);

                //API key: 15b1ecf20ebd3f7f75267fe3bdc61e2d53a081df
                String key = "15b1ecf20ebd3f7f75267fe3bdc61e2d53a081df";

                //https://api.getgeoapi.com/v2/currency/convert?format=json&from=AUD&to=CAD&amount=1&api_key=YOUR_API_KEY&format=json

                //Must use a number in the JSON request to convert
                double num = Double.parseDouble(amountToC);

                //JSON Object request in a try-catch statement
                url = "https://api.getgeoapi.com/v2/currency/convert?format=json&from=" + oldC+ "&to="+ newC +"&amount=" + num +"&api_key="+ key + "&format=json";

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        (response) ->{
                            Toast.makeText(this, "Successful API Fetch", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(this, "Unsuccessful API Fetch", Toast.LENGTH_SHORT).show();
                        });
                queue.add(request);

                //shared preferences, sending the data to the next page. The new amount was already added above.
                editor.putString("Currency", oldC);
                editor.putString("Amount", amountToC);
                editor.putString("NewCurrency", newC);
                editor.apply();

                resultsPage.putExtra("Currency", variableBinding.currencyEnter.getText().toString());
                resultsPage.putExtra("Amount", variableBinding.amountEnter.getText().toString());
                resultsPage.putExtra("NewCurrency", variableBinding.newCurrencyEnter.getText().toString());

            }
            else {

            }

        });
    } //end of onCreate()

    /**
     * A simple set method for a created Boolean variable that determines if converted button was hit. This sets the value.
     * @param answer Has the converted button been hit?
     */
    public void setConverted(Boolean answer){
        this.hasBeenConverted = answer;
    }

    /**
     * A simple get method that determines returns the Boolean variable hasBeenConverted. The value determines if the converted button
     * has been hit.
     * @return hasBeenConverted, if the convert button has been hit
     */
    public boolean hasBeenConverted(){
        return hasBeenConverted;
    };

    /**
     * A boolean method that checks if the data entered by the user is valid. if the current currency code and new currency code are not
     * exactly 3 letters long, the method returns false and provides a feedback Toast message. If there is no amount entered, it
     * returns false. If everything passes the checks, the method returns true and the information is safe to send to the server.
     * @return If the data is in the right format
     */
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

    /**
     * This method inflates the menu bar that lets the user access different activity options.
     * @param menu The options menu in which you place your items.
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.currency_menu_toolbar, menu);

        return true;
    }

    /**
     * The method that handles what happens when each menu item is selected. It's using an if-statement to look for matches then
     * loads the new intents.
     * @param item The menu item that was selected.
     * @return true
     */
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
        else if(item.getItemId() == R.id.action_help) {

            String instructions = getString(R.string.c_instructions);
            String title = getString(R.string.title);
            String answer = getString(R.string.understand);

            AlertDialog.Builder builder = new AlertDialog.Builder( CurrencyConverter.this );

            builder.setMessage(instructions)
                    .setTitle(title)
                    .setPositiveButton(answer, (dialog, cl) ->{
                    })
                    .show();
        }
        return true;
    } //end of menu bar

}