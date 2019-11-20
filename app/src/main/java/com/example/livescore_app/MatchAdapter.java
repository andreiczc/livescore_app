package com.example.livescore_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder> {

    private ArrayList<Match> matches;

    public static class MatchViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageHome;
        public ImageView imageAway;
        public TextView tvCompetitionName;
        public TextView tvDate;
        public TextView tvHome;
        public TextView tvAWay;
        public TextView tvScore;


        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);

            imageHome = itemView.findViewById(R.id.imageHome);
            imageAway = itemView.findViewById(R.id.imageAway);
            tvCompetitionName = itemView.findViewById(R.id.tvCompetitionName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvHome = itemView.findViewById(R.id.tvHome);
            tvAWay = itemView.findViewById(R.id.tvAway);
            tvScore = itemView.findViewById(R.id.tvScore);
        }
    }

    public MatchAdapter(ArrayList<Match> matches) {
        this.matches = matches;
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_item, parent, false);
        MatchViewHolder vh = new MatchViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        Match currentItem = matches.get(position);

        holder.imageHome.setImageResource(R.drawable.ic_android);
        holder.imageAway.setImageResource(R.drawable.ic_android);
        holder.tvHome.setText(currentItem.getHomeTeamName());
        holder.tvAWay.setText(currentItem.getAwayTeamName());
        holder.tvCompetitionName.setText(currentItem.getCompetitionName());

        if(currentItem.isFinished() == true) {
            holder.tvScore.setText(currentItem.getHomeTeamGoals() + " - " + currentItem.getAwayTeamGoals());
        } else {
            holder.tvDate.setText(currentItem.getEventDate());
        }
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }
}
