package com.pavelpotapov.guessthecelebrity;

import android.graphics.Bitmap;

import java.util.List;
import java.util.Map;

public interface Contract {
    interface View {
        void showQuestion(Map<Bitmap, List<String>> question);

        void showAnswer(Map<Boolean, String> answer);
    }

    interface Presenter {
        void onClickAnswer(String tag);

        void startGame();

        void onDestroy();
    }

    interface Repository {
        Map<Bitmap, List<String>> createQuestion();

        void getContent();

        Map<Boolean, String> checkAnswer(String tag);
    }
}