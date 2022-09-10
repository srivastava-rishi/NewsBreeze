package com.rsstudio.newsbreeze.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.rsstudio.newsbreeze.app.App
import com.rsstudio.newsbreeze.data.network.apis.NewsApiInterface
import com.rsstudio.newsbreeze.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun applicationContext( @ApplicationContext applicationContext: Context) : App {
        return applicationContext as App
    }

    @Singleton
    @Provides
    fun provideNewsApi(): NewsApiInterface =
        Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(NewsApiInterface::class.java)

}