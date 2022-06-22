package com.example.netwok_room.localdata

import androidx.room.Database
import androidx.room.RoomDatabase

import com.example.netwok_room.model.GenresMovie
import com.example.netwok_room.model.LastPage

@Database(entities = [GenresMovie::class,LastPage::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun genresDao(): GenresDAO
}