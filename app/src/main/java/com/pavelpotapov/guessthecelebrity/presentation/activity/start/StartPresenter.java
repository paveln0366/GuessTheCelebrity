package com.pavelpotapov.guessthecelebrity.presentation.activity.start;

import android.graphics.Bitmap;

import com.pavelpotapov.guessthecelebrity.repository.AppRepository;

import java.util.List;
import java.util.Map;

public class StartPresenter implements StartContract.Presenter {

    private StartContract.View mView;
    private StartContract.Repository mRepository;

    public StartPresenter(StartContract.View mView) {
        this.mView = mView;
        this.mRepository = new AppRepository();
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
