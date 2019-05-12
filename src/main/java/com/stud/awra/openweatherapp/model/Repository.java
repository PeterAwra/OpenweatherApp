package com.stud.awra.openweatherapp.model;

import io.reactivex.Observable;
import java.util.List;

public interface Repository {
  Observable<List<ListWeather>> getWeather5Days(String name);
  Observable<List<ListWeather>> getWeather5Days(double lan,double lon);
  Observable<Weather> getWeatherCurrent(double lan,double lon);
  Observable<Weather> getWeatherCurrent(String name);

}
