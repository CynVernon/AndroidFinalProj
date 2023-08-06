package algonquin.cst2335.androidfinalproj.currencyconverter.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.androidfinalproj.R;
import algonquin.cst2335.androidfinalproj.currencyconverter.data.ResultsModel;
import algonquin.cst2335.androidfinalproj.databinding.PastResultsBinding;
import algonquin.cst2335.androidfinalproj.databinding.ResultsPageBinding;


/**
 * The ResultsPage class that displays the previous converted amounts by the User and what the
 * currencies were. It loads onto a recycler view after accessing the objects from the database.
 */
public class ResultsPage extends AppCompatActivity {

    private ResultsModel resultsModel;
    private ResultsPageBinding variableBinding;

    ArrayList<Result> results = new ArrayList<>();

    private RecyclerView.Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting the view model
        resultsModel = new ViewModelProvider(this).get(ResultsModel.class);

        //setting the variable binding
        variableBinding = ResultsPageBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        //setting the recycler view
        variableBinding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        //getting shared preferences
        SharedPreferences pref = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        Intent fromPrevious = getIntent();

        //opening the database
        ResultsDatabase db = Room.databaseBuilder(getApplicationContext(), ResultsDatabase.class, "database-name").build();
        ResultDAO rDAO = db.rDAO();

        //adapter implementation
        variableBinding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                PastResultsBinding resultsBinding = PastResultsBinding.inflate(getLayoutInflater());
                return new MyRowHolder(resultsBinding.getRoot());
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {

                Result result = results.get(position);

                holder.oldAmount.setText(result.getAmount());
                holder.oldCurrency.setText(result.getCurrency());
                holder.newAmount.setText(result.getNewAmount());
                holder.newCurrency.setText(result.getNewCurrency());

            } //end of onBindViewHolder
            @Override
            public int getItemCount() {
                return results.size();
            }

            public int getItemViewType(int position){
                return 0;
            }
        });

        variableBinding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        results = resultsModel.results.getValue();

        //loading the previous results
        if(results == null)
        {
            resultsModel.results.setValue(results = new ArrayList<>());

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                results.addAll( rDAO.getAllResults() ); //Once you get the data from database
                myAdapter.notifyDataSetChanged();
                runOnUiThread( () ->  variableBinding.recycleView.setAdapter( myAdapter )); //You can then load the RecyclerView
            });
        }

        //onclick listener for the clear button
        variableBinding.deleteBtn.setOnClickListener(clk -> {

            TextView oldAmount = findViewById(R.id.oldAmount);

            Result result = resultsModel.selectedResult.getValue();

            //ask if you want to delete it first
            AlertDialog.Builder builder = new AlertDialog.Builder( ResultsPage.this );

            // Deleting the message
            int position = results.indexOf(result);

            builder.setMessage( "Do you want to delete this entry?")
                    .setTitle("Confirmation:")
                    .setNegativeButton("No", (dialog, cl) ->{})
                    .setPositiveButton("Yes", (dialog, cl) -> {
                        // Deleting the message
                        Result r = results.get(position);


                        Executor thread = Executors.newSingleThreadExecutor();
                        thread.execute(() -> {
                            // deleting message to the database
                            rDAO.deleteMessage(r);
                        });

                        Result removedResult = results.get(position);

                        results.remove(position);
                        myAdapter.notifyItemRemoved(position);

                        resultsModel.selectedResult.postValue(null);

                        Snackbar.make(oldAmount,"You deleted message #" + position, Snackbar.LENGTH_LONG)
                                .setAction("Undo", click -> {
                                    results.add(position, removedResult);
                                    myAdapter.notifyItemInserted(position); // Notify adapter with correct position
                                })
                                .show();
                    }).create().show();

        });

        //getting strings from shared prefs
        String oldCurrency = fromPrevious.getStringExtra("Currency");
        String oldAmount = fromPrevious.getStringExtra("Amount");
        String newAmount = fromPrevious.getStringExtra("NewAmount");
        String newCurrency = fromPrevious.getStringExtra("NewCurrency");
        Boolean converted = fromPrevious.getBooleanExtra("Converted", false);

        Result r = new Result(oldAmount, oldCurrency, newAmount, newCurrency);

        //if any information is missing, it will not be added to the database. Also prevents
        // usage of the history button without converting anything to not add empty
        // entries into the database
        if (converted == true){
            results.add(r);
            //notifying dataset of added item
            myAdapter.notifyItemInserted(results.size() - 1);
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                // adding message to the database
                rDAO.insertResult(r);
            });
        }

        variableBinding.recycleView.setLayoutManager(new GridLayoutManager(this, 1));


        resultsModel.selectedResult.observe(this, (newValue) -> {

            ResultsDetailsFragment fragment = new ResultsDetailsFragment(newValue);

            if(newValue != null) {
                FragmentManager fMgr = getSupportFragmentManager();
                FragmentTransaction tx = fMgr.beginTransaction();
                tx.replace(R.id.fragmentLocation, fragment);
                tx.commit();
                tx.addToBackStack("");
            }
        });

    } //end of onCreate()
    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView oldAmount;
        TextView oldCurrency;
        TextView newAmount;
        TextView newCurrency;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            //onclick listener for itemView
            itemView.setOnClickListener( clk -> {

                int position = getAbsoluteAdapterPosition();
                Result selected = results.get(position);

                resultsModel.selectedResult.postValue(selected);

                //get the adapter position to know which row to delete
               // int position = getAbsoluteAdapterPosition();

            });

            oldAmount = itemView.findViewById(R.id.oldAmount);
            oldCurrency = itemView.findViewById(R.id.oldCurrency);
            newAmount = itemView.findViewById(R.id.newAmount);
            newCurrency = itemView.findViewById(R.id.newCurrency);
        } //end of RowHolder
    } //end of RowHolder class

} //end of class
