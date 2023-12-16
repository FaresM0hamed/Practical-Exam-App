package com.devfares.practicalexam.data.dao

import androidx.room.*
import com.devfares.practicalexam.data.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user_table WHERE email = :email AND password = :password")
    suspend fun getUserByEmailAndPassword(email: String, password: String): User?

    @Update
    suspend fun updatePassword(user: User)
}
