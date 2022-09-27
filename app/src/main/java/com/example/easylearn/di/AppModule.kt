package com.example.easylearn.di

import android.app.Application
import androidx.room.Room
import com.example.easylearn.api.CourseApi
import com.example.easylearn.data.CourseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit():Retrofit =
        Retrofit.Builder()
            .baseUrl(CourseApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideCourseApi(retrofit: Retrofit): CourseApi =
        retrofit.create(CourseApi::class.java)




    @Provides
    @Singleton
    fun provideDatabase(app: Application) : CourseDatabase =
        Room.databaseBuilder(app, CourseDatabase::class.java, "course_database")
            .build()

    @Provides
    fun provideCourseDao(db:CourseDatabase)= db.courseDao()
}