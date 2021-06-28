package com.pavelpotapov.guessthecelebrity.presentation.game;

import android.graphics.Bitmap;

import com.pavelpotapov.guessthecelebrity.model.IRepositoty;
import com.pavelpotapov.guessthecelebrity.model.Repository;

import java.util.List;
import java.util.Map;

public class GamePresenter implements IGamePresenter {

    private GameView mView;
    private IRepositoty mRepository;

    public GamePresenter(GameView mView) {
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
