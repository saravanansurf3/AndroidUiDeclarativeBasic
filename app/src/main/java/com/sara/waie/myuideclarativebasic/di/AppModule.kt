package com.sara.waie.myuideclarativebasic.di

import com.sara.waie.myuideclarativebasic.data.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesAppRepository():AppRepository{
        return AppRepository()
    }
}