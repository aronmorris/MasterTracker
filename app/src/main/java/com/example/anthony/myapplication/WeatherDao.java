package com.example.anthony.myapplication;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface WeatherDao {
    @Query("SELECT * FROM weather")
    List<Weather> getAll();

    @Query("SELECT * FROM weather WHERE id IN (:WeatherIds)")
    List<Weather> loadAllByIds(int[] WeatherIds);

    @Query("SELECT * FROM weather WHERE year LIKE :year AND "
            + "month LIKE :month AND day like :day LIMIT 1")
    Weather findByDate(int year, int month, int day);

    @Query("SELECT * FROM weather WHERE year LIKE :year AND "
            + "month LIKE :month ")
    List<Weather> findByYearMonth(int year, int month);

    @Insert
    void insertAll(Weather... weatherEntries);

    @Insert
    void insert(Weather weather);

    @Delete
    void delete(Weather weather);

    @Query("DELETE FROM weather")
    void deleteAll();
}
