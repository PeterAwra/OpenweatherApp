package com.stud.awra.openweatherapp.model;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepositoryImpl implements Repository {

  private ApiOpenWeather apiOpenWeather;

  public RepositoryImpl() {
    apiOpenWeather =
        new Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ApiOpenWeather.class);
    ;
  }

  @Override public Observable<List<ListWeather>> getWeather5Days(String name) {
    return
        apiOpenWeather
            .getWeatherForDay5(name,
                ApiOpenWeather.UNITS,
                ApiOpenWeather.LANG,
                ApiOpenWeather.API_KEY,
                40).observeOn(Schedulers.io())
            .map(Result5::getListWeather)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io());
  }

  @Override public Observable<List<ListWeather>> getWeather5Days(double lan, double lon) {
    return apiOpenWeather
        .getWeatherForDay5(lan, lon,
            ApiOpenWeather.UNITS,
            ApiOpenWeather.LANG,
            ApiOpenWeather.API_KEY,
            40)
        .observeOn(Schedulers.io())
        .map(Result5::getListWeather)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io());
  }

  @Override public Observable<Weather> getWeatherCurrent(double lan, double lon) {
    return apiOpenWeather
        .getCurrentWeather(lan, lon,
            ApiOpenWeather.UNITS,
            ApiOpenWeather.LANG,
            ApiOpenWeather.API_KEY)
        .observeOn(Schedulers.io())
        .map(result -> result.getWeather().get(0))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io());
  }

  @Override public Observable<Weather> getWeatherCurrent(String name) {
    return apiOpenWeather
        .getCurrentWeather(name,
            ApiOpenWeather.UNITS,
            ApiOpenWeather.LANG,
            ApiOpenWeather.API_KEY).
            observeOn(Schedulers.io())
        .map(result -> result.getWeather().get(0))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io());
  }
}
