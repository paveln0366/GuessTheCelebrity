package com.pavelpotapov.guessthecelebrity;

import android.graphics.Bitmap;

import java.util.List;
import java.util.Map;

public class Presenter implements Contract.Presenter {

    private Contract.View mView;
    private Contract.Repository mRepository;

    public Presenter(Contract.View mView) {
        this.mView = mView;
        this.mRepository = new Repository();
    }

    @Override
    public void onClickAnswer(String tag) {
        Map<Boolean, String> answer = mRepository.checkAnswer(tag);
        mView.showAnswer(answer);
        startGame();
    }

    @Override
    public void startGame() {
        Map<Bitmap, List<String>> question = mRepository.createQuestion();
        mView.showQuestion(question);
    }

    @Override
    public void onDestroy() {

    }
}
