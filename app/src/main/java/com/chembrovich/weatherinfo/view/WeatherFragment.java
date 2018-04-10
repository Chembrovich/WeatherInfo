package com.chembrovich.weatherinfo.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chembrovich.weatherinfo.R;
import com.chembrovich.weatherinfo.presenter.WeatherPresenter;
import com.chembrovich.weatherinfo.presenter.interfaces.WeatherPresenterInterface;
import com.chembrovich.weatherinfo.view.interfaces.WeatherViewInterface;

public class WeatherFragment extends Fragment implements WeatherViewInterface {
    private WeatherPresenterInterface presenter;
    private OnWeatherFragmentInteractionListener listener;
    private RecyclerView recyclerView;
    private ImageView currentStateImageView;
    private TextView currentStateAndWeatherTextView;
    private TextView cityTextView;
    private TextView countryTextView;

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

        return view;
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
    }

    public interface OnWeatherFragmentInteractionListener {
        void onWeatherFragmentInteraction();
    }
}
