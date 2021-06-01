package com.weiwei.beats.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Restaurant (
    var city: String,
    var name: String,
    var address: String,
    var photoId: Int,
    var description: String,
    var photoFile: String = "",  //for the external photo file
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
) {
//    @PrimaryKey(autoGenerate = true)
//    var id: Long = 0L
//    var photoFile: String = ""  //for the external photo file
}