package com.pavelpotapov.guessthecelebrity.model;

import android.graphics.Bitmap;

import java.util.List;
import java.util.Map;

public interface IRepositoty {
    Map<Bitmap, List<String>> createQuestion();
    void getContent();
    Map<Boolean, String> checkAnswer(String tag);
}
