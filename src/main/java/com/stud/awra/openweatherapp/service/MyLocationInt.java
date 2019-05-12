package com.stud.awra.openweatherapp.service;

import com.google.android.gms.maps.model.LatLng;

public interface MyLocationInt {
  void setLocation(LatLng latLng, String name);
}
