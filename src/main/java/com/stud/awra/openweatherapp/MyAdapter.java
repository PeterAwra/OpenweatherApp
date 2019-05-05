package com.stud.awra.openweatherapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.stud.awra.openweatherapp.response5day.ListWeather;
import java.util.ArrayList;
import java.util.List;

class MyAdapter extends RecyclerView.Adapter<MyHolder> {
  private List<ListWeather> data;

  public MyAdapter() {
    data = new ArrayList<>();
  }

  @NonNull @Override public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new MyHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_waether, parent, false));
  }

  @Override public void onBindViewHolder(@NonNull MyHolder holder, int position) {
    holder.setData(data.get(position));
  }

  @Override public int getItemCount() {
    return data.size();
  }

  public void setData(List<ListWeather> weather) {
    data.clear();
    data.addAll(weather);
    notifyDataSetChanged();
  }
}
