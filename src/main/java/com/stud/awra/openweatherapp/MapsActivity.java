package com.stud.awra.openweatherapp;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

  private GoogleMap mMap;
  private MarkerOptions position;
  private TextView textViewLocation;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maps);
    textViewLocation = findViewById(R.id.tv_location);
    SupportMapFragment mapFragment = new SupportMapFragment();
    getSupportFragmentManager()
        .beginTransaction().add(R.id.container_fragment, mapFragment, "map_fragment").commit();
    mapFragment.getMapAsync(this);
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;
    LatLng latLng = new LatLng(50.46, 30.345);
    moveToLocation(latLng);
    mMap.setOnMapClickListener(this::moveToLocation);
  }

  private void moveToLocation(LatLng latLng) {
    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
    Address address=null;
    try {
      List<Address> fromLocation = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 10);
      for (Address a : fromLocation) {
        if(a.getLocality()!=null){
          Log.d("tag",a.toString());
          address=a;
          break;}
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
      position.position(new LatLng(address.getLatitude(),address.getLongitude()));
      mMap.addMarker(position.title(latLng.toString()));
      mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
      mMap.animateCamera(CameraUpdateFactory.zoomTo(8), 2000, null);
    } else {
      Toast.makeText(this, "Try again)", Toast.LENGTH_SHORT).show();
    }
  }
}
