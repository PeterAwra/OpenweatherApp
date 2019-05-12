package com.stud.awra.openweatherapp.model;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiOpenWeather {
  String UNITS = "metric";
  String LANG = "ru";
  String API_KEY = "4a84c136ad6adced416bcf89cd1d95d1";

  @GET("data/2.5/forecast")
  Call<Result5> getWeatherForDay5(
      @Query("lat") double lat,
      @Query("lon") double lon,
      @Query("units") String units,
      @Query("lang") String lang,
      @Query("appid") String apiKey,
      @Query("cnt") Integer cnt
  );
  @GET("data/2.5/forecast")
  Call<Result5> getWeatherForDay5(
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