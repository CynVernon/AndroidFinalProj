package algonquin.cst2335.androidfinalproj.currencyconverter.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import algonquin.cst2335.androidfinalproj.currencyconverter.ui.Result;

public class ResultsModel extends ViewModel {
    public MutableLiveData<ArrayList<Result>> results = new MutableLiveData< >();

}
