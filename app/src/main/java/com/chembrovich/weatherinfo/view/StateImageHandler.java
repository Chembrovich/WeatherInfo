package com.chembrovich.weatherinfo.view;

import com.chembrovich.weatherinfo.R;
import com.chembrovich.weatherinfo.model.WeatherState;

class StateImageHandler {
    static int getImageResourceId(WeatherState state) {
        switch (state) {
            case CLEAR_SKY:
                return R.drawable.clear_sky;
            case CLEAR_SKY_NIGHT:
                return R.drawable.clear_sky_night;
            case FEW_CLOUDS:
                return R.drawable.few_clouds;
            case FEW_CLOUDS_NIGHT:
                return R.drawable.few_clouds_night;
            case SCATTERED_CLOUDS:
                return R.drawable.scattered_clouds;
            case BROKEN_CLOUDS:
                return R.drawable.broken_clouds;
            case SHOWER_RAIN:
                return R.drawable.shower_rain;
            case RAIN:
                return R.drawable.rain;
            case RAIN_NIGHT:
                return R.drawable.rain_night;
            case THUNDERSTORM:
                return R.drawable.thunderstorm;
            case SNOW:
                return R.drawable.snow;
            case MIST:
                return R.drawable.mist;
            default:
                return R.drawable.clear_sky;
        }
    }
}
