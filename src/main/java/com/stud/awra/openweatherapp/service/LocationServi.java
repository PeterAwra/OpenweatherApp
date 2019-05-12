package com.stud.awra.openweatherapp.service;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.maps.model.LatLng;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class LocationServi {
  private static final int REQUEST_CODE_PERMISSION = 555;
  private MyLocationInt myLocationInt;
  private LatLng deflatLng = new LatLng(47.5414, 31.34);
  private Context context;

  public LocationServi attach(Context context) {
    this.context = context;
    if (context instanceof MyLocationInt) {
      myLocationInt = (MyLocationInt) context;
    } else {
      throw new UnsupportedOperationException(
          context.getClass().getSimpleName() + "must implement MyLocationInt");
    }
    return this;
  }

  private LatLng getLocation() {
    LocationManager locationManager =
        (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    List<String> allProviders = locationManager.getAllProviders();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
          != PackageManager.PERMISSION_GRANTED
          && context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
          != PackageManager.PERMISSION_GRANTED) {
        ((Activity) context).requestPermissions(new String[] {
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

  public void moveToLocation() {
    LatLng location = getLocation();
    if (location != null) {
      moveToLocation(location);
    } else {
      moveToLocation(deflatLng);
    }
  }

  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
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

  public void moveToLocation(LatLng latLng) {
    Geocoder geocoder = new Geocoder(context, Locale.getDefault());
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
      Toast.makeText(context, "Try again)", Toast.LENGTH_SHORT).show();
    }
    if (address != null) {
      LatLng resultLatLng = new LatLng(address.getLatitude(), address.getLongitude());
      myLocationInt.setLocation(resultLatLng,
          String.format("%s, %s", address.getLocality(), address.getCountryName()));
    } else {
      Toast.makeText(context, "Try again)", Toast.LENGTH_SHORT).show();
    }
  }
}
