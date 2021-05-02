package com.pavelpotapov.guessthecelebrity.presenter.activity.game;

public interface IGamePresenter {
    void onClickAnswer(String tag);
    void startGame();
    void onDestroy();
}
