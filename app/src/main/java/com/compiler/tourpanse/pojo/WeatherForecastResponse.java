package com.compiler.tourpanse.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by User on 8/8/2016.
 */
public class WeatherForecastResponse {
    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("message")
    @Expose
    private Double message;
    @SerializedName("cnt")
    @Expose
    private Integer cnt;
    @SerializedName("list")
    @Expose
    private java.util.List<WeatherList> list = new ArrayList<WeatherList>();

    /**
     *
     * @return
     * The city
     */
    public City getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The city
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     *
     * @return
     * The cod
     */
    public String getCod() {
        return cod;
    }

    /**
     *
     * @param cod
     * The cod
     */
    public void setCod(String cod) {
        this.cod = cod;
    }

    /**
     *
     * @return
     * The message
     */
    public Double getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(Double message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The cnt
     */
    public Integer getCnt() {
        return cnt;
    }

    /**
     *
     * @param cnt
     * The cnt
     */
    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    /**
     *
     * @return
     * The list
     */
    public java.util.List<WeatherList> getList() {
        return list;
    }

    /**
     *
     * @param list
     * The list
     */
    public void setList(java.util.List<WeatherList> list) {
        this.list = list;
    }

    public String getCurrentTime(){
        WeatherList weatherList = new WeatherList();
        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm:ssa");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+6"));
        String currentTime=sdf.format((Date)new Date(weatherList.getDt()*1000));
        return currentTime;
    }

}
