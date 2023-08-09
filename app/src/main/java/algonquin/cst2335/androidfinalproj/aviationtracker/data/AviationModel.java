package algonquin.cst2335.androidfinalproj.aviationtracker.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;

public class AviationModel extends ViewModel {
    public MutableLiveData<ArrayList<Flight>> flights = new MutableLiveData<>( new ArrayList<>() );
    public MutableLiveData<Flight> selectedFlight = new MutableLiveData< >();
}

