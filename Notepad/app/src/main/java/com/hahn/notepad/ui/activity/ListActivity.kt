package com.hahn.notepad.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hahn.notepad.R
import com.hahn.notepad.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityListBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}