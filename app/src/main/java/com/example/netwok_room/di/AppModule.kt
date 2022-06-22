package com.example.netwok_room.di

import android.content.Context
import androidx.room.Room

import com.example.netwok_room.localdata.AppDatabase
import com.example.netwok_room.localdata.GenresDAO
import com.example.netwok_room.repository.LocalDbRepository
import com.example.netwok_room.repository.NetworkRepository
import com.example.netwok_room.service.NetworkService
import com.example.netwok_room.utils.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constants.BASEURL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkhttpClient():OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }
    @Provides
    @Singleton
    fun provideNetworkService(retrofit: Retrofit):NetworkService{
        return retrofit.create(NetworkService::class.java)
    }
    @Provides
    @Singleton
    fun provideNetworkRepository(networkService: NetworkService,localDbRepository: LocalDbRepository):NetworkRepository{
        return NetworkRepository(networkService, localDbRepository)
    }


    @Provides
    @Singleton
    fun provideAppContext() :ApplicationContext{
        return provideAppContext()
    }
    @Provides
    @Singleton
    fun provideRoomDb(@ApplicationContext appContext: Context):AppDatabase{

        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java, "genres.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideYourDao(db: AppDatabase) = db.genresDao()

    @Singleton
    @Provides
    fun provideLocalDbRepository(genresDAO: GenresDAO) = LocalDbRepository(genresDAO)

}