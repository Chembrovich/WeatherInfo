package com.chembrovich.weatherinfo.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chembrovich.weatherinfo.R;

public class MainActivity extends AppCompatActivity implements WeatherFragment.OnWeatherFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            WeatherFragment weatherFragment = new WeatherFragment();
            weatherFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, weatherFragment).commit();
        }
    }

    @Override
    public void onWeatherFragmentInteraction() {

    }
}
