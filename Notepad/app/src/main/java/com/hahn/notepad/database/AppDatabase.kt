package com.hahn.notepad.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hahn.notepad.database.dao.NotepadDAO
import com.hahn.notepad.model.Notepad

private const val NAME_DATABASE = "note.db"

@Database(entities = [Notepad::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val notepadDao: NotepadDAO

    companion object {
        @Volatile
        private var db: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return db?: Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                NAME_DATABASE
            ).build().also {
                db = it
            }
        }

    }

}