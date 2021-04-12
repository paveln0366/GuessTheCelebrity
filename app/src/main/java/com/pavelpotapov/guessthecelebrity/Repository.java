package com.pavelpotapov.guessthecelebrity;

import android.graphics.Bitmap;
import android.util.Log;

import com.pavelpotapov.guessthecelebrity.networkTasks.DownloadContentTask;
import com.pavelpotapov.guessthecelebrity.networkTasks.DownloadPhotoTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Repository implements Contract.Repository {
    private static final String URL = "https://guessthecelebrity526640109.wordpress.com/";
    private static final int NUMBER_OF_ANSWERS = 4;

    private ArrayList<String> photos = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();

    private int indexOfRightName;
    private int indexOfRightButton;

    public Repository() {
        getContent();
    }

    private void generateQuestion() {
        indexOfRightName = (int) (Math.random() * names.size());
        indexOfRightButton = (int) (Math.random() * NUMBER_OF_ANSWERS);
    }

    private int generateWrongAnswer() {
        return (int) (Math.random() * names.size());
    }

    @Override
    public Map<Bitmap, List<String>> createQuestion() {
        Bitmap photo = null;
        generateQuestion();
        List<String> variantOfAnswers = new ArrayList<>(NUMBER_OF_ANSWERS);
        Map<Bitmap, List<String>> question = new HashMap<>();

        DownloadPhotoTask downloadPhotoTask = new DownloadPhotoTask();
        try {
            photo = downloadPhotoTask.execute(photos.get(indexOfRightName)).get();
            if (photo != null) {
                for (int i = 0; i < NUMBER_OF_ANSWERS; i++) {
                    if (i == indexOfRightButton) {
                        variantOfAnswers.add(names.get(indexOfRightName));
                    } else {
                        int indexOfWrongAnswer = generateWrongAnswer();
                        variantOfAnswers.add(names.get(indexOfWrongAnswer));
                    }
                }
                question.put(photo, variantOfAnswers);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return question;
    }

    @Override
    public void getContent() {
        DownloadContentTask downloadContentTask = new DownloadContentTask();
        try {
            String content = downloadContentTask.execute(URL).get();
            String start = "<div class=\"entry-content\">";
            String finish = "</div><!-- .entry-content -->";


            Pattern patternContent = Pattern.compile(start + "(.*?)" + finish);
            Matcher matcherContent = patternContent.matcher(content);

            String splitContent = "";
            while (matcherContent.find()) {
                splitContent = matcherContent.group(1);
            }

            Pattern patternPhoto = Pattern.compile("data-orig-file=\"(.*?)\" data-orig-size=");
            Pattern patternName = Pattern.compile("<figcaption>(.*?)</figcaption></figure>");

            Matcher matcherPhoto = patternPhoto.matcher(splitContent);
            Matcher matcherName = patternName.matcher(splitContent);

            while (matcherPhoto.find()) {
                photos.add(matcherPhoto.group(1));
                Log.d("GuessTheCelebrity", matcherPhoto.group(1));
            }

            while (matcherName.find()) {
                names.add(matcherName.group(1));
                Log.d("GuessTheCelebrity", matcherName.group(1));
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<Boolean, String> checkAnswer(String tag) {
        Map<Boolean, String> answer = new HashMap<>();
        if (Integer.parseInt(tag) == indexOfRightButton) {
            answer.put(true, null);
        } else {
            answer.put(false, names.get(indexOfRightName));
        }
        return answer;
    }
}
