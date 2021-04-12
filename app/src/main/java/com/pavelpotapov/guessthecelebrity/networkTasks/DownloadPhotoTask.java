package com.pavelpotapov.guessthecelebrity.networkTasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadPhotoTask extends AsyncTask<String, Void, Bitmap> {

    @Override
    protected Bitmap doInBackground(String... strings) {

        URL url = null;
        HttpURLConnection connection = null;

        try {
            url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            Bitmap photo = BitmapFactory.decodeStream(inputStream);
            return photo;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return null;
    }
}
