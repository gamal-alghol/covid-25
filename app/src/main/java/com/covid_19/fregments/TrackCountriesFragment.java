package com.covid_19.fregments;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.covid_19.Adapter.CountryAdapter;
import com.covid_19.R;
import com.covid_19.model.CovidWithCountries;
import com.covid_19.network.ApiClient;
import com.covid_19.network.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrackCountriesFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<CovidWithCountries> countries = new ArrayList<>();
    private EditText searchEditText;
    private CountryAdapter countryAdapter;
    private ProgressBar progressBar;

    public TrackCountriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_track_countries, container, false);
        recyclerView = view.findViewById(R.id.list_countries);
        searchEditText = view.findViewById(R.id.editText);
        progressBar=view.findViewById(R.id.progressBar);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        getCountries();
        createSearchView();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void createSearchView() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                countryAdapter.getFilter().filter(editable.toString());


            }
        });
    }

    private void getCountries() {

        ApiInterface apiInterface = ApiClient.getClientForCountries().create(ApiInterface.class);


        apiInterface.getCountries().enqueue(new Callback<ArrayList<CovidWithCountries>>() {
            @Override
            public void onResponse(Call<ArrayList<CovidWithCountries>> call, Response<ArrayList<CovidWithCountries>> response) {

                if (response.body() != null) {

                    Log.d("ttt", response.body().get(0).getCountry().toString() + "==> Country2 ");
                    countries= response.body();

                } else {
                    Log.d("ttt", "null " + response.toString());
                }


                countryAdapter=new CountryAdapter(countries, getActivity());
                recyclerView.setAdapter(countryAdapter);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<ArrayList<CovidWithCountries>> call, Throwable t) {
                Log.d("ttt", t.getMessage());

            }
        });
    }
}
