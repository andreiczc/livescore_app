package com.example.livescore_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SavedMatchesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private LinearLayout layout;
    private ArrayList<Match> matches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_matches);

        layout = findViewById(R.id.linearLay);
        Intent intent = getIntent();
        matches = (ArrayList<Match>) MainActivity.savedMatches;

        populateRecyclerView();
    }

    private void populateRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);

        if (matches != null && matches.size() != 0) {
            adapter = new MatchAdapter(matches);
        } else {
            TextView tv = new TextView(this);
            tv.setText("You haven't saved any matches yet.\nTo save a match you need to tap on the match that you'd like to save.");
            tv.setTextSize(16);

            try {
                layout.addView(tv);
            } catch (Exception e) {
                Toast.makeText(this, "wait for it", Toast.LENGTH_LONG).show();
            }
        }

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
