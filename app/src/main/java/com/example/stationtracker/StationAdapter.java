package com.example.stationtracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stationtracker.Model.Station;

import java.util.List;

public class StationAdapter extends RecyclerView.Adapter<StationAdapter.StationViewHolder> {
    private List<Station> stationList;

    // Constructor to initialize station list
    public StationAdapter(List<Station> stationList) {
        this.stationList = stationList;
    }

    @NonNull
    @Override
    public StationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each row
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stations_row, parent, false);
        return new StationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StationViewHolder holder, int position) {
        // Get the station at the current position
        Station station = stationList.get(position);
        // Bind data to the views
        holder.title.setText(station.getTitle());
        holder.line.setText(station.getLine());
        holder.address.setText(station.getAddress());
    }

    @Override
    public int getItemCount() {
        // Return the total number of stations
        return stationList.size();
    }

    // ViewHolder class to hold the views for each station row
    public static class StationViewHolder extends RecyclerView.ViewHolder {
        TextView title, line, address;

        public StationViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.stationTitle);
            line = itemView.findViewById(R.id.stationLine);
            address = itemView.findViewById(R.id.stationAddress);
        }
    }
}
