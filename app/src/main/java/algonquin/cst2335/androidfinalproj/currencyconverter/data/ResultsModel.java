package algonquin.cst2335.androidfinalproj.currencyconverter.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import algonquin.cst2335.androidfinalproj.currencyconverter.ui.Result;

public class ResultsModel extends ViewModel {
    public MutableLiveData<ArrayList<Result>> results = new MutableLiveData< >();
    public String oldAmount;
    public String oldCurrency;
    public String newCurrency;
    public String newAmount;

    public MutableLiveData<Result> selectedResult = new MutableLiveData< >();


}
