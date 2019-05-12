package com.stud.awra.openweatherapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.stud.awra.openweatherapp.model.RepositoryImpl;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

  public static final int REQUEST_CODE = 556;
  public static final String LANTITUDE = "com.stud.awra.openweatherapp.lantitude";
  public static final String LONGITUDE = "com.stud.awra.openweatherapp.LONGITUDE";
  private MyAdapter adapter;
  private FloatingActionButton fab;
  private Toolbar toolbar;
  private Disposable subscribe;

  @Override protected void onDestroy() {
    super.onDestroy();
    dispose();
  }

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

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
      getWeather(data.getDoubleExtra(LANTITUDE, 0), data.getDoubleExtra(LONGITUDE, 0));
    }
  }

  private void getWeather(double lan, double lon) {
    dispose();
    subscribe = App.getRepository().getWeather5Days(lan, lon)
        .subscribe(listWeathers -> {
              toolbar.setTitle(listWeathers.get(0).getCity());
              adapter.setData(listWeathers);
            },
            throwable -> Snackbar.make(fab, throwable.toString(), Snackbar.LENGTH_INDEFINITE)
                .show());
  }

  private void dispose() {
    if (!subscribe.isDisposed()) {
      subscribe.dispose();
    }
  }

  private void getWeather(String name) {
    subscribe = new RepositoryImpl().getWeather5Days(name)
        .subscribe(listWeathers -> {
              toolbar.setTitle(listWeathers.get(0).getCity());
              adapter.setData(listWeathers);
            },
            throwable -> Snackbar.make(fab, throwable.toString(), Snackbar.LENGTH_INDEFINITE)
                .show());
  }
}
