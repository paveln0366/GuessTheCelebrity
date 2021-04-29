package com.pavelpotapov.guessthecelebrity.activities

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Switch
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.pavelpotapov.guessthecelebrity.GameActivity
import com.pavelpotapov.guessthecelebrity.R
import com.pavelpotapov.guessthecelebrity.databinding.ActivityStartBinding
import com.pavelpotapov.guessthecelebrity.services.SoundService
import com.pavelpotapov.guessthecelebrity.utils.ScreenMode

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding
    private var volume = true

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
            val view = layoutInflater.inflate(R.layout.dialog_settings, null)
            val switchMusic = view.findViewById<SwitchCompat>(R.id.switchMusic)
            val switchNotifications = view.findViewById<SwitchCompat>(R.id.switchNotifications)
            val btnSave = view.findViewById<Button>(R.id.btnSave)
            val btnCancel = view.findViewById<Button>(R.id.btnCancel)
            val settingsDialog = AlertDialog.Builder(this).setView(view).create()
            settingsDialog.setCanceledOnTouchOutside(false)
            settingsDialog.setCancelable(false)
            settingsDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            btnSave.setOnClickListener {
                Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show()
                settingsDialog.dismiss()
            }

            btnCancel.setOnClickListener {
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
                settingsDialog.dismiss()
            }

            settingsDialog.show()
        }

        binding.btnVolume.setOnClickListener {
            it as Button
            if (volume) {
                it.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_lock_silent_mode, 0, 0, 0)
//                it.setBackgroundResource(android.R.drawable.ic_lock_silent_mode)
                volume = false
            } else if (!volume) {
                it.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_lock_silent_mode_off, 0, 0, 0)
//                it.setBackgroundColor(getResources().getColor(android.R.color.white)
//                it.setBackgroundResource(android.R.drawable.ic_lock_silent_mode_off)
                volume = true
            }
            Intent(this, SoundService::class.java).apply {
                action = "ACTION_SWITCH_SOUND"
            }.also { intent ->
                startService(intent)
            }
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