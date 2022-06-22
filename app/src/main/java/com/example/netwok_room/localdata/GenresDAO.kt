package com.example.netwok_room.localdata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.example.netwok_room.model.GenresMovie
import com.example.netwok_room.model.LastPage
import kotlinx.coroutines.flow.Flow


@Dao
interface GenresDAO  {

    @Query("SELECT * FROM GenresMovie")
     fun getAll(): Flow<List<GenresMovie>>

    @Query("SELECT * FROM LastPage ORDER BY lastpage DESC LIMIT 1")
    fun getLastPage(): Flow<LastPage>

    @Query("DELETE  FROM GenresMovie")
    suspend fun deleteAll()

    @Query("DELETE  FROM LastPage")
    suspend fun deleteAllpage()

    @Query("SELECT * FROM GenresMovie WHERE id = :id")
     suspend fun getByID(id: Int): GenresMovie

    @Insert()
    suspend  fun insertAll( movie: List<GenresMovie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insertLastPage( lastPage: LastPage)


}