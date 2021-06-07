package com.cesarpita.videosap.daos

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
/*
class ListConverter {
    @TypeConverter
    fun fromString(value: String):List<String>{
       //Obtiene el tipo de dato (Caso de una lista)
        val listType : Type = object : TypeToken<List<String>>(){}.type
        return Gson().fromJson(value, listType)
    }
    @TypeConverter
    fun fromList(list:List<String>): String{
       val gson = Gson()
        return gson.toJson(list)
    }
}
 */