package com.devfares.practicalexam.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devfares.practicalexam.data.dao.ProductDao
import com.devfares.practicalexam.data.dao.UserDao
import com.devfares.practicalexam.data.model.ProductModel
import com.devfares.practicalexam.data.model.User


@Database(
    entities = [ProductModel::class,User::class], version = 1, exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null
        fun getDatabase(context: Context): LocalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, LocalDatabase::class.java, "app_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}