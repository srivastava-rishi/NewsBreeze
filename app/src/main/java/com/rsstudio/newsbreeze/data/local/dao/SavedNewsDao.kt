package com.rsstudio.newsbreeze.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rsstudio.newsbreeze.data.local.entity.SavedNewsEntity

@Dao
interface SavedNewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedNews(savedNewsEntity: SavedNewsEntity)

    @Query("SELECT * FROM savedNews")
    fun getAllSavedNews() : MutableList<SavedNewsEntity>

    @Query("DELETE FROM savedNews")
    fun deleteAllSavedNews()

    @Delete
    fun deleteSavedNews(savedNewsEntity: SavedNewsEntity)

}