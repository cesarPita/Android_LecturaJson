package com.cesarpita.videosap.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.cesarpita.videosap.modelos.ShowImage

@Dao
interface ImagenesDao {
    @Insert
    fun guardarImagen(imagen:ShowImage)

    @Insert
    fun guardarImagenes(imagen:List<ShowImage>)

    @Query("SELECT * FROM imagenes")
    fun lecturaSimple():ShowImage

    @Query("SELECT * FROM imagenes")
    fun lecturaMultiple():List<ShowImage>
}