package com.cesarpita.videosap.daos
/*
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cesarpita.videosap.modelos.Show
import com.cesarpita.videosap.modelos.ShowImage
import com.cesarpita.videosap.modelos.ShowPreferido

@Database(entities = arrayOf(Show::class, ShowImage::class, ShowPreferido::class), version = 3)
@TypeConverters(ListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun showDao(): ShowDao
    abstract fun imagenesDao() : ImagenesDao
    abstract fun preferidoDao() : ShowPreferidoDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        //Volatile, la instacia s eborrr en cualquier momento
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "peliculas_ap_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
//                instance
            }
        }
    }
}
*/