package layout.data;

import android.os.Bundle;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class FlightDetailsFragment extends Fragment {
    private Flight selected;

    public FlightDetailsFragment(Flight f) {
        selected = f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        DetailsLayoutBinding binding = DetailsLayoutBinding.inflate(inflater);

        binding.destination.setText(selected.getDestination());
        binding.terminal.setText(selected.getTerminal());
        binding.gate.setText(selected.getGate());
        binding.delay.setText(selected.getDelay());

        return binding.getRoot();
    }
}