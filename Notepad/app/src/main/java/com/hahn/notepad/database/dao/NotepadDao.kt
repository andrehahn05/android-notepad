package com.hahn.notepad.database.dao

import androidx.room.*
import com.hahn.notepad.model.Notepad
import kotlinx.coroutines.flow.Flow

@Dao
interface NotepadDao {

    @Query("SELECT * FROM notepad WHERE id = :id")
    fun findById(id: String?): Flow<Notepad?>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(vararg note: Notepad)
    @Query("SELECT * FROM notepad")
    fun findAll(): Flow<List<Notepad>>
    @Delete
    suspend fun remove(noteList: Notepad )
    @Query("SELECT * FROM notepad WHERE id = :id")
    fun find(id: String): Notepad?


}
