package com.jhprog.easykino;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.CardViewHolder> {
    private ArrayList<Show> shows;

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewTitle;
        public TextView textViewStartTime;
        public TextView textViewTheatres;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.show_item_title);
            textViewStartTime = (TextView) itemView.findViewById(R.id.show_item_start_time);
            textViewTheatres = (TextView) itemView.findViewById(R.id.show_item_theatres);
        }
    }

    public ShowAdapter(ArrayList<Show> showArrayList) {
        shows = showArrayList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.show_recycler_item, parent, false);
        return new CardViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        String timeString = "Starts at:";
        int calCount = shows.get(position).getStartDT().size();
        Calendar c;

        if (calCount > 1) {
            for (int i = 0; i < calCount; i++) {
                c = shows.get(position).getStartDT().get(i);
                if (i == (calCount - 1)) {
                    timeString += String.format(Locale.getDefault(), " %02d:%02d", c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
                } else {
                    timeString += String.format(Locale.getDefault(), " %02d:%02d,", c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
                }

            }
        } else {
            c = shows.get(position).getStartDT().get(0);
            timeString = String.format(Locale.getDefault(), "Starts at: %02d:%02d", c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
        }

        holder.textViewTitle.setText(shows.get(position).getTitle());
        holder.textViewStartTime.setText(timeString);
        holder.textViewTheatres.setText(shows.get(position).getLocationAndName());
    }

    @Override
    public int getItemCount() {
        return shows.size();
    }
}