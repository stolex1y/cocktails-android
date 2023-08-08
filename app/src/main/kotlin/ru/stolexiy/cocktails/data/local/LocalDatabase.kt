package ru.stolexiy.cocktails.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.stolexiy.cocktails.data.local.dao.CocktailCrudDao
import ru.stolexiy.cocktails.data.local.model.CocktailEntity

@Database(
    entities = [
        CocktailEntity::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
internal abstract class LocalDatabase : RoomDatabase() {
    abstract fun cocktailCrudDao(): CocktailCrudDao

    companion object {
        private const val DATABASE_NAME = "cocktails-db"

        @Volatile
        var instance: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): LocalDatabase =
            Room.databaseBuilder(context, LocalDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}
