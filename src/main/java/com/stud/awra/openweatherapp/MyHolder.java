package com.stud.awra.openweatherapp;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.stud.awra.openweatherapp.model.ListWeather;

class MyHolder extends RecyclerView.ViewHolder {
  private final Context context;
  TextView t1;
  TextView t2;
  TextView t3;
  ImageView iV;

  public MyHolder(@NonNull View itemView) {
    super(itemView);
    context = itemView.getContext();
    t1 = itemView.findViewById(R.id.textView1);
    t2 = itemView.findViewById(R.id.textView2);
    t3 = itemView.findViewById(R.id.textView);
    iV = itemView.findViewById(R.id.image_icon);
  }

  public void setData(ListWeather weather) {
    Glide.with(context)
        .load("http://openweathermap.org/img/w/" + weather.getWeather().get(0).getIcon() + ".png")
        .into(iV);
    t1.setText(weather.getDayName());
    t2.setText(weather.getDescription());
    t3.setText(weather.getMinMaxTemp());
  }
}