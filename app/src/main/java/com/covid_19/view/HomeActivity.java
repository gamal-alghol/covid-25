package com.covid_19.view;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.covid_19.R;
import com.covid_19.fregments.HomeFragment;
import com.covid_19.fregments.StatistiscFragment;
import com.covid_19.fregments.UsersFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    public static BottomNavigationView navigationView;

    HomeFragment homeFragment;
   UsersFragment userFragment ;
            StatistiscFragment statistiscFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navigationView = findViewById(R.id.bottom_navigation);
        homeFragment = new HomeFragment();
        userFragment=new UsersFragment();
        statistiscFragment=new StatistiscFragment();
createNavigationView();

    }
    private void createNavigationView() {
        navigationView.performClick();
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        if (!homeFragment.isAdded())
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_home, homeFragment).commit();

                        return true;

                    case R.id.messages:
                        if (!userFragment.isAdded())
                       getSupportFragmentManager().beginTransaction().replace(R.id.frame_home,userFragment).commit();
                    return true;
                    case R.id.statistics:
                        if (!statistiscFragment.isAdded())
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_home,statistiscFragment).commit();
                        return true;
                        /*
                    case R.id.action_my_order:
                        if (!myOrderFragment.isAdded())
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_home, myOrderFragment).commit();
                        return true;
                    case R.id.action_favorite:
                        if (!favoriteFragment.isAdded())
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_home, favoriteFragment).commit();
                        return true;
*/
                }
                return false;
            }
        });
        navigationView.setSelectedItemId(R.id.action_home);

    }

}
