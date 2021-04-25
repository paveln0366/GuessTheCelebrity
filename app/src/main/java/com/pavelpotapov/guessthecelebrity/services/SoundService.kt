package com.pavelpotapov.guessthecelebrity.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

private const val ACTION_PLAY: String = "ACTION_PLAY"
private const val ACTION_STOP: String = "ACTION_STOP"

class SoundService : Service(), MediaPlayer.OnPreparedListener {

    private var player = MediaPlayer()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PLAY -> {
                player.apply {
                    val afd = applicationContext.assets.openFd("sound_0.mp3")
                    player.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                    player.isLooping = true
                    player.setVolume(100f, 100f)

                    setOnPreparedListener(this@SoundService)
                    prepareAsync()
                }
            }
            ACTION_STOP -> {
                stopService(intent)
            }
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onPrepared(player: MediaPlayer) {
        player.start()
    }

    override fun stopService(name: Intent?): Boolean {
        player.release()
        return super.stopService(name)
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}