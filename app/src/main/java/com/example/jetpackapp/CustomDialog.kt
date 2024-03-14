package com.example.jetpackapp

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class CustomDialog(private val title: String, private val text: String) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the Builder class for convenient dialog construction
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(title)
            .setMessage(text)
            .setPositiveButton("OK") { dialog, id ->
                // Handle OK button action here
            }
            // You can also set a negative button if needed
            .setNegativeButton("Cancel") { dialog, id ->
                // User cancelled the dialog
            }
        // Create the AlertDialog object and return it
        return builder.create()
    }
}
