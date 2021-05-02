package com.pavelpotapov.guessthecelebrity.presenter.start;

import android.graphics.Bitmap;

import com.pavelpotapov.guessthecelebrity.AppContract;
import com.pavelpotapov.guessthecelebrity.model.AppRepository;

import java.util.List;
import java.util.Map;

public class StartPresenter implements AppContract.Presenter {

    private AppContract.View mView;
    private AppContract.Repository mRepository;

    public StartPresenter(AppContract.View mView) {
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
