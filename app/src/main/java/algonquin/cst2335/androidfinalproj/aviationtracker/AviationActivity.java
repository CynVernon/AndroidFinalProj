package algonquin.cst2335.androidfinalproj.aviationtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import java.util.ArrayList;

import algonquin.cst2335.androidfinalproj.aviationtracker.data.Flight;
import algonquin.cst2335.androidfinalproj.aviationtracker.data.FlightDAO;
import algonquin.cst2335.androidfinalproj.aviationtracker.data.FlightDatabase;
import algonquin.cst2335.androidfinalproj.databinding.ActivityAviationBinding;

/**
 * The java code for the aviation activity
 */
public class AviationActivity extends AppCompatActivity {
    ArrayList<Flight> flights;
    FlightDAO fDAO;
    private AviationModel model;
    private FlightDatabase db;
    private ActivityAviationBinding binding;
    private SharedPreferences sharedPreferences;

    //https://github.com/Caryourday96/Group_Project/tree/master/app/src/main/java/alonquin/cst2335/group_project

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(AviationModel.class);

        //code for making the binding
        binding = ActivityAviationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Code for shared preferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this); //TODO: what is the non-depreciated code?
        EditText editTextAirportCode = binding.editText;
        String previousAirportCode = sharedPreferences.getString("airport_code", "");
        editTextAirportCode.setText(previousAirportCode);

        //Database code
        //https://api.aviationstack.com/v1/flights?access_key=ef636c3084596897297efaffc637c25e&dep_iata=YOW
        db = Room.databaseBuilder(getApplicationContext(), FlightDatabase.class, "database-name").build();


        //code for the search button
        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String airportCode = binding.editText.getText().toString().trim().toUpperCase();
                if (!validateCode(airportCode)) {
                    // Invalid airport code
                    Toast.makeText(AviationActivity.this, "Invalid airport code: " + airportCode, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void saveAirportCode(String airportCode) {
        // Store the airport code in SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("airport_code", airportCode);
        editor.apply();
    }

    private boolean validateCode(String airportCode) {
        //TODO: Also make this validate the airport as existing.
        return airportCode.length() == 3 && airportCode.matches("[A-Z]+");
    }
}
