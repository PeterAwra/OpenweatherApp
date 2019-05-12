package com.stud.awra.openweatherapp.model;

import android.text.format.DateFormat;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class ListWeather {

  @SerializedName("dt")
  @Expose
  private Integer dt;
  @SerializedName("main")
  @Expose
  private Main main;
  @SerializedName("weather")
  @Expose
  private java.util.List<Weather> weather = null;
  @SerializedName("clouds")
  @Expose
  private Clouds clouds;
  @SerializedName("wind")
  @Expose
  private Wind wind;
  @SerializedName("sys")
  @Expose
  private Sys sys;
  @SerializedName("dt_txt")
  @Expose
  private String dtTxt;
  @SerializedName("rain")
  @Expose
  private Rain rain;
  private String nameCity;

  public Integer getDt() {
    return dt;
  }

  public void setDt(Integer dt) {
    this.dt = dt;
  }

  public Main getMain() {
    return main;
  }

  public void setMain(Main main) {
    this.main = main;
  }

  public java.util.List<Weather> getWeather() {
    return weather;
  }

  public void setWeather(java.util.List<Weather> weather) {
    this.weather = weather;
  }

  public Clouds getClouds() {
    return clouds;
  }

  public void setClouds(Clouds clouds) {
    this.clouds = clouds;
  }

  public Wind getWind() {
    return wind;
  }

  public void setWind(Wind wind) {
    this.wind = wind;
  }

  public Sys getSys() {
    return sys;
  }

  public void setSys(Sys sys) {
    this.sys = sys;
  }

  public String getDtTxt() {
    return dtTxt;
  }

  public void setDtTxt(String dtTxt) {
    this.dtTxt = dtTxt;
  }

  public Rain getRain() {
    return rain;
  }

  public void setRain(Rain rain) {
    this.rain = rain;
  }

  public String getDescription() {
    return weather.get(0).getDescription().toUpperCase();
  }

  public String getMinMaxTemp() {
    return main.getTempMax() + "\u00b0  " + main.getTempMin() + "\u00b0";
  }

  public String getDayName() {
    String result = "";
    long inDate = (long) dt * 1000 - TimeZone.getDefault().getRawOffset();
    Calendar instance1 = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
    Calendar calendar2 = Calendar.getInstance();
    calendar2.setTimeInMillis(inDate);
    int today = instance1.get(Calendar.DAY_OF_MONTH);
    if (today == calendar2.get(Calendar.DAY_OF_MONTH)) {
      result = "Today";
    } else if ((today + 1) == calendar2.get(Calendar.DAY_OF_MONTH)) {
      result = "Yesterday";
    } else {
      result =  DateFormat.format("EEEE", inDate).toString().toUpperCase();
    }
    return DateFormat.format("HH:mm", inDate) + "  " + result;
  }

  public void setNameCity(String nameCity) {
    this.nameCity = nameCity;
  }

  public String getCity() {
    return nameCity;
  }
}
