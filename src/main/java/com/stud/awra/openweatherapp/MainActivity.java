package com.stud.awra.openweatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.stud.awra.openweatherapp.model.ApiOpenWeather;
import com.stud.awra.openweatherapp.model.Result5;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

  public static final int REQUEST_CODE = 556;
  public static final String LANTITUDE = "com.stud.awra.openweatherapp.lantitude";
  public static final String LONGITUDE = "com.stud.awra.openweatherapp.LONGITUDE";
  private MyAdapter adapter;
  private FloatingActionButton fab;
  private Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    fab = findViewById(R.id.fab);
    fab.setOnClickListener(
        v -> startActivityForResult(new Intent(getApplicationContext(), MapsActivity.class),
            REQUEST_CODE));
    final RecyclerView recyclerView = findViewById(R.id.recycler_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    adapter = new MyAdapter();
    recyclerView.setAdapter(adapter);
    getWeather("kiev");
  }

  private void getWeather(String name) {
    App.getApiOpenWeather()
        .getWeatherForDay5(name, ApiOpenWeather.UNITS, ApiOpenWeather.LANG,
            ApiOpenWeather.API_KEY, 40).enqueue(new Callback<Result5>() {
      @Override public void onResponse(Call<Result5> call, Response<Result5> response) {
        if (response.isSuccessful() && response.errorBody() == null) {
          if (response.body() != null) {
            adapter.setData(response.body().getListWeather());
            toolbar.setTitle(response.body().getCity().getName());
          }
        } else {
          Snackbar.make(fab, response.errorBody().toString(), Snackbar.LENGTH_INDEFINITE).show();
        }
      }

      @Override public void onFailure(Call<Result5> call, Throwable t) {
        Snackbar.make(fab, t.getMessage(), Snackbar.LENGTH_INDEFINITE).show();
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    if(requestCode==REQUEST_CODE&&resultCode==RESULT_OK&&data!=null){
      getWeather(data.getDoubleExtra(LANTITUDE,0),data.getDoubleExtra(LONGITUDE,0));
    }
  }

  private void getWeather(double lan, double lon) {
    App.getApiOpenWeather()
        .getWeatherForDay5(lan, lon,ApiOpenWeather.UNITS, ApiOpenWeather.LANG,
            ApiOpenWeather.API_KEY, 40).enqueue(new Callback<Result5>() {
      @Override public void onResponse(Call<Result5> call, Response<Result5> response) {
        if (response.isSuccessful() && response.errorBody() == null) {
          if (response.body() != null) {
            adapter.setData(response.body().getListWeather());
            toolbar.setTitle(response.body().getCity().getName());
          }
        } else {
          Snackbar.make(fab, response.errorBody().toString(), Snackbar.LENGTH_INDEFINITE).show();
        }
      }

      @Override public void onFailure(Call<Result5> call, Throwable t) {
        Snackbar.make(fab, t.getMessage(), Snackbar.LENGTH_INDEFINITE).show();
      }
    });
  }
}
