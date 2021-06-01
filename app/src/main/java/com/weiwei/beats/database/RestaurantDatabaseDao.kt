package com.weiwei.beats.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RestaurantDatabaseDao {
    @Insert
    suspend fun insertScene(scene: Restaurant) //insert the scene specified by the parameter

    @Update
    suspend fun updateScene(scene: Restaurant)

    @Delete
    suspend fun deleteScene(scene: Restaurant) //delete the scene specified by the parameter

    @Query("select * from Restaurant where id = :id")
    suspend fun loadOneScene(id: Long): Restaurant

    @Query("select * from Restaurant") //load all scene data
    fun loadAllScenes(): LiveData<List<Restaurant>> //should be livedata in the project

    @Query("select * from Restaurant where name LIKE '%' || :name || '%'") //find matched scenes by names
    fun findScenes(name: String): LiveData<List<Restaurant>> //should be livedata in the project

    @Query("delete from Restaurant")
    suspend fun deleleAllScenes()
}