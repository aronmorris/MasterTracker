package com.example.anthony.myapplication;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(indices = {@Index(value = {"year", "month", "day"},
        unique = true)})
public class Weather {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "year")
    private int year;

    @ColumnInfo(name = "month")
    private int month;

    @ColumnInfo(name = "day")
    private int day;

    @ColumnInfo(name = "maxTemp")
    private double maxTemp;

    @ColumnInfo(name = "minTemp")
    private double minTemp;

    @ColumnInfo(name = "meanTemp")
    private double meanTemp;

    @ColumnInfo(name = "totalRain_mm")
    private int totalRain_mm;

    @ColumnInfo(name = "windDir")
    private int windDir_10sDeg;

    @ColumnInfo(name = "windSpeed")
    private int windSpeed;

    public Weather(int year, int month, int day,
                   double maxTemp, double minTemp, double meanTemp,
                   int totalRain_mm, int windDir_10sDeg, int windSpeed) {
        this.setYear(year);
        this.setMonth(month);
        this.setDay(day);
        this.setMaxTemp(maxTemp);
        this.setMinTemp(minTemp);
        this.setMeanTemp(meanTemp);
        this.setTotalRain_mm(totalRain_mm);
        this.setWindDir_10sDeg(windDir_10sDeg);
        this.setWindSpeed(windSpeed);
    }
//comment
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMeanTemp() {
        return meanTemp;
    }

    public void setMeanTemp(double meanTemp) {
        this.meanTemp = meanTemp;
    }

    public int getTotalRain_mm() {
        return totalRain_mm;
    }

    public void setTotalRain_mm(int totalRain_mm) {
        this.totalRain_mm = totalRain_mm;
    }

    public int getWindDir_10sDeg() {
        return windDir_10sDeg;
    }

    public void setWindDir_10sDeg(int windDir_10sDeg) {
        this.windDir_10sDeg = windDir_10sDeg;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }
}