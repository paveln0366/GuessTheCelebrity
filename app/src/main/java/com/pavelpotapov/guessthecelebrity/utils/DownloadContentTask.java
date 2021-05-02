package com.pavelpotapov.guessthecelebrity.utils;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadContentTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... strings) {

        URL url = null;
        HttpURLConnection connection = null;
        StringBuilder content = new StringBuilder();

        try {
            url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                content.append(line);
                line = bufferedReader.readLine();
            }
            return content.toString();
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