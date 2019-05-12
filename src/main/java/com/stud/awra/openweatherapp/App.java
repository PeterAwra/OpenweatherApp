package com.stud.awra.openweatherapp;

import android.app.Application;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.stud.awra.openweatherapp.model.ApiOpenWeather;
import com.stud.awra.openweatherapp.model.Repository;
import com.stud.awra.openweatherapp.model.RepositoryImpl;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
  private static Repository repository;

  public static Repository getRepository() {
    if (repository == null) {
      repository=new RepositoryImpl();
    }
    return repository;
  }
}