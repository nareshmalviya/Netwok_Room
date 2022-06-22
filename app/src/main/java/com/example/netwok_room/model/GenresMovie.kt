package com.example.netwok_room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "GenresMovie")
data class GenresMovie(
                       @PrimaryKey(autoGenerate = true) val pid:Int,
                       @ColumnInfo(name = "id")  val id:Int,
                       @ColumnInfo(name = "original_title") val original_title:String,
                       @ColumnInfo(name = "overview") val overview:String,
                       @ColumnInfo(name = "poster_path") val poster_path :String)