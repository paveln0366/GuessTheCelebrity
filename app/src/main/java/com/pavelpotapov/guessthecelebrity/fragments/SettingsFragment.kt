package com.pavelpotapov.guessthecelebrity.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.pavelpotapov.guessthecelebrity.R

class SettingsFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val selectedItems = ArrayList<Int>()
            val inflater = requireActivity().layoutInflater
            val builder = AlertDialog.Builder(it)
            builder.setView(inflater.inflate(R.layout.fragment_settings, null))
//                    .setTitle(R.string.settings)
//                    .setMessage(R.string.settings)
//                    .setMultiChoiceItems(R.array.settings_array, null,
//                    DialogInterface.OnMultiChoiceClickListener {
//                        dialog, which, isChecked ->
//                        if (isChecked) {
//                            selectedItems.add(which)
//                        } else if (selectedItems.contains(which)) {
//                            selectedItems.remove(Integer.valueOf(which))
//                        }
//                    })
//                    .setPositiveButton("Save", DialogInterface.OnClickListener { dialog, id ->
//                        // On ok
//                    })
//                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
//                        // On cancel
//                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}