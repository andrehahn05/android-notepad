package com.hahn.notepad.extensions


import android.content.Context
import android.content.Intent
import android.widget.Toast

fun Context.toast(message:String) {
    Toast.makeText(
        this,
        message,
        Toast.LENGTH_LONG
    ).show()
}

fun Context.nav(
    clazz: Class<*>,
    intent: Intent.() -> Unit = {}
) {
    Intent(this, clazz).apply {
        intent()
        startActivity(this)
    }
}