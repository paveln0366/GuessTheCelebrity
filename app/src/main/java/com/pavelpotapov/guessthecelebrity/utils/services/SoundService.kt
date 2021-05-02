package com.pavelpotapov.guessthecelebrity.utils.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log

private const val ACTION_PLAY: String = "ACTION_PLAY"
private const val ACTION_PAUSE: String = "ACTION_PAUSE"
private const val ACTION_RESUME: String = "ACTION_RESUME"
private const val ACTION_STOP: String = "ACTION_STOP"

private const val ACTION_SWITCH_SOUND: String = "ACTION_SWITCH_SOUND"

class SoundService : Service(), MediaPlayer.OnPreparedListener {
    private var player = MediaPlayer()
    private var position: Int = 0
    private var numberCallResume = 0
//    private val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
    private var volume = true
//    private var volume = preferences.getBoolean("volume", true)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PLAY -> {
                player.apply {
                    val afd = applicationContext.assets.openFd("sound_0.mp3")
                    player.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                    player.isLooping = true
                    player.setVolume(1.0f, 1.0f)
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
                if (numberCallResume == 0) {
                    Log.d("player_position", "Number of call resume() - $numberCallResume")
                    numberCallResume++
                } else {
                    player.seekTo(position)
                    Log.d("player_position", position.toString())
                    player.start()
                }
            }
            ACTION_STOP -> {
                stopService(intent)
            }
            ACTION_SWITCH_SOUND -> {
                if (volume) {
                    volumeOFF()
                    volume = false
                } else if (!volume) {
                    volumeON()
                    volume = true
                }
            }
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onPrepared(player: MediaPlayer) {
        Log.d("player_position", position.toString())
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

    private fun volumeOFF() {
        player.setVolume(0.0f, 0.0f)
    }

    private fun volumeON() {
        player.setVolume(1.0f, 1.0f)
    }
}