package com.chembrovich.weatherinfo.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface WeatherDao {
    @Query("SELECT * FROM weather_item")
    List<WeatherDbEntity> getAll();

    /*@Query("SELECT * FROM user WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);*/

    @Update
    void updateAll(List<WeatherDbEntity> weatherEntities);

    @Insert
    void insertAll(List<WeatherDbEntity> weatherEntities);

    @Delete
    void deleteAll(WeatherDbEntity[] weatherEntities);

    @Query("DELETE  FROM weather_item")
    void deleteAll();
}
