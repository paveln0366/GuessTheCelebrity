package com.pavelpotapov.guessthecelebrity.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.pavelpotapov.guessthecelebrity.GameActivity
import com.pavelpotapov.guessthecelebrity.databinding.ActivityStartBinding
import com.pavelpotapov.guessthecelebrity.services.SoundService
import com.pavelpotapov.guessthecelebrity.utils.ScreenMode

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            val info = "info"
            val intent = GameActivity.newIntent(this@StartActivity, info)
            startActivity(intent)
        }

        binding.btnSettings.setOnClickListener {
            val info = "info"
            val intent = SettingsActivity.newIntent(this@StartActivity, info)
            startActivity(intent)
        }

        Intent(this, SoundService::class.java).apply {
            action = "ACTION_PLAY"
        }.also { intent ->
            startService(intent)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) ScreenMode.hideSystemUI(window)
    }

    override fun onResume() {
        super.onResume()
        Intent(this, SoundService::class.java).apply {
            action = "ACTION_RESUME"
        }.also { intent ->
            startService(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        Intent(this, SoundService::class.java).apply {
            action = "ACTION_PAUSE"
        }.also { intent ->
            startService(intent)
        }
    }
}