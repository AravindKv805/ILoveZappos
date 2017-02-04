package com.paniaravindkv.ilovezappos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Pani Aravind on 2/4/17.
 */

public class ImageDownloadTask extends AsyncTask<String, Void, Bitmap> {

    @Override
    protected Bitmap doInBackground(String... urls) {

        try {
            URL url = new URL(urls[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();

            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            Log.i("INFO", "In imagedownloadtask - " + bitmap.toString());

            return bitmap;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
