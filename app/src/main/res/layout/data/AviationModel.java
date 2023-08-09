package layout.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import algonquin.cst2335.androidfinalproj.aviationtracker.data.Flight;

public class AviationModel extends ViewModel {
    public MutableLiveData<ArrayList<Flight>> flights = new MutableLiveData<>(new ArrayList<>());

    public void setFlights(ArrayList<Flight> newFlights) {
        flights.setValue(newFlights);
    }
}

