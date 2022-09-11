package com.rsstudio.newsbreeze.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rsstudio.newsbreeze.data.local.dao.SavedNewsDao
import com.rsstudio.newsbreeze.data.local.entity.SavedNewsEntity

@Database(entities = [SavedNewsEntity::class], version = 1)
abstract class SavedNewsDatabase:  RoomDatabase() {

    abstract fun newsDao(): SavedNewsDao

}