package com.chembrovich.weatherinfo.view;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chembrovich.weatherinfo.R;
import com.chembrovich.weatherinfo.presenter.WeatherPresenter;
import com.chembrovich.weatherinfo.presenter.interfaces.WeatherPresenterInterface;
import com.chembrovich.weatherinfo.view.interfaces.WeatherViewInterface;

public class WeatherFragment extends Fragment implements WeatherViewInterface {
    private int GPS_PERMISSIONS_REQUEST_CODE = 1;

    private WeatherPresenterInterface presenter;
    private OnWeatherFragmentInteractionListener listener;

    private RecyclerView recyclerView;
    private ImageView currentStateImageView;
    private TextView currentStateAndWeatherTextView;
    private TextView cityTextView;
    private TextView countryTextView;
    private TextView nowTextView;
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
                presenter.newLocationsIsGetted(location.getLatitude(), location.getLongitude());
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
                presenter.gpsEnabled();
            }

            public void onProviderDisabled(String provider) {
                presenter.gpsDisabled();
            }
        };

        presenter = new WeatherPresenter();
        presenter.attachView(this);

        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new WeatherRecyclerViewAdapter(presenter, listener));

        currentStateImageView = view.findViewById(R.id.image_view_current_state);
        currentStateAndWeatherTextView = view.findViewById(R.id.text_view_current_state_and_weather);
        cityTextView = view.findViewById(R.id.text_view_city);
        countryTextView = view.findViewById(R.id.text_view_country);
        nowTextView = view.findViewById(R.id.text_view_now);

        return view;
    }

    @Override
    public void requestLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == GPS_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter.gpsPermissionGranted();
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
        nowTextView.setVisibility(View.VISIBLE);

        locationManager.removeUpdates(locationListener);

        refreshLayout.setRefreshing(false);
    }

    @Override
    public void makeMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public interface OnWeatherFragmentInteractionListener {
        void onWeatherFragmentInteraction();
    }
}
