package algonquin.cst2335.androidfinalproj.currencyconverter.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import algonquin.cst2335.androidfinalproj.databinding.InstructionFragmentLayoutBinding;

public class InstructionFragment extends Fragment {


    public InstructionFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        InstructionFragmentLayoutBinding binding = InstructionFragmentLayoutBinding.inflate(inflater);

        return binding.getRoot();
   } //end of oncreate()


}
