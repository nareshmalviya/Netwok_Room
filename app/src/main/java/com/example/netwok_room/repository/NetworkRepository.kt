package com.example.netwok_room.repository


import androidx.lifecycle.liveData
import androidx.lifecycle.LiveData


import com.example.netwok_room.model.GenresMovie
import com.example.netwok_room.model.LastPage

import com.example.netwok_room.service.NetworkService
import com.example.netwok_room.utils.BaseDataSource
import com.example.netwok_room.utils.Constants
import com.example.netwok_room.utils.Resource


import kotlinx.coroutines.Dispatchers


import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val networkService: NetworkService,
   private val localDbRepository: LocalDbRepository
) : BaseDataSource() {


    fun getGenres(page: Int): LiveData<Resource<List<GenresMovie>>> = liveData(Dispatchers.IO) {
        emit(Resource.loading())

        val responseStatus = getResult { (networkService.getMovies(Constants.APIKEY,page)) }
        if (responseStatus.status == Resource.Status.SUCCESS) {
            localDbRepository.insertAll(responseStatus.data!!.results)
            localDbRepository.insertLastPage(LastPage(page))
            emit(Resource.success(responseStatus.data!!.results))
        } else if (responseStatus.status == Resource.Status.ERROR) {
            emit(Resource.error(responseStatus.message!!))
        }
    }


}