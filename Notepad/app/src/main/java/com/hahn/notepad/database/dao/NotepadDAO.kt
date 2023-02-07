package com.hahn.notepad.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hahn.notepad.model.Notepad
import kotlinx.coroutines.flow.Flow
@Dao
interface NotepadDAO {

    @Query("SELECT * FROM notepad WHERE id = :id")
    fun findById(id: String): Flow<Notepad?>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(vararg note: Notepad)
    @Query("SELECT * FROM notepad")
    fun findAll(): Flow<List<Notepad>>

}
