package com.pavelpotapov.guessthecelebrity.view.activity.start;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.pavelpotapov.guessthecelebrity.R;
import com.pavelpotapov.guessthecelebrity.databinding.ActivityStartBinding;
import com.pavelpotapov.guessthecelebrity.service.SoundService;
import com.pavelpotapov.guessthecelebrity.util.ScreenMode;
import com.pavelpotapov.guessthecelebrity.view.activity.game.GameActivity;

public class StartActivity extends AppCompatActivity {

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

        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String info = "info";
                Intent intent = GameActivity.newIntent(StartActivity.this, info);
                startActivity(intent);
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

        binding.btnVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Вывести диалог с дополнительной информацией
            }
        });

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
}
