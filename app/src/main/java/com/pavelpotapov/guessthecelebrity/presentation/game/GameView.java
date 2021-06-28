package com.pavelpotapov.guessthecelebrity.presentation.game;

import android.graphics.Bitmap;

import java.util.List;
import java.util.Map;

public interface GameView {
    void showQuestion(Map<Bitmap, List<String>> question);
    void showAnswer(Map<Boolean, String> answer);
}
