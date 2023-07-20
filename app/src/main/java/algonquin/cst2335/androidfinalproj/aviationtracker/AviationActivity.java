package algonquin.cst2335.androidfinalproj.aviationtracker;

import android.os.Bundle;
import android.os.PersistableBundle;

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

    private ActivityAviationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(AviationModel.class);

        //https://api.aviationstack.com/v1/flights?access_key=ef636c3084596897297efaffc637c25e&dep_iata=

        FlightDatabase db = Room.databaseBuilder(getApplicationContext(), FlightDatabase.class, "database-name").build();

        binding = ActivityAviationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}
