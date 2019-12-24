package com.example.livescore_app;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder> {

    private ArrayList<Match> matches;
    private String activityName;

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
    public void onBindViewHolder(@NonNull MatchViewHolder holder, final int position) {
        final Match currentItem = matches.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityManager am = (ActivityManager) view.getContext().getSystemService(Context.ACTIVITY_SERVICE);
                ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
                activityName = cn.toShortString();

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                if (activityName.contains("ShowMatchesActivity") || activityName.contains("ShowMatchesByDateActivity"))
                    builder.setTitle("Do you want to save this match ?");
                else if (activityName.contains("SavedMatchesActivity"))
                    builder.setTitle("Do you want to delete this match ?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (activityName.contains("ShowMatchesActivity") || activityName.contains("ShowMatchesByDateActivity")) {
                            MainActivity.database.dbDao().insertLeague(new League(currentItem.getCompetitionName()));
                            long id = MainActivity.database.dbDao().insertMatch(currentItem);
                            currentItem.setMatchId((int) id);
                            MainActivity.savedMatches.add(currentItem);
                        } else if (activityName.contains("SavedMatchesActivity")) {
                            MainActivity.savedMatches.remove(currentItem);
                            MainActivity.database.dbDao().deleteMatch(currentItem);
                            notifyItemRemoved(position);
                        }
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                });

                builder.create().show();
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
            Date date = parseDate(currentItem.getEventDate());

            int year = date.getYear() + 1900;
            int month = date.getMonth() + 1;
            String minutes = "";

            if (date.getMinutes() == 0) {
                minutes = "00";
            } else {
                minutes = Integer.toString(date.getMinutes());
            }

            holder.tvDate.setText(date.getDate() + "." + month + "." + year + " " + date.getHours() + ":" + minutes);
        }
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    private Date parseDate(String original) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            date = sdf.parse(original);
        } catch (Exception e) {
            Log.e("parseException", e.getMessage());
        }

        return date;
    }
}
