package com.pavelpotapov.guessthecelebrity.presenter.activity.game;

import android.graphics.Bitmap;

import com.pavelpotapov.guessthecelebrity.model.IRepositoty;
import com.pavelpotapov.guessthecelebrity.model.Repository;
import com.pavelpotapov.guessthecelebrity.view.activity.game.IGameActivity;

import java.util.List;
import java.util.Map;

public class GamePresenter implements IGamePresenter {

    private IGameActivity mView;
    private IRepositoty mRepository;

    public GamePresenter(IGameActivity mView) {
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
