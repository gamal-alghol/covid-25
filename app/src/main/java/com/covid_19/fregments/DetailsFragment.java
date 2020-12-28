package com.covid_19.fregments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.covid_19.Adapter.CountryAdapter;
import com.covid_19.R;
import com.covid_19.model.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {


    private TextView casesTv;
    private TextView titleTv;
    private TextView todayCasesTv;
    private TextView deathsTv;
    private TextView todayDeathsTv;
    private TextView recoveredTv;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleTv = view.findViewById(R.id.title_toolbar);
        casesTv = view.findViewById(R.id.cases_tv);
        todayCasesTv = view.findViewById(R.id.todayCases_tv);
        deathsTv = view.findViewById(R.id.deaths_tv);
        todayDeathsTv = view.findViewById(R.id.todayDeaths_tv);
        recoveredTv = view.findViewById(R.id.recovered_tv);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    int position =getArguments().getInt(Constants.POSITION);
        Log.d("ttt", "position in details = ?"+getArguments().getInt(Constants.POSITION));

        casesTv.setText(String.valueOf(CountryAdapter.listFilteredCountries.get(position).getCases()));
        titleTv.setText("Details Of "+CountryAdapter.listFilteredCountries.get(position).getCountry());
        todayCasesTv.setText(String.valueOf(CountryAdapter.listFilteredCountries.get(position).getTodayCases()));
        todayDeathsTv.setText(String.valueOf(CountryAdapter.listFilteredCountries.get(position).getDeaths()));
        deathsTv.setText(String.valueOf(CountryAdapter.listFilteredCountries.get(position).getTodayDeaths()));
        recoveredTv.setText(String.valueOf(CountryAdapter.listFilteredCountries.get(position).getRecovered()));


    }
}
