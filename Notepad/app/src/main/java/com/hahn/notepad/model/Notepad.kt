package com.hahn.notepad.model




import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Notepad(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String
)