package com.chembrovich.weatherinfo.view;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chembrovich.weatherinfo.R;
import com.chembrovich.weatherinfo.interactor.WeatherInteractor;
import com.chembrovich.weatherinfo.presenter.WeatherPresenter;
import com.chembrovich.weatherinfo.presenter.interfaces.WeatherPresenterInterface;
import com.chembrovich.weatherinfo.view.interfaces.WeatherViewInterface;

import static android.content.Context.MODE_PRIVATE;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;

public class WeatherFragment extends Fragment implements WeatherViewInterface {
    private static final int GPS_PERMISSIONS_REQUEST_CODE = 1;
    private static final String SAVING_PREFERENCES_ERROR = "Error while saving shared preferences";

    private WeatherPresenterInterface presenter;
    private OnWeatherFragmentInteractionListener listener;

    private RecyclerView recyclerView;
    private ImageView currentStateImageView;
    private TextView currentStateAndWeatherTextView;
    private TextView cityTextView;
    private TextView countryTextView;
    private TextView updationStatusTextView;
    private SwipeRefreshLayout refreshLayout;

    private LocationManager locationManager;
    private LocationListener locationListener;

    public WeatherFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_list, container, false);

        refreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.updateLocation();
            }
        });

        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                presenter.newLocationsIsReceived(location.getLatitude(), location.getLongitude());
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
                presenter.gpsEnabled();
            }

            public void onProviderDisabled(String provider) {
            }
        };

        presenter = new WeatherPresenter(new WeatherInteractor(getActivity().getApplicationContext()));
        presenter.attachView(this);

        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new WeatherRecyclerViewAdapter(presenter, listener));
        currentStateImageView = view.findViewById(R.id.image_view_current_state);
        currentStateAndWeatherTextView = view.findViewById(R.id.text_view_current_state_and_weather);
        cityTextView = view.findViewById(R.id.text_view_city);
        countryTextView = view.findViewById(R.id.text_view_country);
        updationStatusTextView = view.findViewById(R.id.text_view_updating_status);

        return view;
    }

    @Override
    public void requestLocation() {
        if (checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    GPS_PERMISSIONS_REQUEST_CODE);
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,0,
                                                   locationListener);

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0,
                                                   locationListener);

            refreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void makeErrorLog(String tag, String message) {
        Log.e(tag, message);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == GPS_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter.gpsPermissionGranted();
            } else {
                stopRefreshing();
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnWeatherFragmentInteractionListener) {
            listener = (OnWeatherFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        presenter.detachView();
    }

    @Override
    public void updateData() {
        cityTextView.setText(presenter.getCity());
        countryTextView.setText(presenter.getCountry());
        currentStateAndWeatherTextView.setText(presenter.getCurrentStateAndWeather());
        currentStateImageView.setImageResource(StateImageHandler.getImageResourceId(presenter.getCurrentStateImageDescription()));
        recyclerView.getAdapter().notifyDataSetChanged();

        if (presenter.isWeatherNew()) {
            updationStatusTextView.setText(R.string.now);
            refreshLayout.setRefreshing(false);
            locationManager.removeUpdates(locationListener);
        } else {
            updationStatusTextView.setText(R.string.at_last_time);
        }
    }

    @Override
    public void stopRefreshing() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void saveLocationToPreferences(String city, String country) {
        try {
            SharedPreferences preferences = getActivity().getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = preferences.edit();
            prefEditor.putString(getString(R.string.city_key), city);
            prefEditor.putString(getString(R.string.country_key), country);
            prefEditor.apply();
        } catch (ClassCastException ex) {
            makeErrorLog(getClass().getName(), SAVING_PREFERENCES_ERROR);
        }
    }

    @Override
    public String getCityFromPreferences() {
        SharedPreferences preferences = getActivity().getPreferences(MODE_PRIVATE);
        return preferences.getString(getString(R.string.city_key), "");
    }

    @Override
    public String getCountryFromPreferences() {
        SharedPreferences preferences = getActivity().getPreferences(MODE_PRIVATE);
        return preferences.getString(getString(R.string.country_key), "");
    }

    @Override
    public void makeMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public interface OnWeatherFragmentInteractionListener {
        void onWeatherFragmentInteraction();
    }
}
