package com.chembrovich.weatherinfo.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface WeatherDao {
    @Query("SELECT * FROM weather_item")
    List<WeatherDbEntity> getAll();

    @Update
    void updateAll(List<WeatherDbEntity> weatherEntities);

    @Insert
    void insertAll(List<WeatherDbEntity> weatherEntities);
}
