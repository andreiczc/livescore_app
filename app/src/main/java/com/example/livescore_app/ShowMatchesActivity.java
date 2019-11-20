package com.example.livescore_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ShowMatchesActivity extends AppCompatActivity {
    private JSONObject matchesJson;
    private ArrayList<Match> matches;
    private LinearLayout layout;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_matches);

        layout = findViewById(R.id.linearLay);

        Intent intent = getIntent();
        try {
            matchesJson = new JSONObject(intent.getStringExtra("json"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        matches = parseJson(matchesJson);

        populateRecyclerView();
    }

    private ArrayList<Match> parseJson(JSONObject obj) {
        ArrayList<Match> matches = new ArrayList<>();
        try {
            JSONArray matchesArray = obj.getJSONArray("matches");
            for(int i = 0; i < matchesArray.length(); i++) {
                JSONObject current = matchesArray.getJSONObject(i);

                if(current.getString("status").equalsIgnoreCase("finished")) {
                    matches.add(new Match(
                            current.getJSONObject("competition").getString("name"),
                            current.getString("utcDate"),
                            true,
                            current.getJSONObject("homeTeam").getString("name"),
                            current.getJSONObject("awayTeam").getString("name"),
                            current.getJSONObject("tvScore").getJSONObject("fullTime").getInt("homeTeam"),
                            current.getJSONObject("tvScore").getJSONObject("fullTime").getInt("awayTeam"),
                            null,
                            null
                    ));
                } else {
                    matches.add(new Match(
                            current.getJSONObject("competition").getString("name"),
                            current.getString("utcDate"),
                            false,
                            current.getJSONObject("homeTeam").getString("name"),
                            current.getJSONObject("awayTeam").getString("name"),
                            null,
                            null
                    ));
                }

            }
        } catch(Exception e) {
            Log.e("parseJson", e.getMessage());
        }

        return matches;
    }

    private void populateRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        if(matches.size() != 0) {
            adapter = new MatchAdapter(matches);
        } else {
            ArrayList<Match> matchList = new ArrayList<>();

            matchList.add(new Match("Liga 1", "", true, "FC Dinamo",
                    "FC Steaua", 2, 0, null, null));
            matchList.add(new Match("Liga 1", "", true, "FC Dinamo",
                    "FC Steaua", 2, 0, null, null));
            matchList.add(new Match("Liga 1", "", true, "FC Dinamo",
                    "FC Steaua", 2, 0, null, null));
            matchList.add(new Match("Liga 1", "", true, "FC Dinamo",
                    "FC Steaua", 2, 0, null, null));

            adapter = new MatchAdapter(matchList);

            TextView tvPlaceHolder = new TextView(this);
            tvPlaceHolder.setText("Placeholder matches // No matches for the day");


        }

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
