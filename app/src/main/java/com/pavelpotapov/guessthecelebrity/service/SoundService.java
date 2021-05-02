package com.pavelpotapov.guessthecelebrity.service;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.io.IOException;

public class SoundService extends Service implements MediaPlayer.OnPreparedListener {
    private static final String ACTION_PLAY = "play";
    private static final String ACTION_PAUSE = "pause";
    private static final String ACTION_RESUME = "resume";
    private static final String ACTION_STOP = "stop";
    private static final String ACTION_SWITCH = "switch";
    private MediaPlayer mediaPlayer;
    private AssetFileDescriptor afd;
    private int numberResumeCall = 0; // Колличество вызовов сетода onResume()
    private int position = 0; // Позиция на котором приостановилась мелодия
    private boolean volume = true; // Состояние звука (вкл/выкл)


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals(ACTION_PLAY)) {
            mediaPlayer = new MediaPlayer();
            try {
                afd = getApplicationContext().getAssets().openFd("sound_0.mp3");
                mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                mediaPlayer.setLooping(true);
                mediaPlayer.setOnPreparedListener(this);
                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (intent.getAction().equals(ACTION_PAUSE)) {
            mediaPlayer.pause();
            position = mediaPlayer.getCurrentPosition();
        } else if (intent.getAction().equals(ACTION_RESUME)) {
            if (numberResumeCall == 0) {
                numberResumeCall++;
            } else {
                mediaPlayer.start();
            }
        } else if (intent.getAction().equals(ACTION_STOP)) {
            stopService(intent);
        } else if (intent.getAction().equals(ACTION_SWITCH)) {
            if (volume == true) {
                volumeOff();
                volume = false;
            } else if (volume == false) {
                volumeOn();
                volume = true;
            }
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    @Override
    public boolean stopService(Intent name) {
        mediaPlayer.release();
        return super.stopService(name);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }

    private void volumeOff() {
        mediaPlayer.setVolume(0.0f, 0.0f);
    }

    private void volumeOn() {
        mediaPlayer.setVolume(1.0f, 1.0f);
    }
}
