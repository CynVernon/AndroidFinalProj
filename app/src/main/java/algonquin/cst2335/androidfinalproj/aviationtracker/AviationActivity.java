package algonquin.cst2335.androidfinalproj.aviationtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.androidfinalproj.R;
import algonquin.cst2335.androidfinalproj.aviationtracker.data.AviationModel;
import algonquin.cst2335.androidfinalproj.aviationtracker.data.Flight;
import algonquin.cst2335.androidfinalproj.aviationtracker.data.FlightDAO;
import algonquin.cst2335.androidfinalproj.aviationtracker.data.FlightDatabase;
import algonquin.cst2335.androidfinalproj.databinding.ActivityAviationBinding;
import algonquin.cst2335.androidfinalproj.databinding.FlightRowBinding;

/**
 * The java code for the aviation activity
 */
public class AviationActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private ArrayList<Flight> flights ;
    private FlightDAO fDAO;
    private Flight f;
    private AviationModel model;
    private FlightDatabase db;
    private ActivityAviationBinding binding;
    private SharedPreferences sharedPreferences;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //code for making the binding
        binding = ActivityAviationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //code for the model
        model = new ViewModelProvider(this).get(AviationModel.class);
        flights = model.flights.getValue();

        //Database code; 9694046b348f410681e40e36750f3730 ef636c3084596897297efaffc637c25e
        queue = Volley.newRequestQueue(this);
        Executor thread = Executors.newSingleThreadExecutor();
        String url= "https://api.aviationstack.com/v1/flights?access_key=9694046b348f410681e40e36750f3730&dep_iata=";
        db = Room.databaseBuilder(getApplicationContext(), FlightDatabase.class, "Flight db").build();
        fDAO = db.fDAO();

        //Code for shared preferences
        sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE); //TODO: I have a feeling this won't work. Double check it.
        String previousAirportCode = sharedPreferences.getString("airport_code", "");
        //Set the preference
        EditText editTextAirportCode = binding.editText;
        editTextAirportCode.setText(previousAirportCode);

        //code for the search button
        binding.search.setOnClickListener(click -> {
            flights.clear();
            String airportCode = binding.editText.getText().toString().toUpperCase();
            if (!validateCode(airportCode)) {
                Toast.makeText(AviationActivity.this, "Invalid airport code: " + airportCode, Toast.LENGTH_SHORT).show();
            } else {
                String searchURL = url + airportCode;
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, searchURL, null,
                        (response) -> {
                    try {
                        JSONArray dataArray = response.getJSONArray("data");
                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject position = dataArray.getJSONObject(i);
                            JSONObject arrival = position.getJSONObject("arrival");
                            String destinationAirport = arrival.getString("airport");
                            String terminal = arrival.getString("terminal");
                            String gate = arrival.getString("gate");
                            int delayMinutes = arrival.optInt("delay", 0);

                            JSONObject f = position.getJSONObject("flight");
                            int flightNumber = f.optInt("number", 0);

                            String date = position.getString("flight_date");

                            JSONObject airline = position.getJSONObject("airline");
                            String airlineName = airline.getString("name");

                            if (destinationAirport.equals("null")) {
                                destinationAirport = "N/A";
                            }

                            if (terminal.equals("null")) {
                                terminal = "N/A";
                            }

                            if (gate.equals("null")) {
                                gate = "N/A";
                            }

                            if (date.equals("null")) {
                                date = "N/A";
                            }

                            if (airlineName.equals("null")) {
                                airlineName = "N/A";
                            }

                            String delayString;
                            if (delayMinutes > 0) {
                                delayString = delayMinutes + " minutes";
                            } else {
                                delayString = getString(R.string.no_delay);
                            }
                            Flight flight = new Flight(flightNumber, date, airlineName, destinationAirport, terminal, gate, delayString);
                            flights.add(flight);

                        }
                        runOnUiThread(() -> {
                            adapter.notifyDataSetChanged();
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(AviationActivity.this, "Error processing API response", Toast.LENGTH_SHORT).show();
                    }
                }, (error) -> {
                    Toast.makeText(AviationActivity.this, "Api has crashed. Please try again in a minute.", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                });
                request.setRetryPolicy(new DefaultRetryPolicy(
                        30000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.add(request);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Code",airportCode);
                editor.apply();
                binding.editText.setText("");
            }
        });

        binding.loadSaved.setOnClickListener(clk -> {

        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter( adapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                FlightRowBinding rowBinding = FlightRowBinding.inflate(getLayoutInflater(), parent, false);
                return new MyRowHolder(rowBinding.getRoot());
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                Flight flight = flights.get(position);

                holder.flightNumber.setText(getString(R.string.flight_number) + flight.getFlightNumber());
                holder.date.setText(getString(R.string.date) + flight.getDate());
                holder.airline.setText(getString(R.string.airline) + flight.getAirline());
                holder.destination.setText(getString(R.string.destination) + flight.getDestination());
            }

            @Override
            public int getItemCount() {
                return flights.size();
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

    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView flightNumber;
        TextView date;
        TextView airline;
        TextView destination;
        public MyRowHolder(View itemView) {
            super(itemView);

            //find the TextViews for terminal, destination, gate, and delay
            flightNumber = itemView.findViewById(R.id.flight_number);
            date = itemView.findViewById(R.id.date);
            airline = itemView.findViewById(R.id.airline);
            destination = itemView.findViewById(R.id.destination);

            //Code to view details of the fragment.
            itemView.setOnClickListener(clk -> {
                int position = getAbsoluteAdapterPosition();
                Flight selected = flights.get(position);
                model.selectedFlight.postValue(selected);

                AlertDialog.Builder builder = new AlertDialog.Builder(AviationActivity.this);
                builder.setTitle("More Info")
                        .setMessage(
                                getString(R.string.destination) + selected.getDestination() + "\n" +
                                getString(R.string.terminal) + selected.getTerminal() + "\n" +
                                getString(R.string.gate) + selected.getGate() + "\n" +
                                getString(R.string.delay) + selected.getDelay()
                        )
                        .setNegativeButton("Close", (dialog, cl) -> {
                        })
                        .setPositiveButton("Save", (dialog, cl) -> {
                            Executor thread = Executors.newSingleThreadExecutor();
                            f = flights.get(position);
                            thread.execute(() -> fDAO.insertFlight(f));
                            Snackbar.make(
                                        flightNumber, "You saved flight  #" +
                                        flightNumber.getText() + " to " + destination.getText(),
                                        Snackbar.LENGTH_LONG)
                                    .show();
                        })
                        .create().show();
            });

            //Code to delete an entry
            itemView.setOnLongClickListener(clk -> {
                int position = getAbsoluteAdapterPosition();
                AlertDialog.Builder builder = new AlertDialog.Builder(AviationActivity.this);
                builder.setTitle("Warning!")
                        .setMessage(
                                "Are you sure you wish to delete flight " +
                                flightNumber.getText() + " to " + destination.getText() + "?")
                        .setNegativeButton("No", (dialog, cl) -> {
                        })
                        .setPositiveButton("Yes", (dialog, cl) -> {
                            Executor thread = Executors.newSingleThreadExecutor();
                            f = flights.get(position);
                            thread.execute(() -> fDAO.deleteFlight(f));
                            flights.remove(position);
                            adapter.notifyItemRemoved(position);
                            Snackbar.make(destination, "You deleted flight entry #" + (position + 1), Snackbar.LENGTH_LONG)
                                    .setAction("Undo", click -> {
                                        flights.add(position, f);
                                        runOnUiThread(() -> adapter.notifyItemInserted(position));
                                    })
                                    .show();
                        })
                        .create().show();
                return false;
            });
        }


    }
}
