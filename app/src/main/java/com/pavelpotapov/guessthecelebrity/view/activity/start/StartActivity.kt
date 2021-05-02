package com.pavelpotapov.guessthecelebrity.view.activity.start

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.preference.PreferenceManager
import com.pavelpotapov.guessthecelebrity.R
import com.pavelpotapov.guessthecelebrity.databinding.ActivityStartBinding
import com.pavelpotapov.guessthecelebrity.view.activity.game.GameActivity
import com.pavelpotapov.guessthecelebrity.utils.SoundService
import com.pavelpotapov.guessthecelebrity.utils.ScreenMode

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding
    private var volume = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewDialog = layoutInflater.inflate(R.layout.dialog_settings, null)
        val settingsDialog = AlertDialog.Builder(this).setView(viewDialog).create()
        val switchMusic = viewDialog.findViewById<SwitchCompat>(R.id.switchMusic)
        val switchNotifications = viewDialog.findViewById<SwitchCompat>(R.id.switchNotifications)
        val btnSave = viewDialog.findViewById<Button>(R.id.btnSave)
        val btnCancel = viewDialog.findViewById<Button>(R.id.btnCancel)
        settingsDialog.setCanceledOnTouchOutside(false)
        settingsDialog.setCancelable(false)


        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            val info = "info"
            val intent = GameActivity.newIntent(this@StartActivity, info)
            startActivity(intent)
        }

        binding.btnSettings.setOnClickListener {
            settingsDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            settingsDialog.window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
            switchMusic.setOnClickListener {
                Toast.makeText(this, "Switch", Toast.LENGTH_SHORT).show()
                if (volume){
                    switchMusic.isChecked = false
                    volume = false
                    preferences.edit().putBoolean("volume", false).apply()

                }
                else if (!volume) {
                    switchMusic.isChecked = true
                    volume = true
                    preferences.edit().putBoolean("volume", true).apply()
                }
                Intent(this, SoundService::class.java).apply {
                    action = "ACTION_SWITCH_SOUND"
                }.also { intent ->
                    startService(intent)
                }
            }

            btnSave.setOnClickListener {
                Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show()
                settingsDialog.dismiss()
            }

            btnCancel.setOnClickListener {
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
                settingsDialog.dismiss()
            }

            settingsDialog.show()

            settingsDialog.window?.let { window -> ScreenMode.hideSystemUI(window) }
            settingsDialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
        }

//        binding.btnVolume.setOnClickListener {
//            it as Button
//            if (volume) {
//                it.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_lock_silent_mode, 0, 0, 0)
////                it.setBackgroundResource(android.R.drawable.ic_lock_silent_mode)
//                volume = false
//            } else if (!volume) {
//                it.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_lock_silent_mode_off, 0, 0, 0)
////                it.setBackgroundColor(getResources().getColor(android.R.color.white)
////                it.setBackgroundResource(android.R.drawable.ic_lock_silent_mode_off)
//                volume = true
//            }
//            Intent(this, SoundService::class.java).apply {
//                action = "ACTION_SWITCH_SOUND"
//            }.also { intent ->
//                startService(intent)
//            }
//        }

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