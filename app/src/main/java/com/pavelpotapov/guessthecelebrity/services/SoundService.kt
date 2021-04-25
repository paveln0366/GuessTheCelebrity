package com.pavelpotapov.guessthecelebrity.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log

private const val ACTION_PLAY: String = "ACTION_PLAY"
private const val ACTION_PAUSE: String = "ACTION_PAUSE"
private const val ACTION_RESUME: String = "ACTION_RESUME"
private const val ACTION_STOP: String = "ACTION_STOP"

class SoundService : Service(), MediaPlayer.OnPreparedListener {

    private var player = MediaPlayer()
    private var position: Int = 0
//    private var position: Int = player.currentPosition

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
            ACTION_PAUSE -> {
                player.pause()
                position = player.currentPosition
                Log.d("player_position", position.toString())
            }
            ACTION_RESUME -> {
                player.seekTo(position)
                Log.d("player_position", position.toString())
                player.start()
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
        Log.d("player_position", position.toString())
        player.seekTo(position)
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