package com.pavelpotapov.guessthecelebrity.view.activity.game;

import android.graphics.Bitmap;

import java.util.List;
import java.util.Map;

public interface IGameActivity {
    void showQuestion(Map<Bitmap, List<String>> question);
    void showAnswer(Map<Boolean, String> answer);
}
