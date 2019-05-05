package com.stud.awra.openweatherapp;

import android.app.Application;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
  private static ApiOpenWeather apiOpenWeather;

  public static ApiOpenWeather getApiOpenWeather() {
    if (apiOpenWeather == null) {
      apiOpenWeather =
          new Retrofit.Builder()
              .baseUrl("http://api.openweathermap.org/")
              .addConverterFactory(GsonConverterFactory.create())
              .build()
              .create(ApiOpenWeather.class);
    }
    return apiOpenWeather;
  }
}
