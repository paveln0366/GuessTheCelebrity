package com.pavelpotapov.guessthecelebrity.presentation.activity.game;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pavelpotapov.guessthecelebrity.presentation.activity.start.StartPresenter;
import com.pavelpotapov.guessthecelebrity.R;
import com.pavelpotapov.guessthecelebrity.di.component.AppComponent;
import com.pavelpotapov.guessthecelebrity.di.module.AppModule;
import com.pavelpotapov.guessthecelebrity.di.component.DaggerAppComponent;
import com.pavelpotapov.guessthecelebrity.di.module.PresenterModule;
import com.pavelpotapov.guessthecelebrity.databinding.ActivityGameBinding;
import com.pavelpotapov.guessthecelebrity.presentation.activity.start.StartContract;
import com.pavelpotapov.guessthecelebrity.utils.services.SoundService;
import com.pavelpotapov.guessthecelebrity.utils.ScreenMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class GameActivity extends AppCompatActivity implements StartContract.View {

    private static final String EXTRA_INFO = "info";
    private ActivityGameBinding binding;
    private ImageView imageViewPhoto;
    private ArrayList<TextView> listOfAnswerButtons;

    // private Contract.Presenter mPresenter;
    @Inject
    StartContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // App.appComponent.inject(this);
        AppComponent appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .presenterModule(new PresenterModule(this))
                .build();

        listOfAnswerButtons = new ArrayList<>();
        listOfAnswerButtons.add(binding.button0);
        listOfAnswerButtons.add(binding.button1);
        listOfAnswerButtons.add(binding.button2);
        listOfAnswerButtons.add(binding.button3);

        mPresenter = new StartPresenter(this);
        mPresenter.startGame();
    }

    @Override
    public void showQuestion(Map<Bitmap, List<String>> question) {
        Bitmap photo = null;
        List<String> variantOfAnswers = new ArrayList<>();
        for (Bitmap key : question.keySet()) {
            photo = key;
            variantOfAnswers = question.get(key);
        }
        binding.imageViewPhoto.setImageBitmap(photo);
        for (int i = 0; i < listOfAnswerButtons.size(); i++) {
            TextView button = listOfAnswerButtons.get(i);
            button.setText(variantOfAnswers.get(i));
        }
    }

    @Override
    public void showAnswer(Map<Boolean, String> answer) {
        if (answer.containsKey(true)) {
            Toast.makeText(this, R.string.answer_right, Toast.LENGTH_SHORT).show();
        } else if (answer.containsKey(false)) {
            Toast.makeText(this, getString(R.string.answer_wrong) +
                    " " + answer.get(false), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    public void onClickAnswer(View button) {
        String tag = button.getTag().toString();
        mPresenter.onClickAnswer(tag);
    }

    public static Intent newIntent(Context context, String info) {
        Intent intent = new Intent(context, GameActivity.class);
        intent.putExtra(EXTRA_INFO, info);
        return intent;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            ScreenMode.Companion.hideSystemUI(getWindow());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = new Intent(this, SoundService.class);
        intent.setAction("ACTION_RESUME");
        startService(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        Intent intent = new Intent(this, SoundService.class);
        intent.setAction("ACTION_PAUSE");
        startService(intent);
    }
}