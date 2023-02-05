package com.hahn.notepad.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hahn.notepad.R

class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
    }
}