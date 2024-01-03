package com.belajar.mdh.githubuserapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: FavoriteEntity)

    @Delete
    fun delete(favorite: FavoriteEntity)

    @Query("SELECT * from FavoriteEntity ORDER BY username ASC")
    fun getAllUser(): LiveData<List<FavoriteEntity>>

    @Query("SELECT * FROM FavoriteEntity WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteEntity>
}