package algonquin.cst2335.androidfinalproj.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.androidfinalproj.data.ResultsModel;
import algonquin.cst2335.androidfinalproj.databinding.ResultsPageBinding;

public class ResultsPage extends AppCompatActivity {

    private ResultsModel resultsModel;
    private ResultsPageBinding variableBinding;

    //    ArrayList<ChatMessage> messages = new ArrayList<>();
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

        //opening the database
        ResultsDatabase db = Room.databaseBuilder(getApplicationContext(), ResultsDatabase.class, "database-name").build();
        ResultDAO rDAO = db.rDAO();

        results = resultsModel.results.getValue();

        if(results == null)
        {
            resultsModel.results.setValue(results = new ArrayList<>());

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                results.addAll( rDAO.getAllResults() ); //Once you get the data from database

                runOnUiThread( () ->  variableBinding.recycleView.setAdapter( myAdapter )); //You can then load the RecyclerView
            });
        }

    }
}
