package com.example.netwok_room.ui

import androidx.lifecycle.ViewModel
import com.example.netwok_room.repository.LocalDbRepository
import com.example.netwok_room.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val networkRepository: NetworkRepository,val localDbRepository: LocalDbRepository) :ViewModel() {

  fun getGenres(page: Int ) = networkRepository.getGenres(page)

  val getGenresLocal  = localDbRepository.getGenresLocal
  val getLastPage  = localDbRepository.getLastPage

   suspend fun deleteall() = withContext(Dispatchers.IO){localDbRepository.delete_all()}
   suspend fun deleteallpage() = withContext(Dispatchers.IO){localDbRepository.delete_allpage()}
}