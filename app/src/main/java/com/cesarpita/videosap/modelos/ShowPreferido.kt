package com.cesarpita.videosap.modelos

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "preferidos")
data class ShowPreferido (
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val showId:Long,
    var esFavorito:Boolean
)