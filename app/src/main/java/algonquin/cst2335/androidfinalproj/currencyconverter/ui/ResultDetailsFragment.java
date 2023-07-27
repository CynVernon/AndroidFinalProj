package algonquin.cst2335.androidfinalproj.currencyconverter.ui;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import algonquin.cst2335.androidfinalproj.databinding.ResultDetailsLayoutBinding;


public class ResultDetailsFragment extends Fragment {


    public ResultDetailsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ResultDetailsLayoutBinding binding = ResultDetailsLayoutBinding.inflate(inflater);

        binding.resultView.setText("");
          binding.amountView.setText("");

        return binding.getRoot();
   } //end of oncreate()


}
