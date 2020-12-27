package com.covid_19.network;

import com.covid_19.model.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;
    private static Retrofit retrofitCountries = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL2)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    public static Retrofit getClientForCountries() {
        if (retrofitCountries == null) {
            retrofitCountries = new Retrofit.Builder()
                    .baseUrl(Constants.COUNTRIES_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofitCountries;
    }
}
