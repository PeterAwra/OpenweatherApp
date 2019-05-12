package com.stud.awra.openweatherapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

  public static final int REQUEST_CODE_PERMISSION = 555;
  private GoogleMap mMap;
  private MarkerOptions position;
  private TextView textViewLocation;
  private LatLng deflatLng = new LatLng(50.46, 30.345);
  private LatLng resultLatLng;
  ;

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
    fab.setOnClickListener(v -> moveToLocation());
  }

  private void moveToLocation() {
    LatLng location = getLocation();
    if (location != null) {
      moveToLocation(location);
    } else {
      moveToLocation(deflatLng);
    }
  }

  private LatLng getLocation() {
    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    List<String> allProviders = locationManager.getAllProviders();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
          != PackageManager.PERMISSION_GRANTED
          && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
          != PackageManager.PERMISSION_GRANTED) {
        requestPermissions(new String[] {
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
            },
            REQUEST_CODE_PERMISSION);
        return null;
      }
    }
    for (String provider : allProviders) {
      Location lastKnownLocation = locationManager.getLastKnownLocation(provider);
      if (lastKnownLocation != null) {
        return new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
      }
    }
    return deflatLng;
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;
    moveToLocation();
    mMap.setOnMapClickListener(this::moveToLocation);
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == REQUEST_CODE_PERMISSION) {
      for (int i = 0; i < permissions.length; i++) {
        if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)
            && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
          moveToLocation(Objects.requireNonNull(getLocation()));
          break;
        }
      }
    }
  }

  private void moveToLocation(LatLng latLng) {
    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
    Address address = null;
    try {
      List<Address> fromLocation = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 10);
      for (Address a : fromLocation) {
        if (a.getLocality() != null) {
          Log.d("tag", a.toString());
          address = a;
          break;
        }
      }
    } catch (IOException e) {
      Toast.makeText(this, "Try again)", Toast.LENGTH_SHORT).show();
    }
    if (address != null) {
      textViewLocation.setText(
          String.format("%s, %s", address.getLocality(), address.getCountryName()));
      if (position == null) {
        position = new MarkerOptions();
      } else {
        mMap.clear();
      }
      resultLatLng = new LatLng(address.getLatitude(), address.getLongitude());
      position.position(resultLatLng);
      mMap.addMarker(position.title(latLng.toString()));
      mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
      mMap.animateCamera(CameraUpdateFactory.zoomTo(8), 2000, null);
    } else {
      Toast.makeText(this, "Try again)", Toast.LENGTH_SHORT).show();
    }
  }
}
