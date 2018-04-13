package com.chembrovich.weatherinfo;

import com.chembrovich.weatherinfo.interactor.interfaces.WeatherInteractorInterface;
import com.chembrovich.weatherinfo.model.MainParameters;
import com.chembrovich.weatherinfo.model.WeatherListItem;
import com.chembrovich.weatherinfo.model.Wind;
import com.chembrovich.weatherinfo.presenter.WeatherPresenter;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExampleUnitTest {
    private static final int APRIL_13_FRI_UNIX_TIME = 1523610000;
    private static final String FRIDAY = "Fri";
    private static final String APRIL_13 = "13 Apr 12:00";
    private WeatherPresenter presenter;
    private List<WeatherListItem> weatherList;

    @Before
    public void setUpPresenter() {
        weatherList = mock(ArrayList.class);
        when(weatherList.get(anyInt())).thenReturn(mock(WeatherListItem.class));
        when(weatherList.get(anyInt()).getMainParameters()).thenReturn(mock(MainParameters.class));
        when(weatherList.get(anyInt()).getWind()).thenReturn(mock(Wind.class));

        WeatherInteractorInterface interactor = mock(WeatherInteractorInterface.class);

        presenter = new WeatherPresenter(interactor);
        presenter.setWeatherList(weatherList);
    }

    @Test
    public void dayOfWeekEvaluating_isCorrect() throws Exception {
        when(weatherList.get(anyInt()).getWeatherForecastTime()).thenReturn(APRIL_13_FRI_UNIX_TIME);
        assertEquals(FRIDAY, presenter.getListItemWeekDay(anyInt()));
    }

    @Test
    public void dayWithMonthEvaluating_isCorrect() throws Exception {
        when(weatherList.get(anyInt()).getWeatherForecastTime()).thenReturn(APRIL_13_FRI_UNIX_TIME);
        assertEquals(APRIL_13, presenter.getListItemDayWithTime(anyInt()));
    }

    @Test
    public void getTemperature_isCorrect() throws Exception {
        final double ROUNDING_UP_VALUE = 23.5;
        final double ROUNDING_DOWN_VALUE = 23.49;
        final String ROUNDING_UP_EXPECTED_RESULT = "24°C";
        final String ROUNDING_DOWN_EXPECTED_RESULT = "23°C";
        when(weatherList.get(anyInt()).getMainParameters().getTemperature()).thenReturn(ROUNDING_UP_VALUE);
        assertEquals(ROUNDING_UP_EXPECTED_RESULT,presenter.getListItemTemperature(anyInt()));

        when(weatherList.get(anyInt()).getMainParameters().getTemperature()).thenReturn(ROUNDING_DOWN_VALUE);
        assertEquals(ROUNDING_DOWN_EXPECTED_RESULT,presenter.getListItemTemperature(anyInt()));
    }

    @Test
    public void getWindSpeed_isCorrect() throws Exception {
        final double ROUNDING_UP_VALUE = 3.01;
        final double ROUNDING_DOWN_VALUE = 0.99;
        final String ROUNDING_UP_EXPECTED_RESULT = "3mph";
        final String ROUNDING_DOWN_EXPECTED_RESULT = "1mph";
        when(weatherList.get(anyInt()).getWind().getSpeed()).thenReturn(ROUNDING_UP_VALUE);
        assertEquals(ROUNDING_UP_EXPECTED_RESULT,presenter.getListItemWindSpeed(anyInt()));

        when(weatherList.get(anyInt()).getWind().getSpeed()).thenReturn(ROUNDING_DOWN_VALUE);
        assertEquals(ROUNDING_DOWN_EXPECTED_RESULT,presenter.getListItemWindSpeed(anyInt()));
    }
}