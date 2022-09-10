package com.rsstudio.newsbreeze.di

import android.content.Context
import com.rsstudio.newsbreeze.app.App
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun applicationContext( @ApplicationContext applicationContext: Context) : App {
        return applicationContext as App
    }

}