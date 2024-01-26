package com.example.trip.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.trip.R;
import com.example.trip.entity.Trip;

import java.util.ArrayList;

public class TripAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Trip> trips;
    private Context context;

    public TripAdapter(Context context, ArrayList<Trip> trips){
        this.context = context;
        this.trips = trips;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_item, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Trip trip = getItem(position);
        TripViewHolder tripViewHolder = (TripViewHolder) holder;

        tripViewHolder.trip_TV_name.setText(trip.getTitle());
        Glide.with(context).load(trip.getImageUrl()).into(tripViewHolder.trip_IV_image);
        tripViewHolder.trip_RB_rate.setRating(trip.getRate());
     }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    public Trip getItem(int i){
        return this.trips.get(i);
    }

    public class TripViewHolder extends  RecyclerView.ViewHolder {

        public ImageView trip_IV_image;
        public TextView trip_TV_name;
        public RatingBar trip_RB_rate;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            trip_IV_image = itemView.findViewById(R.id.trip_IV_image);
            trip_TV_name = itemView.findViewById(R.id.trip_TV_name);
            trip_RB_rate = itemView.findViewById(R.id.trip_RB_rate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
