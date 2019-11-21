package com.example.livescore_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

        public View itemView;


        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);

            imageHome = itemView.findViewById(R.id.imageHome);
            imageAway = itemView.findViewById(R.id.imageAway);
            tvCompetitionName = itemView.findViewById(R.id.tvCompetitionName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvHome = itemView.findViewById(R.id.tvHome);
            tvAWay = itemView.findViewById(R.id.tvAway);
            tvScore = itemView.findViewById(R.id.tvScore);

            this.itemView = itemView;
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
        final Match currentItem = matches.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Do you want to save this match ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.savedMatches.add(currentItem);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        if (currentItem.getHomeTeamCrest() != null) {
            // extract img from url
        } else {
            holder.imageHome.setImageResource(R.mipmap.ic_ball);
        }

        if (currentItem.getAwayTeamCrest() != null) {
            // extract img from url
        } else {
            holder.imageAway.setImageResource(R.mipmap.ic_ball);
        }

        holder.tvHome.setText(currentItem.getHomeTeamName());
        holder.tvAWay.setText(currentItem.getAwayTeamName());
        holder.tvCompetitionName.setText(currentItem.getCompetitionName());

        if (currentItem.isFinished() == true) {
            holder.tvScore.setText(currentItem.getHomeTeamGoals() + " - " + currentItem.getAwayTeamGoals());
        } else {
            holder.tvDate.setText(currentItem.getEventDate());

            // TODO parse date
        }
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }
}
