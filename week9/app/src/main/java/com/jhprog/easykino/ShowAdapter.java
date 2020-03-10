package com.jhprog.easykino;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.CardViewHolder> {
    private ArrayList<Show> shows;

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.show_item_title);
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
        holder.textView.setText(shows.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return shows.size();
    }
}