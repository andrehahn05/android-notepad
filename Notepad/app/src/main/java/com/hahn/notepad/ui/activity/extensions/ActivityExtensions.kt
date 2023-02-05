package com.hahn.notepad.ui.activity.extensions

import android.app.Activity
import android.widget.Toast

fun Activity.tosat(message:String) {
    Toast.makeText(
        this,
        message,
        Toast.LENGTH_LONG
    ).show()
}