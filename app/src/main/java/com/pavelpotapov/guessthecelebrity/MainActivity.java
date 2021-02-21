package com.pavelpotapov.guessthecelebrity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private ImageView imageViewCelebrity;
    private static final String URL = "https://www.imdb.com/list/ls045252306/";
    private ArrayList<String> photos;
    private ArrayList<String> names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        imageViewCelebrity = findViewById(R.id.imageViewCelebrity);
        photos = new ArrayList<>();
        names = new ArrayList<>();
        getContent();
    }

    private void getContent() {
        DownloadNameTask downloadNameTask = new DownloadNameTask();
        try {
            String name = downloadNameTask.execute(URL).get();
            String start = "<h1 class=\"header list-name\">Top 100 Stars of 2018</h1>";
            String finish = "<div class=\"list-create-widget\">";
            Pattern patternContent = Pattern.compile(start + "(.*?)" + finish);
            Matcher matcherContent = patternContent.matcher(name);
            String splitContent = "";
            while (matcherContent.find()) {
                splitContent = matcherContent.group(1);
            }
            Pattern patternPhoto = Pattern.compile("src=\"(.*?)\"");
            Pattern patternName = Pattern.compile("<img alt=\"(.*?)\"");
            Matcher matcherPhoto = patternPhoto.matcher(splitContent);
            Matcher matcherName = patternName.matcher(splitContent);

            while (matcherPhoto.find()) {
                photos.add(matcherPhoto.group(1));
            }
            while (matcherName.find()) {
                names.add(matcherName.group(1));
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class DownloadNameTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            HttpURLConnection connection = null;
            StringBuilder result = new StringBuilder();
            try {
                url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = bufferedReader.readLine();
                while (line != null) {
                    result.append(line);
                    line = bufferedReader.readLine();
                }
                return result.toString();
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

    private static class DownloadPhotoTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            URL url = null;
            HttpURLConnection connection = null;
            StringBuilder result = new StringBuilder();
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
}