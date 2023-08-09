package algonquin.cst2335.androidfinalproj.aviationtracker.data;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import algonquin.cst2335.androidfinalproj.databinding.FlightRowBinding;

public class FlightDetailsFragment extends Fragment {
    private Flight selected;

    public FlightDetailsFragment(Flight f) {
        selected = f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        FlightRowBinding binding = FlightRowBinding.inflate(inflater);

        binding.destination.setText(selected.destination);
        binding.terminal.setText(selected.terminal);
        binding.gate.setText(selected.gate);
        binding.delay.setText(selected.delay);

        return binding.getRoot();
    }
}
