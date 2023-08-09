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

                            if (terminal.equals("null")) {
                                terminal = "N/A";
                            }

                            if (gate.equals("null")) {
                                gate = "N/A";
                            }

                            String delayString;
                            if (delayMinutes > 0) {
                                delayString = delayMinutes + " minutes";
                            } else {
                                delayString = getString(R.string.no_delay);
                            }
                            Flight flight = new Flight(destinationAirport, terminal, gate, delayString);
                            //TODO: make sure data doesn't duplicate.
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

                holder.destination.setText(getString(R.string.destination) + flight.getDestination());
                holder.terminal.setText(getString(R.string.terminal) + flight.getTerminal());
                holder.gate.setText(getString(R.string.gate) + flight.getGate());
                holder.delay.setText(getString(R.string.delay) + flight.getDelay());
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
        TextView destination;
        TextView terminal;
        TextView gate;
        TextView delay;
        public MyRowHolder(View itemView) {
            super(itemView);

            //find the TextViews for terminal, destination, gate, and delay
            destination = itemView.findViewById(R.id.destination);
            terminal = itemView.findViewById(R.id.terminal);
            gate = itemView.findViewById(R.id.gate);
            delay = itemView.findViewById(R.id.delay);

            //Code to view details of the fragment.
            itemView.setOnClickListener(clk -> {
                int position = getAbsoluteAdapterPosition();
                Flight selected = flights.get(position);
                model.selectedFlight.postValue(selected);
            });

            //Code to delete an entry
            itemView.setOnLongClickListener(clk -> {
                int position = getAbsoluteAdapterPosition();
                AlertDialog.Builder builder = new AlertDialog.Builder(AviationActivity.this);
                builder.setTitle("Warning!")
                        .setMessage("Are you sure you wish to delete " + destination.getText() + "?")
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
