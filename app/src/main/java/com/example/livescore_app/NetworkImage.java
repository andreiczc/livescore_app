package com.example.livescore_app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.net.URL;

public class NetworkImage extends AsyncTask<URL, Void, Bitmap> {
    public NetworkImage() {

    }

    @Override
    protected Bitmap doInBackground(URL... urls) {
        Bitmap bmp = null;

        try {
            bmp = BitmapFactory.decodeStream(urls[0].openConnection().getInputStream());
        } catch(Exception e) {
            e.printStackTrace();
        }

        return bmp;
    }
}
