package algonquin.cst2335.androidfinalproj.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import algonquin.cst2335.androidfinalproj.data.ResultsModel;
import algonquin.cst2335.androidfinalproj.databinding.ResultsPageBinding;

public class Results extends AppCompatActivity {

    private ResultsModel model;
    private ResultsPageBinding variableBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting the view model
        model = new ViewModelProvider(this).get(ResultsModel.class);

        //setting the variable binding
        variableBinding = ResultsPageBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

    }
}
