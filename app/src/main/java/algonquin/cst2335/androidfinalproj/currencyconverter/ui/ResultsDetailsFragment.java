package algonquin.cst2335.androidfinalproj.currencyconverter.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import algonquin.cst2335.androidfinalproj.databinding.ResultDetailsLayoutBinding;

public class ResultsDetailsFragment extends Fragment {
    Result selected;

    public ResultsDetailsFragment(Result m){
        selected = m;
    }

    public ResultsDetailsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ResultDetailsLayoutBinding binding = ResultDetailsLayoutBinding.inflate(inflater);

        if (selected != null) {

            binding.result.setText(selected.newAmount + " " + selected.newCurrency);
            binding.oldInfo.setText(selected.amount + " " + selected.currency);

            //binding.message.setText(selected.message);
           // binding.time.setText(selected.timeSent);
            //binding.sendReceive.setText(selected.getIsSent().toString());
            //binding.databaseId.setText("ID: " + selected.id);
        }

        return binding.getRoot();
   } //end of oncreate()


}
