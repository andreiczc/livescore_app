package com.example.livescore_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowMatchesByDateActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private JSONObject obj;

    private ArrayList<Match> matches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_matches_by_date);

        Intent intent = getIntent();
        try {
            obj = new JSONObject(intent.getStringExtra("json"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        matches = parseJson(obj);

        if(matches.size() > 0) {
            adapter = new MatchAdapter(matches);
        } else {
            TextView tv = new TextView(this);
            tv.setText("There are no matches in the selected period");
            tv.setTextSize(16);

            LinearLayout layout = findViewById(R.id.linearLay);
            layout.addView(tv);
        }
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<Match> parseJson(JSONObject obj) {
        ArrayList<Match> matches = new ArrayList<>();
        try {
            JSONArray matchesArray = obj.getJSONArray("matches");
            for(int i = 0; i < matchesArray.length(); i++) {
                JSONObject current = matchesArray.getJSONObject(i);

                Match match = null;

                /*int homeTeamId = current.getJSONObject("homeTeam").getInt("id");
                int awayTeamId = current.getJSONObject("awayTeam").getInt("id");
                final String request = "https://api.football-data.org/v2/teams/";

                Network netHome = new Network();
                netHome.execute(new URL(request + homeTeamId));

                Network netAway = new Network();
                netAway.execute(new URL(request + awayTeamId));

                String homeUrl = netHome.getObjResult().getString("crestUrl");
                String awayUrl = netAway.getObjResult().getString("crestUrl");*/

                String homeUrl = null;
                String awayUrl = null;
                // not enough api calls

                if(current.getString("status").equalsIgnoreCase("finished")) {
                    match = new Match(
                            current.getJSONObject("competition").getString("name"),
                            current.getString("utcDate"),
                            true,
                            current.getJSONObject("homeTeam").getString("name"),
                            current.getJSONObject("awayTeam").getString("name"),
                            current.getJSONObject("score").getJSONObject("fullTime").getInt("homeTeam"),
                            current.getJSONObject("score").getJSONObject("fullTime").getInt("awayTeam"),
                            homeUrl,
                            awayUrl
                    );
                } else {
                    match = new Match(
                            current.getJSONObject("competition").getString("name"),
                            current.getString("utcDate"),
                            false,
                            current.getJSONObject("homeTeam").getString("name"),
                            current.getJSONObject("awayTeam").getString("name"),
                            homeUrl,
                            awayUrl
                    );
                }

                matches.add(match);
            }
        } catch(Exception e) {
            Log.e("parseJson", e.getMessage());
        }

        return matches;
    }
}
