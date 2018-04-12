package com.chembrovich.weatherinfo.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {WeatherDbEntity.class}, version = 1)
public abstract class WeatherDatabase extends RoomDatabase{
    private static final String DATABASE_NAME = "weather_database";
    private static WeatherDatabase instance;

    public abstract WeatherDao weatherDao();

    static WeatherDatabase getInstance(Context appContext) {
        if (instance == null) {
            instance = Room.databaseBuilder(appContext, WeatherDatabase.class, DATABASE_NAME).build();
        }
        return instance;
    }

    static void destroyInstance() {
        instance = null;
    }
}
