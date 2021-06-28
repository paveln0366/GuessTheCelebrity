package com.pavelpotapov.guessthecelebrity.ui.game;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.pavelpotapov.guessthecelebrity.R;
import com.pavelpotapov.guessthecelebrity.databinding.ActivityGameBinding;
import com.pavelpotapov.guessthecelebrity.presentation.game.GamePresenter;
import com.pavelpotapov.guessthecelebrity.presentation.game.GameView;
import com.pavelpotapov.guessthecelebrity.service.SoundService;
import com.pavelpotapov.guessthecelebrity.util.ScreenMode;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity implements GameView {
    private AdView adView;
    private static final String AD_TEST_ID = "ca-app-pub-3940256099942544/6300978111";
    private static final String AD_ID = "ca-app-pub-5839831086467167/3140273583";

    private static final String EXTRA_INFO = "info";
    private ActivityGameBinding binding;
    private ArrayList<TextView> listOfAnswerButtons;

    private GamePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.adContainerView.setVisibility(View.GONE);
        adView = new AdView(this);
        adView.setAdUnitId(AD_TEST_ID);
        adView.setAdSize(adSize());
        binding.adContainerView.addView(adView);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull @NotNull LoadAdError loadAdError) {
                Log.d("admob", "Loading banner is failed");
                binding.adContainerView.setVisibility(View.GONE);
            }

            @Override
            public void onAdLoaded() {
                Log.d("admob", "Banner is loaded");
                binding.adContainerView.setVisibility(View.VISIBLE);
            }
        });

        // Инициализация SDK мобильной рекламы
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NotNull InitializationStatus initializationStatus) {
                loadAd();
                Log.i("TAG", "The ad initialization complete");
            }
        });


        listOfAnswerButtons = new ArrayList<>();
        listOfAnswerButtons.add(binding.button0);
        listOfAnswerButtons.add(binding.button1);
        listOfAnswerButtons.add(binding.button2);
        listOfAnswerButtons.add(binding.button3);

        presenter = new GamePresenter(this);
        presenter.startGame();
    }

    private void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private AdSize adSize() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        int adWidth = (int) (widthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
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
        presenter.onDestroy();
    }

    public void onClickAnswer(View button) {
        String tag = button.getTag().toString();
        presenter.onClickAnswer(tag);
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
            ScreenMode.hideSystemUI(getWindow());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = new Intent(this, SoundService.class);
        intent.setAction("resume");
        startService(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        Intent intent = new Intent(this, SoundService.class);
        intent.setAction("pause");
        startService(intent);
    }
}