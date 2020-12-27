package com.covid_19.network;

import com.covid_19.model.Constants;
import com.covid_19.model.Covid;
import com.covid_19.model.CovidWithCountries;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET(Constants.BASE_URL2)
    Call<Covid>getAllCases();

    @GET(Constants.COUNTRIES_URL)
    Call<ArrayList<CovidWithCountries>>getCountries();

}


