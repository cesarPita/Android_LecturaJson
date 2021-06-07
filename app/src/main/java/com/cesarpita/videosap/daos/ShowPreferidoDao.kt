package com.cesarpita.videosap.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cesarpita.videosap.modelos.ShowPreferido

@Dao
interface ShowPreferidoDao {
    @Query("SELECT * FROM preferidos WHERE showId=:id")
    fun get(id:Long) : ShowPreferido

    @Insert
    fun guardar(preferido:ShowPreferido)

    @Update
    fun modificar(preferido: ShowPreferido)
}