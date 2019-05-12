package com.stud.awra.openweatherapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.stud.awra.openweatherapp.service.LocationServi;
import com.stud.awra.openweatherapp.service.MyLocationInt;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, MyLocationInt {

  private GoogleMap mMap;
  private MarkerOptions position;
  private TextView textViewLocation;
  private LatLng resultLatLng;
  private LocationServi locationServi;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maps);
    textViewLocation = findViewById(R.id.tv_location);
    textViewLocation.setOnClickListener(v -> {
      setResult(RESULT_OK,
          getIntent().putExtra(MainActivity.LANTITUDE, resultLatLng.latitude)
              .putExtra(MainActivity.LONGITUDE, resultLatLng.longitude));
      finish();
    });
    SupportMapFragment mapFragment = new SupportMapFragment();
    getSupportFragmentManager()
        .beginTransaction().add(R.id.container_fragment, mapFragment, "map_fragment").commit();
    mapFragment.getMapAsync(this);
    FloatingActionButton fab = findViewById(R.id.fab1);
    fab.setOnClickListener(v -> locationServi.moveToLocation());
    locationServi = new LocationServi();
    locationServi.attach(this);
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;
    mMap.setOnMapClickListener(latLng -> locationServi.moveToLocation(latLng));
    locationServi.moveToLocation();
  }

  @Override public void setLocation(LatLng latLng, String name) {
    resultLatLng = latLng;
    textViewLocation.setText(name);
    if (position == null) {
      position = new MarkerOptions();
    } else {
      mMap.clear();
    }
    position.position(resultLatLng);
    mMap.addMarker(position.title(latLng.toString()));
    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    mMap.animateCamera(CameraUpdateFactory.zoomTo(8), 2000, null);
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    locationServi.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }
}
