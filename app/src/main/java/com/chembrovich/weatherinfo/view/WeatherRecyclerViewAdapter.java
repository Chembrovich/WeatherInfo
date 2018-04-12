package com.chembrovich.weatherinfo.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chembrovich.weatherinfo.R;
import com.chembrovich.weatherinfo.presenter.interfaces.WeatherPresenterInterface;

public class WeatherRecyclerViewAdapter extends RecyclerView.Adapter<WeatherRecyclerViewAdapter.ViewHolder> {
    private WeatherPresenterInterface presenter;
    private final WeatherFragment.OnWeatherFragmentInteractionListener listener;

    WeatherRecyclerViewAdapter(WeatherPresenterInterface presenter, WeatherFragment.OnWeatherFragmentInteractionListener listener) {
        this.presenter = presenter;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.weekDay.setText(presenter.getListItemWeekDay(position));
        holder.dayWithMonth.setText(presenter.getListItemDayWithTime(position));
        holder.stateText.setText(presenter.getListItemWeatherState(position));
        holder.temperature.setText(presenter.getListItemTemperature(position));
        holder.windSpeed.setText(presenter.getListItemWindSpeed(position));
        holder.humidity.setText(presenter.getListItemHumidity(position));
        holder.cloudiness.setText(presenter.getListItemCloudiness(position));

        ImageView stateImage = holder.stateImage;

        int resourceId = StateImageHandler.getImageResourceId(presenter.getListItemImageDescription(position));
        stateImage.setImageResource(resourceId);
    }

    @Override
    public int getItemCount() {
        return presenter.getWeatherListSize();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView weekDay;
        final TextView dayWithMonth;
        final ImageView stateImage;
        final TextView stateText;
        final TextView temperature;
        final TextView windSpeed;
        final TextView humidity;
        final TextView cloudiness;

        ViewHolder(View view) {
            super(view);
            weekDay =  view.findViewById(R.id.text_view_week_day);
            dayWithMonth = view.findViewById(R.id.text_view_day);
            stateImage =  view.findViewById(R.id.image_view_state);
            stateText = view.findViewById(R.id.text_view_state);
            temperature = view.findViewById(R.id.text_view_temperature);
            windSpeed = view.findViewById(R.id.text_view_wind_speed);
            humidity = view.findViewById(R.id.text_view_humidity);
            cloudiness = view.findViewById(R.id.text_view_cloudiness);
        }
    }
}
