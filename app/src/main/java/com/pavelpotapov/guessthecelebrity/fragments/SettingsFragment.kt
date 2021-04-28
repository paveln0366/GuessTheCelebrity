package com.pavelpotapov.guessthecelebrity.fragments

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.pavelpotapov.guessthecelebrity.R
import com.pavelpotapov.guessthecelebrity.services.SoundService

class SettingsFragment : DialogFragment() {

    lateinit var listener: SettingsDialogListener

    interface SettingsDialogListener {
        fun onDialogSaveClick(dialog: DialogFragment)
        fun onDialogCancelClick(dialog: DialogFragment)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as SettingsDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement SettingsDialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val inflater = requireActivity().layoutInflater
            val builder = AlertDialog.Builder(it)
            builder.setView(inflater.inflate(R.layout.fragment_settings, null))
            builder.setPositiveButton("Save", DialogInterface.OnClickListener { dialog, id ->
                listener.onDialogSaveClick(this)
            })
            builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                listener.onDialogCancelClick(this)
            })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}