package com.covid_19;

import android.util.Log;
import android.view.View;

import com.covid_19.Adapter.CountryAdapter;
import com.covid_19.model.CountryInfo;
import com.covid_19.model.Covid;
import com.covid_19.model.CovidWithCountries;
import com.covid_19.network.ApiClient;
import com.covid_19.network.ApiInterface;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountriesUnitTest {

    private CountDownLatch latch = new CountDownLatch(1);
    ApiInterface apiInterface;
    ArrayList<CovidWithCountries> countriesArrayListResponse;
    String country;
    CountryInfo countryInfo;

    @Before
    public void beforeClassTest() {
         apiInterface = ApiClient.getClientForCountries().create(ApiInterface.class);

    }

        @Test
    public void testGetCountries() throws InterruptedException {
        apiInterface.getCountries().enqueue(new Callback<ArrayList<CovidWithCountries>>() {
            @Override
            public void onResponse(Call<ArrayList<CovidWithCountries>> call, Response<ArrayList<CovidWithCountries>> response) {
                countriesArrayListResponse=response.body();
                latch.countDown();

            }

            @Override
            public void onFailure(Call<ArrayList<CovidWithCountries>> call, Throwable t) {
                Log.d("ttt", t.getMessage());

            }
        });


        latch.await(5, TimeUnit.SECONDS);
        Assert.assertNotNull(countriesArrayListResponse);

    }

    @Test
    public void testCountry() throws InterruptedException {
        apiInterface.getCountries().enqueue(new Callback<ArrayList<CovidWithCountries>>() {
            @Override
            public void onResponse(Call<ArrayList<CovidWithCountries>> call, Response<ArrayList<CovidWithCountries>> response) {
                country=response.body().get(0).getCountry();
                latch.countDown();

            }

            @Override
            public void onFailure(Call<ArrayList<CovidWithCountries>> call, Throwable t) {
                Log.d("ttt", t.getMessage());

            }
        });


        latch.await(5, TimeUnit.SECONDS);
        Assert.assertNotNull(country);

    }


    @Test
    public void testCountryInfo() throws InterruptedException {
        apiInterface.getCountries().enqueue(new Callback<ArrayList<CovidWithCountries>>() {
            @Override
            public void onResponse(Call<ArrayList<CovidWithCountries>> call, Response<ArrayList<CovidWithCountries>> response) {
                countryInfo=response.body().get(0).getCountryInfo();
                latch.countDown();

            }
            @Override
            public void onFailure(Call<ArrayList<CovidWithCountries>> call, Throwable t) {
                Log.d("ttt", t.getMessage());

            }
        });


        latch.await(5, TimeUnit.SECONDS);
        Assert.assertNotNull(countryInfo);
    }

    @Test
    public void testCountryFlag() throws InterruptedException {
        apiInterface.getCountries().enqueue(new Callback<ArrayList<CovidWithCountries>>() {
            @Override
            public void onResponse(Call<ArrayList<CovidWithCountries>> call, Response<ArrayList<CovidWithCountries>> response) {
                country=response.body().get(0).getCountryInfo().getFlag();
                latch.countDown();

            }

            @Override
            public void onFailure(Call<ArrayList<CovidWithCountries>> call, Throwable t) {
                Log.d("ttt", t.getMessage());

            }
        });


        latch.await(5, TimeUnit.SECONDS);
        Assert.assertNotNull(country);
    }



}
