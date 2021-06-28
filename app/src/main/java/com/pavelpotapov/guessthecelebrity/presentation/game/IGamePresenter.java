package com.pavelpotapov.guessthecelebrity.presentation.game;

public interface IGamePresenter {
    void onClickAnswer(String tag);
    void startGame();
    void onDestroy();
}
