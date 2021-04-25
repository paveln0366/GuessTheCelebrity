package com.pavelpotapov.guessthecelebrity.activities

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.pavelpotapov.guessthecelebrity.GameActivity
import com.pavelpotapov.guessthecelebrity.databinding.ActivityStartBinding
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
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) ScreenMode.hideSystemUI(window)
    }
}