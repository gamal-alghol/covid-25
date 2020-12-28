package com.covid_19;

import com.covid_19.model.Covid;
import com.covid_19.network.ApiClient;
import com.covid_19.network.ApiInterface;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeUnitTest {

    private CountDownLatch latch = new CountDownLatch(1);
    ApiInterface apiInterface;
    Covid covidResponse;
    long numDeaths;
    long numCases;

    @Before
    public void beforeClassTest() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    @Test
    public void testGetData() throws InterruptedException {
        apiInterface.getAllCases().enqueue(new Callback<Covid>() {
            @Override
            public void onResponse(Call<Covid> call, Response<Covid> response) {
                  covidResponse = response.body();
                  latch.countDown();


            }
            @Override
            public void onFailure(Call<Covid> call, Throwable t) {
            }
        });

        latch.await(5, TimeUnit.SECONDS);
        Assert.assertNotNull(covidResponse);

    }

    @Test
    public void testIsMoreMillionDeaths() throws InterruptedException {
        apiInterface.getAllCases().enqueue(new Callback<Covid>() {
            @Override
            public void onResponse(Call<Covid> call, Response<Covid> response) {
                  numDeaths = response.body().getDeaths();

                  latch.countDown();


            }
            @Override
            public void onFailure(Call<Covid> call, Throwable t) {
            }
        });

        latch.await(5, TimeUnit.SECONDS);
        Assert.assertNotNull(numDeaths);

        Assert.assertTrue(isMoreMillion(numDeaths));

    }



    @Test
    public void testIsMoreMillionCases() throws InterruptedException {
        apiInterface.getAllCases().enqueue(new Callback<Covid>() {
            @Override
            public void onResponse(Call<Covid> call, Response<Covid> response) {
                numCases = response.body().getCases();

                latch.countDown();


            }
            @Override
            public void onFailure(Call<Covid> call, Throwable t) {
            }
        });

        latch.await(5, TimeUnit.SECONDS);
        Assert.assertNotNull(numCases);

        Assert.assertTrue(isMoreMillion(numCases));

    }

    @Test
    public void testRecoveredCases() throws InterruptedException {
        apiInterface.getAllCases().enqueue(new Callback<Covid>() {
            @Override
            public void onResponse(Call<Covid> call, Response<Covid> response) {
                numCases = response.body().getRecovered();

                latch.countDown();


            }
            @Override
            public void onFailure(Call<Covid> call, Throwable t) {
            }
        });

        latch.await(5, TimeUnit.SECONDS);
        Assert.assertNotNull(numCases);
    }


    private boolean isMoreMillion(long num){
        return num > 1000000;
    }

    @Test
    public void testTodayCases() throws InterruptedException {
        apiInterface.getAllCases().enqueue(new Callback<Covid>() {
            @Override
            public void onResponse(Call<Covid> call, Response<Covid> response) {
                numCases = response.body().getTodayCases();

                latch.countDown();


            }
            @Override
            public void onFailure(Call<Covid> call, Throwable t) {
            }
        });

        latch.await(5, TimeUnit.SECONDS);
        Assert.assertNotNull(numCases);
        Assert.assertTrue(isMoreMillion(numCases));
    }


    @Test
    public void testActiveCases() throws InterruptedException {
        apiInterface.getAllCases().enqueue(new Callback<Covid>() {
            @Override
            public void onResponse(Call<Covid> call, Response<Covid> response) {
                numCases = response.body().getRecovered();

                latch.countDown();


            }
            @Override
            public void onFailure(Call<Covid> call, Throwable t) {
            }
        });

        latch.await(5, TimeUnit.SECONDS);
        Assert.assertNotNull(numCases);
    }



}
