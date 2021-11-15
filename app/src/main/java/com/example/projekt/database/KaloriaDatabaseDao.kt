package com.example.projekt.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface KaloriaDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(session: KaloriaSession)

    @Update
    suspend fun update(session: KaloriaSession)

    @Query("SELECT * from kaloria_session_table WHERE sessionId = :key")
    suspend fun get(key: String): KaloriaSession?

    @Query("DELETE FROM kaloria_session_table")
    suspend fun clear()

    @Query("SELECT * FROM kaloria_session_table ORDER BY sessionId DESC")
    fun getAllSessions(): LiveData<List<KaloriaSession>>
}