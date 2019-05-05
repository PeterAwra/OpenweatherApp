package com.stud.awra.openweatherapp;

import com.stud.awra.openweatherapp.response.Result;
import com.stud.awra.openweatherapp.response5day.Result5day;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiOpenWeather {
  final String UNITS = "metric";
  final String LANG = "ru";
  final String API_KEY = "4a84c136ad6adced416bcf89cd1d95d1";

  @GET("data/2.5/forecast")
  Call<Result5day> getWeatherForDay5(
      @Query("q") String nameCity,
      @Query("units") String units,
      @Query("lang") String lang,
      @Query("appid") String apiKey,
      @Query("cnt") Integer cnt
  );
  @GET("data/2.5/weather")
  Call<Result> getCurrentWeather(
      @Query("q") String nameCity,
      @Query("units") String units,
      @Query("lang") String lang,
      @Query("appid") String apiKey);
}
