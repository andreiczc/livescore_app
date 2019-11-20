package com.example.livescore_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ShowMatchesActivity extends AppCompatActivity {
    private JSONObject matchesJson;
    private List<Match> matches;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_matches);

        Intent intent = getIntent();
        try {
            matchesJson = new JSONObject(intent.getStringExtra("json"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        matches = parseJson(matchesJson);

        StringBuilder show = new StringBuilder("");
        for(Match m : matches) {
            show.append(m.toString());
        }
    }

    private List<Match> parseJson(JSONObject obj) {
        List<Match> matches = new ArrayList<>();
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
                            current.getJSONObject("score").getJSONObject("fullTime").getInt("homeTeam"),
                            current.getJSONObject("score").getJSONObject("fullTime").getInt("awayTeam"),
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
}
