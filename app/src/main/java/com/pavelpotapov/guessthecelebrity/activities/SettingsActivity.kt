package com.pavelpotapov.guessthecelebrity.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import com.pavelpotapov.guessthecelebrity.R
import com.pavelpotapov.guessthecelebrity.services.SoundService
import com.pavelpotapov.guessthecelebrity.utils.ScreenMode

class SettingsActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_INFO = "info"
        fun newIntent(context: Context, info: String): Intent {
            val intent = Intent(context, SettingsActivity::class.java)
            intent.putExtra(EXTRA_INFO, info)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.settings, SettingsFragment())
                    .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
//            setPreferencesFromResource(R.xml.root_preferences, rootKey)
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