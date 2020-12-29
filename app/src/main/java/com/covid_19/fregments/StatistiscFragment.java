package com.covid_19.fregments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.covid_19.R;
import com.covid_19.model.Covid;
import com.covid_19.network.ApiClient;
import com.covid_19.network.ApiInterface;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StatistiscFragment extends Fragment {
    private TextView casesTv;
    private TextView todayCasesTv;
    private TextView deathsTv;
    private TextView todayDeathsTv;
    private TextView recoveredTv;

    Button trackButton;
    public StatistiscFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        casesTv=view.findViewById(R.id.cases_tv);
        todayCasesTv =view.findViewById(R.id.todayCases_tv);
        deathsTv=view.findViewById(R.id.deaths_tv);
        todayDeathsTv=view.findViewById(R.id.todayDeaths_tv);
        recoveredTv=view.findViewById(R.id.recovered_tv);
        trackButton=view.findViewById(R.id.track_btn);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getData();

        trackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.frame_home,new TrackCountriesFragment())
                        .addToBackStack("stack").commit();
            }
        });

    }

    private void getData() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.getAllCases().enqueue(new Callback<Covid>() {
            @Override
            public void onResponse(Call<Covid> call, Response<Covid> response) {
                if (response.body() != null) {
                    //           Log.d("ttt", response.body().getCases() + "==> cases ");
//                    Toast.makeText(getContext(), response.body().getDeaths() + "", Toast.LENGTH_SHORT).show();

                    casesTv.setText(String.valueOf(response.body().getCases()));
                    todayCasesTv.setText(String.valueOf(response.body().getTodayCases()));
                    todayDeathsTv.setText(String.valueOf(response.body().getDeaths()));
                    deathsTv.setText(String.valueOf(response.body().getTodayDeaths()));
                    recoveredTv.setText(String.valueOf(response.body().getRecovered()));

                } else {
                    Log.d("ttt", "null " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<Covid> call, Throwable t) {
                Log.d("ttt", "fuiler  " + t.getMessage());
                FirebaseCrashlytics.getInstance().log("Email or Password Error");
            }
        });
    }



}

