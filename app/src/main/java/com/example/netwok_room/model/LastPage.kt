package com.example.netwok_room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LastPage(@PrimaryKey @ColumnInfo(name = "lastpage")val lastpage: Int)
