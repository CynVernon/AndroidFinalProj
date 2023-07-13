package algonquin.cst2335.androidfinalproj.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.net.HttpCookie;
import java.util.ArrayList;

import algonquin.cst2335.androidfinalproj.ui.Result;

public class ResultsModel extends ViewModel {
    public MutableLiveData<ArrayList<Result>> results = new MutableLiveData< >();

}
