package com.example.projekt.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "kaloria_session_table")
data class KaloriaSession(
    @PrimaryKey
    var sessionId: String= "asd",
    @ColumnInfo(name = "burned_calories")
    var burnedCalories: Double = 0.0
)
