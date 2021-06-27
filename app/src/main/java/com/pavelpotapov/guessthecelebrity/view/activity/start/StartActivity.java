package com.pavelpotapov.guessthecelebrity.view.activity.start;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.pavelpotapov.guessthecelebrity.R;
import com.pavelpotapov.guessthecelebrity.databinding.ActivityStartBinding;
import com.pavelpotapov.guessthecelebrity.service.SoundService;
import com.pavelpotapov.guessthecelebrity.util.ScreenMode;
import com.pavelpotapov.guessthecelebrity.view.activity.game.GameActivity;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class StartActivity extends AppCompatActivity {
    private InterstitialAd ad;
    private static final String AD_TEST_ID = "ca-app-pub-3940256099942544/1033173712";
    private static final String AD_ID = "ca-app-pub-5839831086467167/9779308908";

    private ActivityStartBinding binding;
    private Boolean volume = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View viewDialog = getLayoutInflater().inflate(R.layout.dialog_settings, null);
        AlertDialog settingsDialog = new AlertDialog.Builder(this).setView(viewDialog).create();
        SwitchCompat switchMusic = viewDialog.findViewById(R.id.switchMusic);
        SwitchCompat switchNotifications = viewDialog.findViewById(R.id.switchNotifications);
        Button btnSave = viewDialog.findViewById(R.id.btnSave);
        Button btnCancel = viewDialog.findViewById(R.id.btnCancel);
        settingsDialog.setCanceledOnTouchOutside(false);
        settingsDialog.setCancelable(false);

        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Инициализация SDK мобильной рекламы
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NotNull InitializationStatus initializationStatus) {
                loadAd();
                Log.i("TAG", "The ad initialization complete");
            }
        });

        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAd(ad);
            }
        });

        binding.btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                settingsDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

                switchMusic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Toast.makeText(StartActivity.this, "Switch", Toast.LENGTH_SHORT).show();
                        if (volume == true) {
                            switchMusic.setChecked(false);
                            volume = false;
                        } else if (volume == false) {
                            switchMusic.setChecked(true);
                            volume = true;
                        }
                        Intent intent = new Intent(StartActivity.this, SoundService.class);
                        intent.setAction("switch");
                        startService(intent);
                    }
                });

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Toast.makeText(StartActivity.this, "Save", Toast.LENGTH_SHORT).show();
                        settingsDialog.dismiss();
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Toast.makeText(StartActivity.this, "Save", Toast.LENGTH_SHORT).show();
                        settingsDialog.dismiss();
                    }
                });

                settingsDialog.show();
                ScreenMode.hideSystemUI(settingsDialog.getWindow());
                settingsDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
            }
        });

//        binding.btnVolume.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // TODO: Вывести диалог с дополнительной информацией
//            }
//        });

        Intent intent = new Intent(StartActivity.this, SoundService.class);
        intent.setAction("play");
        startService(intent);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) ScreenMode.hideSystemUI(getWindow());
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Intent intent = new Intent(StartActivity.this, SoundService.class);
        intent.setAction("resume");
        startService(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent = new Intent(StartActivity.this, SoundService.class);
        intent.setAction("pause");
        startService(intent);
    }

    public void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this, AD_ID, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                ad = interstitialAd;
                Log.i("TAG", "onAdLoaded");

                ad.setFullScreenContentCallback(new FullScreenContentCallback(){
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        Log.d("TAG", "The ad was dismissed.");
                        ad = null;
                        String info = "info";
                        Intent intent = GameActivity.newIntent(StartActivity.this, info);
                        startActivity(intent);
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NotNull AdError adError) {
                        Log.d("TAG", "The ad failed to show.");
                        ad = null;
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        Log.d("TAG", "The ad was shown.");
                        ad = null;
                    }
                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Log.i("TAG", "onAdFailedToLoad");
                ad = null;
            }
        });
    }

    // Показать рекламу
    public void showAd(InterstitialAd ad) {
        if (ad != null) {
            ad.show(this);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");

            String info = "info";
            Intent intent = GameActivity.newIntent(StartActivity.this, info);
            startActivity(intent);
        }
    }


}
