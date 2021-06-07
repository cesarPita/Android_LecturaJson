package com.cesarpita.videosap.modelos

//import androidx.room.ColumnInfo
//import androidx.room.Entity
//import androidx.room.PrimaryKey
import java.io.Serializable

//@Entity(tableName = "imagenes")
data class ShowImage (
//    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name="idImage")
    //val id:Long,
//    @ColumnInfo(name="mediando")
    val medium:String?,
  //  @ColumnInfo(name="orignal") //nombre la columna en BDs
    val original:String?
):Serializable