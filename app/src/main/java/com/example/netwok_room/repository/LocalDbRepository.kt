package com.example.netwok_room.repository

import com.example.netwok_room.localdata.GenresDAO

import com.example.netwok_room.model.GenresMovie
import com.example.netwok_room.model.LastPage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalDbRepository @Inject constructor(private val genresDAO: GenresDAO) {


    suspend fun insertAll(list :List<GenresMovie>) = genresDAO.insertAll(list)
    suspend fun insertLastPage(lastPage: LastPage) = genresDAO.insertLastPage(lastPage)

    val getGenresLocal: Flow<List<GenresMovie>> = genresDAO.getAll()
    val getLastPage: Flow<LastPage> = genresDAO.getLastPage()

   suspend fun delete_all() =genresDAO.deleteAll()
   suspend fun delete_allpage() =genresDAO.deleteAllpage()
}