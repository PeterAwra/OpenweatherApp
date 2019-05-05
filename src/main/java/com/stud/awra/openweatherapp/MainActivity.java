package com.stud.awra.openweatherapp;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.stud.awra.openweatherapp.response5day.Result5day;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

  private MyAdapter adapter;
  private FloatingActionButton fab;
  private Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    final RecyclerView recyclerView = findViewById(R.id.recycler_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    adapter = new MyAdapter();
    recyclerView.setAdapter(adapter);

    fab = findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        getWeather();
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });
    getWeather();
  }

  private void getWeather() {
    App.getApiOpenWeather()
        .getWeatherForDay5("kiev", ApiOpenWeather.UNITS, ApiOpenWeather.LANG,
            ApiOpenWeather.API_KEY, 40).enqueue(new Callback<Result5day>() {
      @Override public void onResponse(Call<Result5day> call, Response<Result5day> response) {
        if (response.isSuccessful() && response.errorBody() == null) {
          if (response.body() != null) {
            adapter.setData(response.body().getListWeather());
            toolbar.setTitle(response.body().getCity().getName());
          }
        } else {
          Snackbar.make(fab, response.errorBody().toString(), Snackbar.LENGTH_INDEFINITE).show();
        }
      }

      @Override public void onFailure(Call<Result5day> call, Throwable t) {
        Snackbar.make(fab, t.getMessage(), Snackbar.LENGTH_INDEFINITE).show();
      }
    });
  }

  //@Override
  //public boolean onCreateOptionsMenu(Menu menu) {
  //  getMenuInflater().inflate(R.menu.menu_main, menu);
  //  return true;
  //}
  //
  //@Override
  //public boolean onOptionsItemSelected(MenuItem item) {
  //  int id = item.getItemId();
  //
  //  if (id == R.id.action_settings) {
  //    return true;
  //  }
  //
  //  return super.onOptionsItemSelected(item);
  //}
}
