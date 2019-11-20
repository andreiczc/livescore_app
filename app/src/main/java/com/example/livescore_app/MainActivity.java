package com.example.livescore_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private Network net;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            net = new Network();
            net.execute(new URL("http://api.football-data.org/v2/matches"));
        } catch (Exception e) {
            Log.e("errorNetwork", e.getMessage());
        }
    }

    public void showMatches(View view) {
        Intent intent = new Intent(this, ShowMatchesActivity.class);
        intent.putExtra("json", net.getObjResult().toString());

        startActivity(intent);
    }
}
