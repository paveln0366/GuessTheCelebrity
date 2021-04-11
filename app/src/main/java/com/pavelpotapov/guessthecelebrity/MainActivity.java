package com.pavelpotapov.guessthecelebrity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private TextView button0;
    private TextView button1;
    private TextView button2;
    private TextView button3;

    private ImageView imageViewPhoto;

//    private static final String URL = "https://www.imdb.com/list/ls045252306/";
    // Новый URL
    private static final String URL = "https://guessthecelebrity526640109.wordpress.com/";

    private ArrayList<String> photos;
    private ArrayList<String> names;
    private ArrayList<TextView> buttons;

    private int numberOfQuestion;
    private int numberOfRightAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        imageViewPhoto = findViewById(R.id.imageViewPhoto);

        photos = new ArrayList<>();
        names = new ArrayList<>();
        buttons = new ArrayList<>();

        buttons.add(button0);
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);

        getContent();
        playGame();
    }

    private void getContent() {
        DownloadContentTask downloadContentTask = new DownloadContentTask();
        try {
            String name = downloadContentTask.execute(URL).get();
//            TimeUnit.SECONDS.sleep(5);
//            String start = "<h1 class=\"header list-name\">Top 100 Stars of 2018</h1>";
//            String finish = "<div class=\"list-create-widget\">";
            String start = "<div class=\"entry-content\">";
            String finish = "</div><!-- .entry-content -->";


            Pattern patternContent = Pattern.compile(start + "(.*?)" + finish);
            Matcher matcherContent = patternContent.matcher(name);
            String splitContent = "";
            while (matcherContent.find()) {
                splitContent = matcherContent.group(1);
            }
//            Pattern patternPhoto = Pattern.compile("src=\"(.*?)\"");
//            Pattern patternName = Pattern.compile("<img alt=\"(.*?)\"");
            Pattern patternPhoto = Pattern.compile("data-orig-file=\"(.*?)\" data-orig-size=");
            Pattern patternName = Pattern.compile("<figcaption>(.*?)</figcaption></figure>");
            Matcher matcherPhoto = patternPhoto.matcher(splitContent);
            Matcher matcherName = patternName.matcher(splitContent);
            while (matcherPhoto.find()) {
                photos.add(matcherPhoto.group(1));
                Log.d("TAG", matcherPhoto.group(1));
            }
            while (matcherName.find()) {
                names.add(matcherName.group(1));
                Log.d("TAG", matcherName.group(1));
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void playGame() {
        generateQuestion();
        DownloadPhotoTask downloadPhotoTask = new DownloadPhotoTask();
        try {
            Bitmap photo = downloadPhotoTask.execute(photos.get(numberOfQuestion)).get();
            if (photo != null) {
                imageViewPhoto.setImageBitmap(photo);
                for (int i = 0; i < buttons.size(); i++) {
                    if (i == numberOfRightAnswer) {
                        buttons.get(i).setText(names.get(numberOfQuestion));
                    } else {
                        int wrongAnswer = generateWrongAnswer();
                        buttons.get(i).setText(names.get(wrongAnswer));
                    }
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void generateQuestion() {
        numberOfQuestion = (int) (Math.random() * names.size());
        numberOfRightAnswer = (int) (Math.random() * buttons.size());
    }

    private int generateWrongAnswer() {
        return (int) (Math.random() * names.size());
    }

    public void onClickAnswer(View view) {
        TextView button = (TextView) view;
        String tag = button.getTag().toString();
        if (Integer.parseInt(tag) == numberOfRightAnswer) {
            Toast.makeText(this, R.string.answer_right, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.answer_wrong) + " " + names.get(numberOfQuestion), Toast.LENGTH_LONG).show();
        }
        playGame();
    }

    private static class DownloadContentTask extends AsyncTask<String, Void, String> {

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