package com.example.zizoj.retrofitfirstapp.RoomDatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "weathercity")
public class WeatherDatabaseModel {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "city_id")
    private int cityWeatherId ;
    @ColumnInfo(name = "city_name")
    private String city_name ;
    @ColumnInfo(name = "tempt")
    private String temp ;
    @ColumnInfo(name = "temp_date")
    private String temp_date ;
    @ColumnInfo(name = "temp_min")
    private String temp_min ;
    @ColumnInfo(name = "temp_max")
    private String temp_max ;
    @ColumnInfo(name = "condition")
    private String condition ;
    @ColumnInfo(name = "imgurl")
    private String imgurl ;
    @ColumnInfo(name = "forcastlist")
    private String forcastlist;

    public WeatherDatabaseModel( @NonNull String city_name, @NonNull String temp, @NonNull String temp_date, @NonNull String temp_min
            , @NonNull String temp_max, @NonNull String condition, @NonNull String imgurl , @NonNull String forcastlist) {
//        this.cityWeatherId = cityWeatherId;
        this.city_name = city_name;
        this.temp = temp;
        this.temp_date = temp_date;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.condition = condition;
        this.imgurl = imgurl;
        this.forcastlist = forcastlist;
    }

    public String getForcastlist() {
        return forcastlist;
    }

    public void setForcastlist(String forcastlist) {
        this.forcastlist = forcastlist;
    }

    @NonNull
    public int getCityWeatherId() {
        return cityWeatherId;
    }

    public void setCityWeatherId(@NonNull int cityWeatherId) {
        this.cityWeatherId = cityWeatherId;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTemp_date() {
        return temp_date;
    }

    public void setTemp_date(String temp_date) {
        this.temp_date = temp_date;
    }

    public String getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(String temp_min) {
        this.temp_min = temp_min;
    }

    public String getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(String temp_max) {
        this.temp_max = temp_max;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
