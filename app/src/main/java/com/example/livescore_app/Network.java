package com.example.livescore_app;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

public class Network extends AsyncTask<URL, Void, JSONObject> implements Serializable {
    private StringBuilder result;
    private JSONObject objResult;

    public Network() {
        result = new StringBuilder("");
    }

    @Override
    protected JSONObject doInBackground(URL... urls) {
        HttpURLConnection conn;
        try {
            conn = (HttpURLConnection) urls[0].openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-Auth-Token", "cb162e2800584b609e0128130c3d6cc3");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = reader.readLine();
            while(line != null) {
                result.append(line);
                line = reader.readLine();
            }
            reader.close();

            objResult = new JSONObject(result.toString());

            return objResult;
        } catch(Exception e) {
            Log.e("network", e.getMessage());
            return null;
        }
    }

    public JSONObject getObjResult() {
        return objResult;
    }
}
